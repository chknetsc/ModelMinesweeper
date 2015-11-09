package de.htwg.nich.minesweeper

import de.htwg.nich.minesweeper.control.impl.{MineControl, MineFieldGenerator}
import de.htwg.nich.minesweeper.model.MineBox
import de.htwg.nich.minesweeper.view.MineTUI

/**
 * Created by Boldi on 26.10.2015.
 */
object Minesweeper {

  def main(args: Array[String]) {
    val mineController = new MineControl
    val mineTUI = new MineTUI(mineController)
    mineController.addObserver(mineTUI)
    mineTUI.run()
  }

}
