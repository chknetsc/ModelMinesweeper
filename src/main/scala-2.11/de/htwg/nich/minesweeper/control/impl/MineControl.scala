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
    val inputArray = input.split(",")
    println(inputArray.size)
    inputArray(0) match {
      case "show" =>
        // FIXME Fehler bei Test ob x/y der Eingabe in der Feldgröße liegt
        if ((gameData.fieldSize._1 to gameData.fieldSize._2 contains inputArray(1)) &&  (gameData.fieldSize._1 to gameData.fieldSize._2 contains inputArray(2))) {
          gameData.clickMode = ClickMode.Click
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
        }
      case "flag" =>
        // FIXME Fehler bei Test ob x/y der Eingabe in der Feldgröße liegt
        if ((gameData.fieldSize._1 to gameData.fieldSize._2 contains inputArray(1)) &&  (gameData.fieldSize._1 to gameData.fieldSize._2 contains inputArray(2))) {
          gameData.clickMode = ClickMode.Toggle
          gameData.clickPosition = Some((inputArray(1).toInt, inputArray(2).toInt))
        }
    }
    notifyObservers()
  }

  def getMineField: Array[Array[MineBox]] =
    gameData.currentGameState match  {
      case GameState.NewGame => MineFieldGenerator.returnInitialField(gameData.fieldSize)
      case GameState.FirstClick => MineFieldGenerator.returnFieldAfterFirstClick(gameData.fieldSize, gameData.minesOnField, gameData.clickPosition.getOrElse(0,0))
      case GameState.InGame =>
        val mineBoxArray = MineFieldGenerator.returnFieldAfterFirstClick(gameData.fieldSize, gameData.minesOnField, gameData.clickPosition.getOrElse(0,0))
        MineFieldRefresher.returnRefreshedMineField(gameData.fieldSize, mineBoxArray, gameData.clickPosition.getOrElse(0, 0), gameData.clickMode, gameData)
  }

}
