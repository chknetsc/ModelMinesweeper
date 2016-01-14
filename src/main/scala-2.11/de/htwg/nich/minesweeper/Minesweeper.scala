package de.htwg.nich.minesweeper

import de.htwg.nich.minesweeper.control.impl.{MineControl}
import de.htwg.nich.minesweeper.model.GameData
import de.htwg.nich.minesweeper.view.{MineGUI, MineTUI}
import scaldi.{Injectable, Module}

/**
 * Created by Boldi on 26.10.2015.
 */
object Minesweeper {

  def main(args: Array[String]) {

    val mineController = new MineControl

    new MineGUI(mineController)
    val mineTUI = new MineTUI(mineController)

    while(true) {mineTUI.input()}
  }

}
