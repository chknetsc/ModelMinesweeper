package de.htwg.nich.minesweeper.view

import de.htwg.nich.minesweeper.control.impl.MineControl
import de.htwg.nich.minesweeper.model.GameState
import de.htwg.nich.minesweeper.observer.{Observer, Observable}

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.io.Source._
/**
  * Created by chris on 14.11.2015.
  */
class MineGUI(controller: MineControl) extends Frame with Observer {

  def gridPanel = new GridPanel(controller.gameData.fieldSize._1, controller.gameData.fieldSize._2) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for (outerRow <- 0 until controller.gameData.fieldSize._1; outerColumn <- 0 until controller.gameData.fieldSize._2) {
      contents += new Button()
    }
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
  }
  title = "Mineswepper"
  visible = true

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") { controller.gameData.currentGameState = GameState.NewGame })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      //      contents += new MenuItem(Action("Copy") { controller.copy })
      //      contents += new MenuItem(Action("Paste") { controller.paste })
    }
  }

  override def update(): Unit = {
    redraw
  }

  def redraw = {
    for (row <- 0 until controller.gameData.fieldSize._1; column <- 0 until controller.gameData.fieldSize._2) {
      //cells(row)(column).redraw
    }
    repaint
  }

}
