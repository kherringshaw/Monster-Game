package edu.luc.etl.cs313.scala.uidemo
package controller

import android.app.Activity
import android.graphics.Color
import android.view.View.OnKeyListener
import android.view.{MenuItem, Menu, KeyEvent, View}

import view.DotView
import model.Dots

import scala.util.Random

/** Controller mixin (stackable trait) for Android UI demo program */
trait Controller extends Activity with TypedActivityHolder {

  val dotModel: Dots

  private var dotView: DotView = _

  // TODO consider using State pattern

 // var isDotsView = true

  def toggleView(): Unit = {
      setContentView(R.layout.main)
      connectDotsView()
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.simple_menu, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem) = item.getItemId match {
    case R.id.menu_clear => dotModel.clearDots(); true
    case _ => super.onOptionsItemSelected(item)
  }

  def connectDotsView(): Unit = {
    dotView = findView(TR.dots)

    dotView.setDots(dotModel)
    dotView.setOnTouchListener(new TrackingTouchListener(dotModel))
    /* dotView.setOnKeyListener(new OnKeyListener {
       override def onKey(v: View, keyCode: Int, event: KeyEvent): Boolean =
       if (KeyEvent.ACTION_DOWN == event.getAction)
         keyCode match {
           case KeyEvent.KEYCODE_SPACE => makeDot(dotModel, Color.MAGENTA); true
           case KeyEvent.KEYCODE_ENTER => makeDot(dotModel, Color.BLUE); true
           case _ => false
         }
       else
         false
     })*/


    dotModel.setDotsChangeListener(new Dots.DotsChangeListener {
      def onDotsChange(dots: Dots) = {

        val d = dots.getLastDot
        var dead= dots.Count()
        findView(TR.text1).setText(if (null == d) "" else "MINIONS TO GO:")
        findView(TR.text2).setText(if (null == d) "GOOD JOB!!!" else dead.toString)
        dotView.invalidate()
      }
    })
  }

  /**
   * @param dots the dots we're drawing
   * @param color the color of the dot
   */
  def makeDot(dots: Dots, color: Int, vulnerable: Int): Unit = {

    dots.addDot(DOT_DIAMETER/2 + (Random.nextFloat() * 10).toInt * (dotView.getWidth / 10),
      DOT_DIAMETER/2 + (Random.nextFloat() * 10).toInt * (dotView.getHeight / 10), color, DOT_DIAMETER, vulnerable)
  }

  def moveDots(dots: Dots): Unit = {

    val gen = new Random()
    val rows = 10
    val cols = 10
    val cellWidth = dotView.getWidth / rows
    val cellHeight = dotView.getHeight / cols
    var x = 0.toFloat
    var y = 0.toFloat

    for (currentCell <- dots.getDots()) {
      val direction = gen.nextInt(7)
      x = currentCell.x
      y = currentCell.y

      direction match {

        case 0 => y = currentCell.y + cellHeight
        case 1 => y = currentCell.y - cellHeight
        case 2 => x = currentCell.x + cellWidth
        case 3 => x = currentCell.x - cellWidth
        case 4 => {x = currentCell.x - cellWidth
          y = currentCell.y + cellHeight}
        case 5 => {x = currentCell.x - cellWidth
          y = currentCell.y - cellHeight}
        case 6 => {x = currentCell.x + cellWidth
          y = currentCell.y + cellHeight}
        case 7 => {x = currentCell.x + cellWidth
          y = currentCell.y - cellHeight}
      }

      if (!dots.isOccupied(x, y) && x > 0 && x < dotView.getWidth - DOT_DIAMETER && y > 0 && y < dotView.getHeight - DOT_DIAMETER) {
        currentCell.x = x
        currentCell.y = y
      }

    }
  }

}
