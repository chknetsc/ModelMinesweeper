package de.htwg.nich.minesweeper.view

import de.htwg.nich.minesweeper.control.impl.MineControl
import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MineTuiSpecs  extends SpecificationWithJUnit {

  "New Mineswepper Tui" should {
    val tui = new MineTUI(new MineControl)

    "show Mineswepper Tui inputs" in {
      tui.showInputs()
      true must beTrue
    }

    "process input Tui " in {
      tui.processInputLine("show, 2, 3")
      true must beTrue
    }

  }

}
