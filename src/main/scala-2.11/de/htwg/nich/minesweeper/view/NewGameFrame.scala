package de.htwg.nich.minesweeper.view

import javax.swing.text.DocumentFilter.FilterBypass
import javax.swing.text.{AbstractDocument, AttributeSet, DocumentFilter}

import de.htwg.nich.minesweeper.control.impl.MineControl

import scala.swing._
import scala.swing.event.ButtonClicked

/**
  * Created by Boldi on 23.11.2015.
  */
class NewGameFrame(controller: MineControl) extends Frame {

  listenTo(controller)

  val playerNameTextField = new TextField() {
    horizontalAlignment = Alignment.Right
    text = "Player1"
    columns = 10
  }

  def numberTextField = new TextField() {
    horizontalAlignment = Alignment.Right
    text = "10"
    columns = 10

    object IntegralFilter extends DocumentFilter {
      override def insertString(fb: FilterBypass, offs: Int, str: String, a: AttributeSet) {
        if (str.forall((c) => c.isDigit)) super.insertString(fb, offs, str, a)
      }

      override def replace(fb: FilterBypass, offs: Int, l: Int, str: String, a: AttributeSet) {
        if (str.forall((c) => c.isDigit)) super.replace(fb, offs, l, str, a)
      }
    }

    peer.getDocument().asInstanceOf[AbstractDocument].setDocumentFilter(IntegralFilter)
  }

  val mines = numberTextField
  val sizeX = numberTextField
  val sizeY = numberTextField

  val button = new Button() {
    preferredSize = new Dimension(100, 30)
    text = "OK"
    listenTo(this)
    reactions += {
      case ButtonClicked(b) =>
        if (controller.newGame(playerNameTextField.text, sizeX.text.toInt, sizeY.text.toInt, mines.text.toInt)) {
          dispose()
        }
    }
  }

  val gridPanel = new BoxPanel(Orientation.Vertical) {
    background = java.awt.Color.WHITE
    contents += flowPanel(new Label("Player: "), playerNameTextField)
    contents += flowPanel(new Label("Size X: "), sizeX)
    contents += flowPanel(new Label("Size Y: "), sizeY)
    contents += flowPanel(new Label("Mines: "), mines)
    contents += flowPanel(new Label(" "), button)

  }

  def flowPanel(label: Label, component: Component) = new FlowPanel() {
    contents += label
    contents += component
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
  }
  title = "New Game"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE)
  size = new Dimension(300, 300)
  visible = true


}
