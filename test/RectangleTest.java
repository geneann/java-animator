import org.junit.Test;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;

import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests Rectangle class and all of its public methods.
 */
public class RectangleTest {
  @Test
  public void testCreateRectangle() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals("O", rectangle1.getName());
  }

  @Test
  public void testGetPos() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Point(1, 1), rectangle1.getPos());
  }

  @Test
  public void testGetSize() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Dimension(100, 150), rectangle1.getSize());
  }

  @Test
  public void testGetColor() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Color(255, 0, 0), rectangle1.getColor());
  }

  @Test
  public void testCopy() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertNotEquals(rectangle1, rectangle1.copy());
  }

  @Test
  public void testMove() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    rectangle1.move(new Point(10, 10));
    assertEquals(new Point(10, 10), rectangle1.getPos());
  }

  @Test
  public void testReSize() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    rectangle1.reSize(new Dimension(150, 100));
    assertEquals(new Dimension(150, 100), rectangle1.getSize());
  }

  @Test
  public void testReColor() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    rectangle1.reColor(new Color(0, 255, 0));
    assertEquals(new Color(0, 255, 0), rectangle1.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBadColor() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(100, 150),
            new Color(256, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBadDimension() {
    Shapes rectangle1;
    rectangle1 = new Rectangle("O", new Point(1, 1), new Dimension(-1, 150),
            new Color(255, 0, 0));
  }
}