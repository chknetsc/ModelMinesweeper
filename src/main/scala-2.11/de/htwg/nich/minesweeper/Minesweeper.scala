package de.htwg.nich.minesweeper

import de.htwg.nich.minesweeper.control.impl.{MineControl, MineFieldGenerator}
import de.htwg.nich.minesweeper.model.MineBox
import de.htwg.nich.minesweeper.view.{MineGUI, MineTUI}

/**
 * Created by Boldi on 26.10.2015.
 */
object Minesweeper {

  def main(args: Array[String]) {

    val mineController = new MineControl

    new MineGUI(mineController)
    val mineTUI = new MineTUI(mineController)
    while (mineTUI.input()){}
  }

}
