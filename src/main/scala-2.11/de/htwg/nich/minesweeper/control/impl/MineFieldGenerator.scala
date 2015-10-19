package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.{MineBox, MineField}

import scala.util.Random

/**
 * Created by Boldi on 19.10.2015.
 */
object MineFieldGenerator {
  def calculateField(size: (Int, Int), mineCount: Int, clickPosition: (Int, Int), mineField: MineField): MineField = {
    def assembleMinePositionsList {
      var minePositions: List[(Int, Int)] = Nil
      val r = new Random
      var cycles = 10
      for (i <- 0 until cycles) {
        val posX = r.nextInt(size._1)
        val posY = r.nextInt(size._2)
        addIfNotInListYet((posX, posY), clickPosition)
      }

      def addIfNotInListYet(minePosition: (Int, Int), clickPosition: (Int, Int)): Unit = {
        if (!(minePositions.contains(minePosition) && minePosition != clickPosition)) {
          minePositions = minePosition :: minePositions
        } else {
          cycles += 1
        }
      }
    }
    new MineField(size, mineCount, initialField)
  }

  def returnInitialField(size: (Int, Int), mineCount: Int): MineField = {
    val initialField = Array.ofDim[MineBox](size._1,size._2)
    for (i <- 0 until size._1; j <- 0 until size._2) {
      initialField(i)(j) = new MineBox(i, j, 0, false, true, false)
    }

    new MineField(size, mineCount, initialField)

  }

}
