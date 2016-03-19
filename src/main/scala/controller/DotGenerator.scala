package edu.luc.etl.cs313.scala.uidemo
package controller

import android.os.AsyncTask
import android.util.Log
import android.graphics.Color

import model.Dots


// TODO figure out how to replace this with a future

/** Generate new dots, one per second. */
class DotGenerator(dots: Dots, controller: Controller, color: Int)
  extends AsyncTask[AnyRef, AnyRef, AnyRef] {

  /** Delay between generation of dots. */
  val time = 1000
  // TODO externalize
  val game = (LEVEL1, LEVEL2, LEVEL3)
  val vul = (VULNERABILITY1, VULNERABILITY2, VULNERABILITY3)

  var level = game._1
  var vulLevel = vul._1


  while (i < level ) {
    controller.makeDot(dots, Color.BLACK, 0)
    i+=1
  }

  def getController(): Controller = controller
    var i = 0

  def checkCount: Unit = {
    if (dots.Count() == 0){
      var i = 0

      if (level == game._1){
        level = game._2
        vulLevel = vul._2
      }
      else if (level == game._2){
        level = game._3
        vulLevel = vul._3
      }
      else if (level == game._3){
        level = 0
        dots.isGameOver = true
      }
    }
    level
  }


  override protected def onProgressUpdate(values: AnyRef*) = {
    //to change game levels
    if(dots.Count() == 0 ) {
      checkCount
      for (i <- 1 to level) {
        controller.makeDot(dots, Color.BLACK, 0)
      }
    }
    controller.moveDots(dots)
    dots.changeVulnerable
    dots.howVulnerable(vulLevel.toFloat)
    dots.decrementVulnerable

  }

  override protected def doInBackground(params: AnyRef*): AnyRef = {
    while (! isCancelled ) {
      try { Thread.sleep(time) } catch { case _: InterruptedException => return null }
      Log.d(TAG, "dot generator scheduling dot creation of color " + color)
      publishProgress(null)
    }
    null
  }
}