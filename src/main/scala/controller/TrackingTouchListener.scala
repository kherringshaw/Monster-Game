package edu.luc.etl.cs313.scala.uidemo
package controller

import android.content.Context
import android.graphics.Color
import android.view.{MotionEvent, View}
import android.widget.Toast

import scala.collection.mutable.ArrayBuffer

import model.Dots
import view.DotView

/** Listen for taps. */
class TrackingTouchListener(dots: Dots) extends View.OnTouchListener {

  val tracks = new ArrayBuffer[Int]

  override def onTouch(v: View, evt: MotionEvent): Boolean = {
    val action = evt.getAction
    action & MotionEvent.ACTION_MASK match {
      case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_POINTER_DOWN =>
        val idx = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
          MotionEvent.ACTION_POINTER_INDEX_SHIFT
        tracks += evt.getPointerId(idx)
      case MotionEvent.ACTION_POINTER_UP =>
        val idx = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
          MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        tracks -= evt.getPointerId(idx)
      case MotionEvent.ACTION_MOVE =>
        for (i <- tracks) {
          val idx = evt.findPointerIndex(i)
          for (j <- 0 until evt.getHistorySize) {
            /*addDot(
              dots,
              evt.getHistoricalX(idx, j),
              evt.getHistoricalY(idx, j),
              evt.getHistoricalPressure(idx, j),
              evt.getHistoricalSize(idx, j)
            )*/
          }
        }
      case _ => return false
    }

    for (i <- tracks) {
      val idx = evt.findPointerIndex(i)

      removeDot(dots,
        evt.getX(idx),
        evt.getY(idx),
        DOT_DIAMETER)
    }

    true

  }

  private def removeDot(dots: Dots, x: Float, y: Float, diameter:Int)=
    dots.removeDot(x,y,diameter)
}