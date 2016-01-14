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
    showInputs()
    val input = StdIn.readLine()
    processInputLine(input)
  }

  def showInputs() = {
    val possibleInputs = new StringBuilder
    possibleInputs.append("Possible Inputs: \n")
    possibleInputs.append("Show Field  => show, x, y \n")
    possibleInputs.append("Flag Field  => flag, x, y \n")
    possibleInputs.append("New Game    => new, playername, sizeX, sizeY, mines \n")
    possibleInputs.append("End Game    => end \n")
    println(possibleInputs)
  }

  def processInputLine(input: String): Boolean = {
    if (input != null) {
      val inputArray = input.replaceAll(" ", "").split(",")
      inputArray(0) match {
        case "show" if inputArray(1).toInt < controller.gameData.fieldSize._1 && inputArray(2).toInt < controller.gameData.fieldSize._2 =>
          controller.handleInput(1, inputArray(1).toInt, inputArray(2).toInt)
        case "flag" if inputArray(1).toInt < controller.gameData.fieldSize._1 && inputArray(2).toInt < controller.gameData.fieldSize._2 =>
          controller.handleInput(3, inputArray(1).toInt, inputArray(2).toInt)
        case "new" =>
          controller.newGame(inputArray(1), inputArray(2).toInt, inputArray(3).toInt, inputArray(4).toInt)
        case "end" =>
          System.exit(0)
        case _ =>
          println("Wrong Input !")
          true
      }
    }
    true
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
      println("Udate")
      update()
    case e: GameMessage =>
      endUpdate(e.message)
    case e: NewGame =>
      println("New")
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
