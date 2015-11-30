package de.htwg.nich.minesweeper.control.impl

import akka.actor.{Props, ActorSystem}
import de.htwg.nich.minesweeper.control.impl.MineFieldRefresher.ClickMode
import de.htwg.nich.minesweeper.model.{GameData, GameState}

import scala.swing.Publisher

/**
  * Created by Boldi on 19.10.2015.
  */
class MineControl extends Publisher {

  val gameData = new GameData
  val system = ActorSystem("MyActorSystem")
  val generator = system.actorOf(Props(MineFieldGenerator), "generator")
  val refresher = system.actorOf(Props(MineFieldRefresher), "refresher")
  updateMineField

  def newGame(playerName: String, sizeX: Int, sizeY: Int, mines: Int): Boolean = {
    if (sizeX * sizeY <= mines) {
      publish(new GameMessage("Number of mines must be smaller than field size!"))
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

  def handleInput(clickType: Int, fieldX: Int, fieldY: Int): Boolean = {
    val continue = false
    if (xPositionOnField(fieldX) && yPositionOnField(fieldY)) {
      if (gameData.mineField(fieldX)(fieldY).isCovered) {
        if (clickType == 1 && !gameData.mineField(fieldX)(fieldY).isFlagged) {
          gameData.clickMode = ClickMode.Click
          gameData.clickPosition = Some((fieldX, fieldY))
          updateGame
        } else {
          gameData.clickMode = ClickMode.Toggle
          gameData.clickPosition = Some((fieldX, fieldY))
          updateGame
        }
      }
    }
    continue
  }

  def updateGame() = {
    updateMineField
    checkGameState
  }

  def updateMineField: Unit = {
    gameData.currentGameState match {
      case GameState.NewGame =>
        gameData.currentGameState = GameState.FirstClick
        generator ! "Test"
        gameData.mineField = MineFieldGenerator.returnInitialField(gameData.fieldSize)
      case GameState.FirstClick =>
        gameData.currentGameState = GameState.InGame
        generator ! "Test"
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, MineFieldGenerator.returnFieldAfterFirstClick(gameData.fieldSize, gameData.minesOnField, gameData.clickPosition.getOrElse(0, 0)), gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
      case GameState.InGame =>
      refresher ! "Test2"
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, gameData.mineField, gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
      case _ =>
      // TODO Error Handling
        publish(new GameMessage("Fatal Error -> Invalid GameState !!!!!!!!!!!!!!!!"))
    }
  }

  def checkGameState = {
    gameData.currentGameState match {
      case GameState.Lost =>
        uncoverField
        publish(new GameMessage("Sorry " + gameData.playerName + ", you lost the game!"))
      case GameState.Won =>
        uncoverField
        publish(new GameMessage("Congratulations " + gameData.playerName + "! You won the game!"))
      case _ =>
        publish(new UpdatePosition)
    }
  }

  def uncoverField: Unit = {
    for (x <- 0 until gameData.fieldSize._1; y <- 0 until gameData.fieldSize._2) {
      gameData.mineField(x)(y) = gameData.mineField(x)(y).uncover()
    }
  }

  // Closure  Helper Function
  def positionOnField(fieldSize: Int) = (position: Int) => {
    if (position >= 0 && position < fieldSize) true else false
  }
  val xPositionOnField = positionOnField(gameData.fieldSize._1)
  val yPositionOnField = positionOnField(gameData.fieldSize._2)
}
