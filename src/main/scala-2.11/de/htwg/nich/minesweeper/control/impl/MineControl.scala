package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.control.impl.MineFieldRefresher.ClickMode
import de.htwg.nich.minesweeper.model.{GameState, MineBox, GameData}
import de.htwg.nich.minesweeper.observer.Observable

/**
  * Created by Boldi on 19.10.2015.
  */
class MineControl extends Observable {

  val gameData = new GameData

  def handleInput(input: String): Unit = {
    // Input String Pattern: show/flag, x, y
    val inputArray = input.replaceAll(" ", "").split(",")
    inputArray(0) match {
      case "show" =>
        if (inputArray(1).toInt < gameData.fieldSize._1 && inputArray(2).toInt < gameData.fieldSize._2) {
          gameData.clickMode = ClickMode.Click
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
          changeGameState
        }
      case "flag" =>
        if (inputArray(1).toInt < gameData.fieldSize._1 && inputArray(2).toInt < gameData.fieldSize._2) {
          gameData.clickMode = ClickMode.Toggle
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
          changeGameState
        }
      case default =>
      // TODO FEHLERBEHANDLUNG
    }
    notifyObservers()
  }

  def changeGameState: Unit = {
    gameData.currentGameState match {
      case GameState.NewGame =>
        println("Change NEW GAME")
        gameData.currentGameState = GameState.FirstClick
      case GameState.FirstClick =>
        println("Change FIRST CLICK")
        gameData.currentGameState = GameState.InGame
      case GameState.InGame =>
        println("Is in InGAME")
      case GameState.Won =>
        println("Won!")
        System.exit(0)
    }
  }

  def getMineField: Array[Array[MineBox]] =
    gameData.currentGameState match {
      case GameState.NewGame =>
        println("NEW GAME")
        MineFieldGenerator.returnInitialField(gameData.fieldSize)
      case GameState.FirstClick =>
        println("FIRST CLICK")
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, MineFieldGenerator.returnFieldAfterFirstClick(gameData.fieldSize, gameData.minesOnField, gameData.clickPosition.getOrElse(0, 0)), gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
        gameData.mineField
      case GameState.InGame =>
        println("InGAME")
        gameData.mineField = MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, gameData.mineField, gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
        gameData.mineField
    }
}
