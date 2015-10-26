package de.htwg.nich.minesweeper.model



/**
 * Created by Boldi on 26.10.2015.
 */

object GameState extends Enumeration {
  type gameState = Value
  val Won, Lost, Ingame, NewGame = Value
}

class GameData {

  var currentGameState = GameState.NewGame
}