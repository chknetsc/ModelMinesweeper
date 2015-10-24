package de.htwg.nich.minesweeper.control.impl

import de.htwg.nich.minesweeper.model.{MineBox, MineField}

import scala.util.Random

object MineFieldGenerator {
  def returnFieldAfterFirstClick(size: (Int, Int), mineCount: Int, clickPosition: (Int, Int), mineField: MineField): MineField = {
    val minesPositions = assembleMinePositionsList
    val minesAroundList = calculateMinesAround(minesPositions)
    assembleRefreshedMineField(minesAroundList, minesPositions)

    def assembleMinePositionsList: List[(Int, Int)] = {
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
      minePositions
    }

    def calculateMinesAround(minePositions: List[(Int, Int)]): Array[Array[Int]] = {
      val minesAroundMap = Array.ofDim[Int](size._1, size._2)
      var minesAround = 0
      for (x <- 0 until size._1; y <- 0 until size._2) {
        if (y - 1 >= 0) {
          if (x - 1 >= 0) {
            minesAround += getMineAroundBox((x - 1, y - 1), minePositions)
          }
          if (x + 1 < size._1) {
            minesAround += getMineAroundBox((x + 1, y - 1), minePositions)
          }
          minesAround += getMineAroundBox((x, y - 1), minePositions)
        }

        if (y + 1 < size._2) {
          if (x - 1 >= 0) {
            minesAround += getMineAroundBox((x - 1, y + 1), minePositions)
          }
          if (x + 1 < size._1) {
            minesAround += getMineAroundBox((x + 1, y + 1), minePositions)
          }
          minesAround += getMineAroundBox((x, y + 1), minePositions)
        }

        if (x - 1 >= 0) {
          minesAround += getMineAroundBox((x - 1, y), minePositions)
        }
        if (x + 1 < size._1) {
          minesAround += getMineAroundBox((x + 1, y), minePositions)
        }
        minesAroundMap(x)(y) = minesAround
      }

      def getMineAroundBox(checkPosition: (Int, Int), minePositions: List[(Int, Int)]): Int = {
        if (minePositions.contains(checkPosition)) {
          1
        }
        0
      }
      minesAroundMap
    }

    def assembleRefreshedMineField(minesAround: Array[Array[Int]], minePositions: List[(Int, Int)]): MineField = {
      val mineBoxField: Array[Array[MineBox]] = _
      for (x <- 0 until size._1; y <- 0 until size._2) {
        mineBoxField(x)(y) = new MineBox((x, y), minesAround(x)(y), minePositions.contains((x, y)), true, false)
      }
      new MineField((size._1, size._2), mineCount, mineBoxField)
    }
  }

  def returnInitialField(size: (Int, Int), mineCount: Int): MineField = {
    val initialField = Array.ofDim[MineBox](size._1,size._2)
    for (i <- 0 until size._1; j <- 0 until size._2) {
      initialField(i)(j) = new MineBox((i, j), 0, false, true, false)
    }

    new MineField(size, mineCount, initialField)

  }
}