package de.htwg.nich.minesweeper.view

import de.htwg.nich.minesweeper.control.impl._
import de.htwg.nich.minesweeper.model.MineBox

import scala.io.StdIn
import scala.swing.Reactor

/**
  * Created by Boldi on 19.10.2015.
  */
class MineTUI(controller: MineControl) extends Reactor {

  listenTo(controller)
  update()

  def input(): Boolean = {
    val input = StdIn.readLine()
    val continue = controller.handleInput(input)
    continue
  }

  def update(): Unit = {
    println("Selected Position: " + controller.gameData.clickPosition)
    printTUI(controller.gameData.mineField)
  }

  def endUpdate(message: String): Unit = {
    println(message)
    printTUI(controller.gameData.mineField)

  }

  reactions += {
    case e: UpdatePosition =>
      update()
    case e: GameEnd =>
      endUpdate(e.message)
    case e: NewGame =>
      update()
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
