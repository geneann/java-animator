import org.junit.Test;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;

import cs3500.animator.model.Oval;
import cs3500.animator.model.Shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests Oval class and all of its public methods.
 */
public class OvalTest {
  @Test
  public void testCreateOval() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals("O", oval1.getName());
  }

  @Test
  public void testGetPos() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Point(1, 1), oval1.getPos());
  }

  @Test
  public void testGetSize() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Dimension(100, 150), oval1.getSize());
  }

  @Test
  public void testGetColor() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertEquals(new Color(255, 0, 0), oval1.getColor());
  }

  @Test
  public void testCopy() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    assertNotEquals(oval1, oval1.copy());
  }

  @Test
  public void testMove() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    oval1.move(new Point(10, 10));
    assertEquals(new Point(10, 10), oval1.getPos());
  }

  @Test
  public void testReSize() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    oval1.reSize(new Dimension(150, 100));
    assertEquals(new Dimension(150, 100), oval1.getSize());
  }

  @Test
  public void testReColor() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    oval1.reColor(new Color(0, 255, 0));
    assertEquals(new Color(0, 255, 0), oval1.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBadColor() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(256, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBadDimension() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(-1, 150),
            new Color(255, 0, 0));
  }

}