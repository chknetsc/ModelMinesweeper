package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.MineBox

import scala.util.Random

object MineFieldGenerator {
  def returnFieldAfterFirstClick(size: (Int, Int), mineCount: Int, clickPosition: (Int, Int)): Array[Array[MineBox]] = {

    def assembleMinePositionsList: List[(Int, Int)] = {
      var minePositions: List[(Int, Int)] = Nil
      /*minePositions = (0, 0) :: minePositions
      minePositions = (3, 0) :: minePositions
      minePositions = (5, 4) :: minePositions
      minePositions*/

      val r = new Random
      var posX = 0
      var posY = 0
      for (i <- 0 until mineCount) {
        def generateMines(): Unit = {
          def generatePositionX(): Unit = {
            posX = r.nextInt(size._1)
            if (posX == clickPosition._1) {
              generatePositionX
            }
          }
          def generatePositionY(): Unit = {
            posY = r.nextInt(size._2)
            if (posY == clickPosition._2) {
              generatePositionY
            }
          }
          generatePositionX()
          generatePositionY()
          val addedToList = addIfNotInListYet((posX, posY))
          if (!addedToList) {
            generateMines()
          }

        }
        generateMines()
      }

      def addIfNotInListYet(minePosition: (Int, Int)): Boolean = {
        if (!(minePositions.contains(minePosition))) {
          minePositions = minePosition :: minePositions
          return true
        }
        return false
      }
      minePositions
    }

    def calculateMinesAround(minePositions: List[(Int, Int)]): Array[Array[MineBox]] = {
      val mineBoxField = Array.ofDim[MineBox](size._1, size._2)
      for (x <- 0 until size._1; y <- 0 until size._2) {
        mineBoxField(x)(y) = new MineBox((x, y), 0, false, true, false)
      }
      for (x <- minePositions) {
        mineBoxField(x._1)(x._2) = mineBoxField(x._1)(x._2).setMine()
        if (inRange(x._1 - 1, x._2 - 1)) {
          mineBoxField(x._1 - 1)(x._2 - 1) = mineBoxField(x._1 - 1)(x._2 - 1).increaseMinesAroundCount()
        }
        if (inRange(x._1 - 1, x._2)) {
          mineBoxField(x._1 - 1)(x._2) = mineBoxField(x._1 - 1)(x._2).increaseMinesAroundCount()

        }
        if (inRange(x._1 - 1, x._2 + 1)) {
          mineBoxField(x._1 - 1)(x._2 + 1) = mineBoxField(x._1 - 1)(x._2 + 1).increaseMinesAroundCount()

        }
        if (inRange(x._1, x._2 - 1)) {
          mineBoxField(x._1)(x._2 - 1) = mineBoxField(x._1)(x._2 - 1).increaseMinesAroundCount()

        }
        if (inRange(x._1, x._2 + 1)) {
          mineBoxField(x._1)(x._2 + 1) = mineBoxField(x._1)(x._2 + 1).increaseMinesAroundCount()

        }
        if (inRange(x._1 + 1, x._2 - 1)) {
          mineBoxField(x._1 + 1)(x._2 - 1) = mineBoxField(x._1 + 1)(x._2 - 1).increaseMinesAroundCount()

        }
        if (inRange(x._1 + 1, x._2)) {
          mineBoxField(x._1 + 1)(x._2) = mineBoxField(x._1 + 1)(x._2).increaseMinesAroundCount()

        }
        if (inRange(x._1 + 1, x._2 + 1)) {
          mineBoxField(x._1 + 1)(x._2 + 1) = mineBoxField(x._1 + 1)(x._2 + 1).increaseMinesAroundCount()

        }
      }

      def inRange(position: (Int, Int)): Boolean = position._1 < size._1 && position._1 > -1 && position._2 < size._2 && position._2 > -1

      mineBoxField
    }

    val minesPositions = assembleMinePositionsList
    val mineField = calculateMinesAround(minesPositions)

    mineField
  }

  def returnInitialField(size: (Int, Int)): Array[Array[MineBox]] = {
    val initialField = Array.ofDim[MineBox](size._1, size._2)
    for (i <- 0 until size._1; j <- 0 until size._2) {
      initialField(i)(j) = new MineBox((i, j), 0, false, true, false)
    }

    initialField

  }


}
