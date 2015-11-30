package de.htwg.nich.minesweeper.view

import javax.swing.ImageIcon

import de.htwg.nich.minesweeper.control.impl._

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

/**
  * Created by chris on 14.11.2015.
  */
class MineGUI(controller: MineControl) extends Frame {

  var buttonArray: Array[Array[Button]] = Array.ofDim(controller.gameData.fieldSize._1, controller.gameData.fieldSize._2)

  listenTo(controller)

  val flag = new ImageIcon(getClass.getResource("/flag.png"))
  val mine = new ImageIcon(getClass.getResource("/mine.jpg"))

  def gridPanel = new GridPanel(controller.gameData.fieldSize._2, controller.gameData.fieldSize._1) {
    buttonArray = Array.ofDim(controller.gameData.fieldSize._1, controller.gameData.fieldSize._2)
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for (y <- 0 until controller.gameData.fieldSize._2; x <- 0 until controller.gameData.fieldSize._1) {
      val button = new Button("") {
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
           controller.handleInput(e.peer.getButton, x, y)
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
  size = new Dimension(500, 500)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        new NewGameFrame(controller)
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }
  visible = true

  def updateButtons() = {
    for (x <- 0 until controller.gameData.fieldSize._1; y <- 0 until controller.gameData.fieldSize._2) {
      //  FLAG
      if (controller.gameData.mineField(x)(y).isFlagged) {
        buttonArray(x)(y).icon = flag
      } else {
        try {
          buttonArray(x)(y).icon = null
        } catch {
          case e: Exception =>
            println("X = " + x)
            println("X = " + x)
            println("Button = " + buttonArray(x)(y))
        }
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
      } else if (!controller.gameData.mineField(x)(y).isCovered && !controller.gameData.mineField(x)(y).isMine) {
        buttonArray(x)(y).enabled = false
        buttonArray(x)(y).text = controller.gameData.mineField(x)(y).minesAround.toString
      }

    }
    repaint()
  }

  reactions += {
    case e: UpdatePosition =>
      println("GUI UPDATE")
      updateButtons()
    case e: GameMessage =>
      updateButtons()
      Dialog.showMessage(gridPanel, e.message)
    case e: NewGame =>
      println("GUI NEW")
      this.contents = new BorderPanel {
        add(gridPanel, BorderPanel.Position.Center)
      }
      this.size = new Dimension(500, 500)
      println(buttonArray)
      updateButtons()
  }

}
