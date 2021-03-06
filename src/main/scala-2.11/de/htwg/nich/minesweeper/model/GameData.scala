package de.htwg.nich.minesweeper.model

import de.htwg.nich.minesweeper.control.impl.MineFieldRefresher.ClickMode

/**
  * Created by Boldi on 26.10.2015.
  */

object GameState extends Enumeration {
  type gameState = Value
  val Won, Lost, InGame, FirstClick, NewGame = Value
}


class GameData {

   var currentGameState = GameState.NewGame
   var playerName = "Player1"
   var fieldSize = (10, 10)
   var minesOnField = 10
   var clickPosition = None: Option[(Int, Int)]
   var clickMode = ClickMode.Click
   var mineField: Array[Array[MineBox]] = null

}