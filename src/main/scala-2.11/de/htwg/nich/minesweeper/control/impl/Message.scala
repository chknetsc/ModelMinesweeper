package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.{MineBox, GameData}

/**
 * Created by Chris on 30.11.2015.
 */
object GameDataMessage {

  case class InitEmptyField(gameData: GameData)
  case class InitField(gameData: GameData)
  case class ReturnFirstGameField(gameField: Array[Array[MineBox]])
  case class ReturnGameField(gameField: Array[Array[MineBox]])
}
