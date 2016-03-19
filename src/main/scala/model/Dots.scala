package edu.luc.etl.cs313.scala.uidemo.model

import scala.collection.mutable.ListBuffer
import android.graphics.Color
import scala.util.Random

/**
 * A dot: the coordinates, color and size.
 * @param x horizontal coordinate.
 * @param y vertical coordinate.
 * @param color the color.
 * @param diameter dot diameter.
 */
case class Dot(var x: Float, var y: Float, var color: Int, diameter: Int, var vulnerable: Int)



object Dots {
  trait DotsChangeListener {
    /** @param dots the dots that changed. */
    def onDotsChange(dots: Dots): Unit
  }
}

/** A list of dots. */
class Dots {
  var isGameOver = false

  private var dots = new ListBuffer[Dot]

  private var dotsChangeListener: Dots.DotsChangeListener = _

  /** @param l set the change listener. */
  def setDotsChangeListener(l: Dots.DotsChangeListener) = dotsChangeListener = l

  /** @return the most recently added dot. */
  def getLastDot(): Dot = if (dots.size <= 0) null else dots.last // TODO convert to option

  /** @return immutable list of dots. */
  def getDots(): List[Dot] = dots.toList

  /**
   * @param x dot horizontal coordinate.
   * @param y dot vertical coordinate.
   * @param color dot color.
   * @param diameter dot size.
   */
  def addDot(x: Float, y: Float, color: Int, diameter: Int, vulnerable: Int): Unit = {
    dots += Dot(x, y, color, diameter, vulnerable)
    notifyListener()
  }

  def removeDot(x: Float, y:Float, diameter: Int)={
    for (current <- dots)
      if(current.x > x-diameter && current.x < x+diameter && current.y > y-diameter && current.y < y+ diameter && current.color==Color.RED){
        dots-= current
      }
    notifyListener()
  }

  /** Vulnerable state random time*/
  def howVulnerable(percentage : Float) : Unit ={
    for (current <- dots){
      var howVulnerable = Random.nextInt(4)
      if (Random.nextFloat > percentage && current.vulnerable == 0){
        current.vulnerable=howVulnerable
      }
    }
    notifyListener()
  }


  /** Countdown to change vulnerable state */
  def decrementVulnerable: Unit ={
    for(current <- dots){
      if (current.vulnerable > 0 ){
        current.vulnerable = current.vulnerable - 1
      }
    }
    notifyListener()
  }

  /** If vulnerable change state// if not vulnerable change state*/
  def changeVulnerable: Unit ={
    for(current <- dots){
      if (current.vulnerable == 0 && current.color == Color.RED){ current.color = Color.BLACK}
      if (current.vulnerable > 0 && current.color == Color.BLACK){ current.color = Color.RED}
    }
    notifyListener()
  }

  /** Keep track of how many dots -- levels/score */
  def Count() : Int= {
    dots.length
  }


  /** Check cell for availability. */
  def isOccupied(xLoc: Float, yLoc: Float): Boolean ={
    var isAvail = false
    for(currentCell <- dots){
      if(currentCell.x == xLoc && currentCell.y == yLoc){
        isAvail = true
      }
    }
    isAvail
  }

  /** Remove all dots. */
  def clearDots(): Unit = {
    dots.clear()
    notifyListener()
  }

  private def notifyListener(): Unit =
    if (null != dotsChangeListener)
      dotsChangeListener.onDotsChange(this)

}