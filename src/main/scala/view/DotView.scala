package edu.luc.etl.cs313.scala.uidemo
package view


import android.R.drawable
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context
import android.graphics.{Canvas, Color, Paint}
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.View
import scala.util.Random
import model._


/**
 * I see spots!
 *
 * @param context
 * @param attrs
 * @param defStyle
 *
 * @author <a href="mailto:android@callmeike.net">Blake Meike</a>
 */
class DotView(context: Context, attrs: AttributeSet, defStyle: Int) extends View(context, attrs, defStyle) {

  { setFocusableInTouchMode(true) }

  /** The model underlying this view. */
  private var dots: Dots = _

  val minion1 = BitmapFactory.decodeResource(getResources, R.drawable.minionfront2)
  val minionbad = BitmapFactory.decodeResource(getResources, R.drawable.minionfront2bad)
  val jail = BitmapFactory.decodeResource(getResources, R.drawable.javajail2)


  /** @param context the rest of the application */
  def this(context: Context) = {
    this(context, null, 0)
    setFocusableInTouchMode(true)
  }

  /**
   * @param context
   * @param attrs
   */
  def this(context: Context, attrs: AttributeSet) = {
    this(context, attrs, 0)
    setFocusableInTouchMode(true)
  }

  /**
   * Injects the model underlying this view.
   *
   * @param dots
   * */
  def setDots(dots: Dots): Unit = this.dots = dots

  /** @see android.view.View#onDraw(android.graphics.Canvas) */
  override protected def onDraw(canvas: Canvas): Unit = {
    val paint = new Paint
    paint.setStyle(Style.STROKE)
    canvas.drawRect(0, 0, getWidth - 1, getHeight - 1, paint)

    if (null == dots) return

    paint.setStyle(Style.FILL)
    for (dot <- dots.getDots) {
      paint.setColor(dot.color)
      //canvas.drawCircle(dot.x, dot.y, dot.diameter, paint)
      //canvas.drawRect(dot.x, dot.y, dot.x+25, dot.y+25, paint)
      if (dot.color==Color.BLACK)
      canvas.drawBitmap(minion1, dot.x, dot.y, paint)
      if (dot.color==Color.RED)
        canvas.drawBitmap(minionbad, dot.x, dot.y, paint)
    }
    if(dots.isGameOver){
      canvas.drawBitmap(jail, (getWidth-jail.getWidth)/2, (getHeight-jail.getHeight)/2, paint)
    }
  }

}