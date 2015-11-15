package de.htwg.nich.minesweeper.view

import de.htwg.nich.minesweeper.control.impl.MineControl
import de.htwg.nich.minesweeper.model.{GameState, MineBox}
import de.htwg.nich.minesweeper.observer.Observer

import scala.io.StdIn

/**
 * Created by Boldi on 19.10.2015.
 */
class MineTUI(controller: MineControl) extends Runnable with Observer {

  override def run(): Unit = {
    update()
    while (controller.gameData.currentGameState != GameState.Lost || controller.gameData.currentGameState != GameState.Won) {
      input()
    }
  }

  def input(): Unit = {
    val input = StdIn.readLine()
    controller.handleInput(input)
  }

  override def update(): Unit = {
    println("Selected Position: " +  controller.gameData.clickPosition)
    printTUI(controller.getMineField)
    println(controller.getGameState)
  }

  def printTUI(mineField: Array[Array[MineBox]]): Unit = {
    val sb = new StringBuilder
    val sizeX = mineField.length
    val sizeY = mineField(0).length
    val numberWithTwoDigits = 10
    val newline = System.lineSeparator()

    sb.append("    ")

    for (x <- 0 until sizeX) {
      if (x < numberWithTwoDigits) {
        sb.append("{ ").append(x).append("}")
      } else {
        sb.append("{").append(x).append("}")
      }
    }
    sb.append(newline)

    for (y <- 0 until sizeY) {
      if (y < numberWithTwoDigits) {
        sb.append("{ ").append(y).append("}")
      } else {
        sb.append("{").append(y).append("}")
      }

      for (x <- 0 until sizeX) {
        val thisBox: MineBox = mineField(x)(y)
        if (thisBox.isFlagged && thisBox.isCovered) {
          sb.append("[ F]")
        } else if (thisBox.isCovered) {
          sb.append("[  ]")
        } else if (thisBox.isMine) {
          sb.append("[ X]")
        } else {
          sb.append("[ ").append(thisBox.minesAround)
            .append("]")
        }
      }
      sb.append(newline)
    }
    print(sb.toString())
  }
}
