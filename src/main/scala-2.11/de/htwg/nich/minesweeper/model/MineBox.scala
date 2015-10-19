package de.htwg.nich.minesweeper.model

/**
 * Created by Boldi on 19.10.2015.
 */
class MineBox(val posX: Int, val posY: Int, val minesAround: Int, val isMine: Boolean, val isCovered: Boolean, val isFlagged: Boolean) {

  def hasMinesAround: Boolean = minesAround > 0

}
