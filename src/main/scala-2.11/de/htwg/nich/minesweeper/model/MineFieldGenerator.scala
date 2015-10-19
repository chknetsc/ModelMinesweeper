package de.htwg.nich.minesweeper.model

import scala.util.Random

/**
 * Created by Boldi on 19.10.2015.
 */
object MineFieldGenerator{
  def calculateField(sizeX:Int, sizeY:Int, mineCount:Int): Array[Array[MineBox]] = {
    def assembleMinePositionsList {
      var minePositions: List[(Int, Int)] = Nil
      val r = new Random
      var cycles = 10
      for (i <- 0 until cycles) {
        val posX = r.nextInt(sizeX)
        val posY = r.nextInt(sizeY)
        addIfNotInListYet((posX, posY))
      }

      def addIfNotInListYet(minePosition: (Int, Int)): Unit = {
        if (!minePositions.contains(minePosition)) {
          minePositions = minePosition :: minePositions
        } else {
          cycles += 1
        }
      }
    }
      return null
    }

  def returnInitialField(sizeX:Int, sizeY:Int): Array[Array[MineBox]] ={
    val initialField = Array.ofDim[MineBox](sizeX,sizeY)
    for(i <- 0 until sizeX; j <- 0 until sizeY){
      initialField(i)(j) = new MineBox(i,j,0,false,true,false)
    }
    return initialField
  }
  
}
