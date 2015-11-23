package de.htwg.nich.minesweeper.view

import javax.swing.ImageIcon

import de.htwg.nich.minesweeper.control.impl.{GameLost, GameWon, MineControl, UpdatePosition}
import de.htwg.nich.minesweeper.model.GameState

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

/**
  * Created by chris on 14.11.2015.
  */
class MineGUI(controller: MineControl) extends Frame {

  val buttonArray: Array[Array[Button]] = Array.ofDim(controller.gameData.fieldSize._1, controller.gameData.fieldSize._2)

  listenTo(controller)

  val flag = new ImageIcon(getClass.getResource("/flag.png"))
  val mine = new ImageIcon(getClass.getResource("/mine.jpg"))

  def gridPanel = new GridPanel(controller.gameData.fieldSize._2, controller.gameData.fieldSize._1) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for (y <- 0 until controller.gameData.fieldSize._2; x <- 0 until controller.gameData.fieldSize._1) {
      val button = new Button("") {
        preferredSize = new Dimension(100, 100)
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
            if (e.peer.getButton == 1) controller.handleInput("show," + x + "," + y)
            else if (e.peer.getButton == 3) controller.handleInput("flag," + x + "," + y)
        }
      }
      contents += button
      buttonArray(x)(y) = button
    }
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
  }
  title = "Minesweeper"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        controller.gameData.currentGameState = GameState.NewGame
        controller.updateGame
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    /*contents += new Menu("Edit") {
      mnemonic = Key.E
      //      contents += new MenuItem(Action("Copy") { controller.copy })
      //      contents += new MenuItem(Action("Paste") { controller.paste })
    }*/
  }
  visible = true

  def updateButtons() = {
    for (x <- 0 until controller.gameData.fieldSize._1; y <- 0 until controller.gameData.fieldSize._2) {
      //  FLAG
      if (controller.gameData.mineField(x)(y).isFlagged) {
        buttonArray(x)(y).icon = flag
      } else {
        buttonArray(x)(y).icon = null
      }

      //  MINE
      if (!controller.gameData.mineField(x)(y).isCovered && controller.gameData.mineField(x)(y).isMine && !controller.gameData.mineField(x)(y).isFlagged) {
        buttonArray(x)(y).icon = mine
      } else if (controller.gameData.mineField(x)(y).isCovered && !controller.gameData.mineField(x)(y).isFlagged) {
        buttonArray(x)(y).icon = null
      }


      if (controller.gameData.mineField(x)(y).isCovered) {
        buttonArray(x)(y).enabled = true
        buttonArray(x)(y).text = ""
      } else {
        buttonArray(x)(y).enabled = false
        buttonArray(x)(y).text = controller.gameData.mineField(x)(y).minesAround.toString
      }

    }
    repaint()
  }

  reactions += {
    case e: UpdatePosition =>
      updateButtons()
    case e: GameWon =>
      Dialog.showMessage(gridPanel, "Congratulations You won the game!")
    case e: GameLost =>
      Dialog.showMessage(gridPanel, "Sorry, you lost the game!")
  }

}
