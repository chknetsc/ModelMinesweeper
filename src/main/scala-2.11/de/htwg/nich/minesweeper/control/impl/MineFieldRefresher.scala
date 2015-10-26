package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.MineBox

object MineFieldRefresher {

  var currentGameState: GameState.Value = GameState.NewGame

  object ClickMode extends Enumeration {
    type clickMode = Value
    val Click, Toggle = Value
  }

  object GameState extends Enumeration {
    type gameState = Value
    val Won, Lost, NewGame = Value
  }

  def returnRefreshedMineField(size: (Int, Int), mineBoxArray: Array[Array[MineBox]], clickPosition: (Int, Int), clickMode: ClickMode.clickMode): Array[Array[MineBox]] = {
    val returnMineBoxField = Array.ofDim[MineBox](size._1, size._2)
    if (clickMode == ClickMode.Click) {
      if (mineBoxArray(clickPosition._1)(clickPosition._2).isMine) {
        currentGameState = GameState.Lost
      } else if (allBoxesUncovered) {
        currentGameState = GameState.Won
      }
    } else if (clickMode == ClickMode.Toggle) {
      if (mineBoxArray(clickPosition._1)(clickPosition._2).isFlagged) {
        toggleTheFlag(false)
      } else {
        toggleTheFlag(true)
      }
    }
    def toggleTheFlag(flag: Boolean) = {
      for (x <- 0 until size._1; y <- 0 until size._2) {
        if (clickPosition ==(x, y)) {
          returnMineBoxField(x)(y) = new MineBox((x, y), mineBoxArray(x)(y).minesAround, mineBoxArray(x)(y).isMine, true, flag)
        }
      }
    }

    def allBoxesUncovered: Boolean = {
      for (x <- 0 until size._1; y <- 0 until size._2) {
        if (!mineBoxArray(x)(y).isMine && mineBoxArray(x)(y).isCovered) {
          false
        }
      }
      true
    }
    returnMineBoxField
  }

}
