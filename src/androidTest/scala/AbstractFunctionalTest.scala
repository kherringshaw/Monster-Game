package edu.luc.etl.cs313.scala.uidemo

import android.graphics.Color
import org.junit.Assert._
import org.junit.Test

/**
 * An abstract GUI-based functional test for the hello app.
 * This follows the XUnit Testcase Superclass pattern.
 */
trait AbstractFunctionalTest {

  /**
   * The activity to be provided by concrete subclasses of this test.
   */
  protected def activity(): MainActivity

  @Test def testActivityExists() {
    assertNotNull(activity)
  }


  //Dot(x, y, color, diameter, vulnerable)

  @Test def addDots: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count()) //asserts empty list
    dots.addDot(60, 60, Color.BLACK, 30, 0)
    dots.addDot(120, 120, Color.BLACK, 30, 0)
    dots.addDot(0, 0, Color.BLACK, 30, 1)
    dots.addDot(300, 300, Color.BLACK, 30, 1)
    assertEquals(4, dots.Count())
  }

  //Dot(x, y, color, diameter, vulnerable)
  //check to see remove dots

  @Test def removeRedDots: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count()) //asserts empty list
    dots.addDot(60, 60, Color.RED, DOT_DIAMETER, 0)
    dots.addDot(120, 120, Color.RED, DOT_DIAMETER, 0)
    dots.removeDot(60, 60, DOT_DIAMETER)
    dots.removeDot(120, 120, DOT_DIAMETER)
    assertEquals(0, dots.Count())
  }

  //Dot(x, y, color, diameter, vulnerable)
  //check to see remove dots -- should be false

  @Test def notRemoveBlackDots: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count()) //asserts empty list
    dots.addDot(60, 60, Color.BLACK, DOT_DIAMETER, 0)
    dots.addDot(120, 120, Color.BLACK, DOT_DIAMETER, 0)
    dots.removeDot(60, 60, DOT_DIAMETER)
    dots.removeDot(120, 120, DOT_DIAMETER)
    assertEquals(2, dots.Count())
  }


  /** change color working*/

  @Test def colorVulnerable: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count())
    dots.addDot(0, 0, Color.RED, 30, 0)
    dots.changeVulnerable
    for (dot <- dots.getDots()) {
      assertEquals(Color.BLACK, dot.color)}
  }

  /** check to see if cell is empty*/
  @Test def isOccupied: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count())
    dots.addDot(0, 0, 1, 30, 0)
    assertTrue(dots.isOccupied(0, 0))
  }

  /** check to see if cell is occupied*/
  @Test def isUnoccupied: Unit = {
    var dots = new model.Dots
    assertEquals(0, dots.Count())
    dots.addDot(0, 0, 1, 30, 0)
    assertFalse(dots.isOccupied(50, 50))
  }

  // TODO test short and long click and resulting toasts
}
