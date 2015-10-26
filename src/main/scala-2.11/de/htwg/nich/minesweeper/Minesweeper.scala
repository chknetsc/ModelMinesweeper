package de.htwg.nich.minesweeper

import de.htwg.nich.minesweeper.control.impl.MineFieldGenerator
import de.htwg.nich.minesweeper.model.MineBox
import de.htwg.nich.minesweeper.view.MineTUI

/**
 * Created by Boldi on 26.10.2015.
 */
object Minesweeper {

  def main(args: Array[String]) {
    val mineField:Array[Array[MineBox]] = MineFieldGenerator.returnInitialField(4,7)
    val mineTUI = new MineTUI
    mineTUI.printTUI(mineField)
  }

}
