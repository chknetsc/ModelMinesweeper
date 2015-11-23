package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.control.impl.MineFieldRefresher.ClickMode
import de.htwg.nich.minesweeper.model.{GameData, GameState}

import scala.swing.Publisher
import scala.swing.event.Event

/**
  * Created by Boldi on 19.10.2015.
  */
case class UpdatePosition() extends Event

case class GameEnd(message: String) extends Event

case class NewGame() extends Event

case class Error(errorMessage: String) extends Event

class MineControl extends Publisher {

  val gameData = new GameData
  updateMineField

  // Closure Function
  def positionOnField(fieldSize: Int) = (position: Int) => {
    if (position >= 0 && position < fieldSize) true
    false
  }

  val xPositionOnField = positionOnField(gameData.fieldSize._1)
  val yPositionOnField = positionOnField(gameData.fieldSize._2)

  // New
  def handleInput(clickType: Int, fieldX: Int, fieldY: Int): Boolean = {
    val continue = true
    if (xPositionOnField(fieldX) && yPositionOnField(fieldY)) {
      if (clickType == 3) {
        gameData.clickMode = ClickMode.Toggle
      } else {
        gameData.clickMode = ClickMode.Click
      }
      gameData.clickPosition = Some((fieldX, fieldY))
      updateGame
    }
    continue
  }

  def updateGame() = {
    updateMineField
    checkGameState
  }

  def checkGameState = {
    gameData.currentGameState match {
      case GameState.Lost =>
        uncoverField
        publish(new GameEnd("Sorry " + gameData.playerName + ", you lost the game!"))
      case GameState.Won =>
        uncoverField
        publish(new GameEnd("Congratulations " + gameData.playerName + "! You won the game!"))
      case _ =>
        publish(new UpdatePosition)
    }
  }

  def uncoverField: Unit = {
    for (x <- 0 until gameData.fieldSize._1; y <- 0 until gameData.fieldSize._2) {
      gameData.mineField(x)(y) = gameData.mineField(x)(y).uncover()
    }
  }

  def updateMineField: Unit = {
    gameData.currentGameState match {
      case GameState.NewGame =>
        gameData.currentGameState = GameState.FirstClick
        gameData.mineField = MineFieldGenerator.returnInitialField(gameData.fieldSize)
      case GameState.FirstClick =>
        gameData.currentGameState = GameState.InGame
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, MineFieldGenerator.returnFieldAfterFirstClick(gameData.fieldSize, gameData.minesOnField, gameData.clickPosition.getOrElse(0, 0)), gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
      case GameState.InGame =>
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, gameData.mineField, gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
      case _ =>
      // FIXME TUT nichts verbessern
    }
  }

  // OLD
  def handleInput(input: String): Boolean = {
    val inputArray = input.replaceAll(" ", "").split(",")
    inputArray(0) match {
      case "show" =>
        if (inputArray(1).toInt < gameData.fieldSize._1 && inputArray(2).toInt < gameData.fieldSize._2) {
          if (gameData.mineField(inputArray(1).toInt)(inputArray(2).toInt).isFlagged) {
            return false
          }
          gameData.clickMode = ClickMode.Click
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
          updateGame
        }
      case "flag" =>
        if (inputArray(1).toInt < gameData.fieldSize._1 && inputArray(2).toInt < gameData.fieldSize._2) {
          gameData.clickMode = ClickMode.Toggle
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
          updateGame
        }
      case default =>
      // TODO FEHLERBEHANDLUNG
    }
    true
  }

  def newGame(playerName: String, sizeX: Int, sizeY: Int, mines: Int): Boolean = {
    if (sizeX * sizeY <= mines) {
      publish(new Error("Number of mines must be smaller than field size!"))
      return false
    }
    gameData.currentGameState = GameState.NewGame
    gameData.playerName = playerName
    gameData.minesOnField = mines
    gameData.fieldSize = (sizeX, sizeY)
    updateMineField
    publish(new NewGame)
    true
  }


}
