package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.{GameData, GameState, MineBox}


object MineFieldRefresher {

  object ClickMode extends Enumeration {
    type clickMode = Value
    val Click, Toggle = Value
  }

  def returnRefreshedMineField(size: (Int, Int), mineBoxArray: Array[Array[MineBox]], clickPosition: (Int, Int), clickMode: ClickMode.clickMode, gameData: GameData): Array[Array[MineBox]] = {
    if (clickMode == ClickMode.Click) {
      if (mineBoxArray(clickPosition._1)(clickPosition._2).isMine) {
        gameData.currentGameState = GameState.Lost
      } else {
        def recursiveUncovering(boxPosition: (Int, Int)): Unit = {
          def getBoxAtPosition(position: (Int, Int)): MineBox = {
            if (position._1 < size._1 && position._1 > -1 && position._2 < size._2 && position._2 > -1) {
              return mineBoxArray(position._1)(position._2)
            }
            return null
          }
          def getMineBoxesAround(recursivePosition: (Int, Int)): List[MineBox] = {
            var boxesAroundList: List[MineBox] = Nil
            if (getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2 - 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2 - 1) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2 + 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 - 1, recursivePosition._2 + 1) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1, recursivePosition._2 - 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1, recursivePosition._2 - 1) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1, recursivePosition._2 + 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1, recursivePosition._2 + 1) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2 - 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2 - 1) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2) :: boxesAroundList
            }
            if (getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2 + 1) != null) {
              boxesAroundList = getBoxAtPosition(recursivePosition._1 + 1, recursivePosition._2 + 1) :: boxesAroundList
            }
            return boxesAroundList
          }
          val boxesAround = getMineBoxesAround(boxPosition)
          mineBoxArray(boxPosition._1)(boxPosition._2) = mineBoxArray(boxPosition._1)(boxPosition._2).uncover()

          if (mineBoxArray(boxPosition._1)(boxPosition._2).minesAround == 0) {
            for (x <- boxesAround) {
              if (mineBoxArray(x.position._1)(x.position._2).isCovered) {
                recursiveUncovering(x.position)
              }
            }
          }
        }
        recursiveUncovering(clickPosition)
        if (allBoxesUncovered) {
          gameData.currentGameState = GameState.Won
        }
      }
    } else if (clickMode == ClickMode.Toggle) {
      mineBoxArray(clickPosition._1)(clickPosition._2) = mineBoxArray(clickPosition._1)(clickPosition._2).toggleFlag()
    }

    def allBoxesUncovered: Boolean = {
      for (x <- 0 until size._1; y <- 0 until size._2) {
        if (!mineBoxArray(x)(y).isMine && mineBoxArray(x)(y).isCovered) {
          return false
        }
      }
      true
    }
    mineBoxArray
  }

}
