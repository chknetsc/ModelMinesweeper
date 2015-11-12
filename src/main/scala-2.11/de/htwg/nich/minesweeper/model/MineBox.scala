package de.htwg.nich.minesweeper.model

/**
  * Created by Boldi on 19.10.2015.
  */
class MineBox(val position: (Int, Int), val minesAround: Int, val isMine: Boolean, val isCovered: Boolean, val isFlagged: Boolean) {

  def hasMinesAround: Boolean = minesAround > 0

  def toggleFlag(): MineBox = {
    if (this.isFlagged) {
      return new MineBox(this.position, this.minesAround, this.isMine, this.isCovered, false)
    }
    return new MineBox(this.position, this.minesAround, this.isMine, this.isCovered, true)
  }

  def uncover(): MineBox = {
    return new MineBox(this.position, this.minesAround, this.isMine, false, this.isFlagged)
  }

  def increaseMinesAroundCount(): MineBox = {
    return new MineBox(this.position, this.minesAround + 1, this.isMine, this.isCovered, this.isFlagged)
  }

  def setMine(): MineBox = {
    return new MineBox(this.position, this.minesAround, true, this.isCovered, this.isFlagged)
  }

}
