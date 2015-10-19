package de.htwg.nich.minesweeper.model

/**
 * Created by Boldi on 19.10.2015.
 */
class MineField(val size: (Int, Int), val mineCount: Int, mineField: Array[Array[MineBox]]) {

  def getAt(posX: Int, posY: Int) = mineField(posX)(posY)


}
