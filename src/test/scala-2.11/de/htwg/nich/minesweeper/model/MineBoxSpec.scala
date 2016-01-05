package de.htwg.nich.minesweeper.model

import org.specs2.mutable.SpecificationWithJUnit

/**
  * Created by chris on 05.01.2016.
  */
class MineBoxSpec extends SpecificationWithJUnit {

  "A new MineBox on Postion 0,0 with no Mines Around" should {
    val minebox = new MineBox((0,0), 0, false, true, false)

    "has zero mines around" in {
      minebox.hasMinesAround must beFalse
    }

    "Position must be 0,0" in {
      minebox.position must be_== (0,0)
    }

    "increases mines around" in {
      minebox.hasMinesAround must beFalse
      minebox.increaseMinesAroundCount().hasMinesAround must beTrue
    }

    "toogle flag on minebox" in {
      minebox.isFlagged must beFalse
      val newMinebox = minebox.toggleFlag()
      newMinebox.isFlagged must beTrue

      newMinebox.toggleFlag().isFlagged must beFalse

    }

    "unvocer minebox" in {
      minebox.isCovered must beTrue
      minebox.uncover().isCovered must beFalse
    }

    "set mine on minebox" in {
      minebox.isMine must beFalse
      minebox.setMine().isMine must beTrue
    }
  }
}
