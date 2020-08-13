import org.junit.Test;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;

import cs3500.animator.model.Command;

import static org.junit.Assert.assertEquals;

/**
 * Tests command class and all of its public methods.
 */
public class CommandTest {

  @Test
  public void testCreateCommand() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(5, cmd.getStartTick());
  }

  @Test
  public void testGetId() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals("O", cmd.getId());
  }

  @Test
  public void testGetStartTick() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(5, cmd.getStartTick());
  }

  @Test
  public void testGetStartPos() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(new Point(1, 1), cmd.getStartPos());
  }

  @Test
  public void testGetStartDimension() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(5, 6),
            new Color(255, 0, 0));
    assertEquals(new Dimension(1, 1), cmd.getStartDimension());
  }

  @Test
  public void testGetStartColor() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(0, 255, 0));
    assertEquals(new Color(255, 0, 0), cmd.getStartColor());
  }

  @Test
  public void testGetEndTick() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(10, cmd.getEndTick());
  }

  @Test
  public void testGetEndPos() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(new Point(5, 5), cmd.getEndPos());
  }

  @Test
  public void testGetEndDimension() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(new Dimension(1, 1), cmd.getEndDimension());
  }

  @Test
  public void testGetEndColor() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(5, 5), new Dimension(1, 1),
            new Color(255, 0, 0));
    assertEquals(new Color(255, 0, 0), cmd.getEndColor());
  }

  @Test
  public void testGetDeltas() {
    Command cmd = new Command("O", 5, new Point(1, 1), new Dimension(1, 1),
            new Color(255, 0, 0), 10, new Point(6, 6), new Dimension(9, 9),
            new Color(0, 35, 45));
    assertEquals(1, cmd.getPosXDelta(), .001);
    assertEquals(1, cmd.getPosYDelta(), .001);
    assertEquals(1.6, cmd.getWidthDelta(), .001);
    assertEquals(1.6, cmd.getHeightDelta(), .001);
    assertEquals(-51, cmd.getColorRDelta(), .001);
    assertEquals(7, cmd.getColorGDelta(), .001);
    assertEquals(9, cmd.getColorBDelta(), .001);
  }

}