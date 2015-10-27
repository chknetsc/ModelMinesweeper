package de.htwg.nich.minesweeper.observer

/**
 * Created by chris on 26.10.15.
 */
trait Observable {
  private var observers: List[Observer] = Nil

  def addObserver(observer: Observer) = observers = observer :: observers

  def notifyObservers() = observers.foreach(_.update())

  def getObserverList() = observers
}
