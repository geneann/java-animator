import org.junit.Test;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.util.HashMap;

import cs3500.animator.model.Scene;
import cs3500.animator.model.SceneModel;
import cs3500.animator.model.State;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests an implementation of Scene and all of its public methods.
 */
public class SceneModelTest {

  @Test
  public void testMoveOvalOnce() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(new Point(5, 5), states.get(5).getShapes().get("O").getPos());
  }

  @Test
  public void testColorChangeOval() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(0, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(0, 0, 0), 10, new Point(1, 1), new Dimension(100, 150),
            new Color(0, 255, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(new Color(0, 255, 0), states.get(10).getShapes().get("O").getColor());
  }

  @Test
  public void testColorChangeOvalNegative() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(1, 1), new Dimension(100, 150),
            new Color(0, 255, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(new Color(0, 255, 0), states.get(10).getShapes().get("O").getColor());
  }

  @Test
  public void testMultiChange() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(150, 100), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(150, 100), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(100, 150), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(new Color(0, 255, 0), states.get(10).getShapes().get("O").getColor());
    assertEquals(new Point(100, 150), states.get(10).getShapes().get("O").getPos());
    assertEquals(new Dimension(150, 100), states.get(10).getShapes().get("O").getSize());
  }

  @Test
  public void testMoveTwoShapesSameTimeMultiChange() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(150, 100), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.createOval("T", new Point(10, 10), new Dimension(50, 100),
            new Color(255, 0, 255));
    scene.command("O", 1, new Point(150, 100), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(100, 150), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("T", 1, new Point(10, 10), new Dimension(50, 100),
            new Color(255, 0, 255), 10, new Point(19, 19), new Dimension(10, 10),
            new Color(255, 100, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(new Color(0, 255, 0), states.get(10).getShapes().get("O").getColor());
    assertEquals(new Point(100, 150), states.get(10).getShapes().get("O").getPos());
    assertEquals(new Dimension(150, 100), states.get(10).getShapes().get("O").getSize());
    assertEquals(new Color(255, 100, 0), states.get(10).getShapes().get("T").getColor());
    assertEquals(new Point(19, 19), states.get(10).getShapes().get("T").getPos());
    assertEquals(new Dimension(10, 10), states.get(10).getShapes().get("T").getSize());
  }

  @Test
  public void testGetStates() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.executeAnimation();
    HashMap<Integer, State> states = scene.getAnimationStates();

    assertEquals(11, states.size());
    assertEquals(5, states.get(5).getShapes().get("O").getPos().x);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNegativeStartTick() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", -1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNegativeEndTick() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", -5, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), -1, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateEndTickLessThanStart() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 5, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 4, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidId() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("K", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
  }

  @Test
  public void testToString() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("O", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 20, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    scene.createRectangle("T", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("T", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("T", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 30, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    assertEquals("shape O ellipse\n" +
            "motion O 1 1 1 100 150 255 0 0 10 10 10 150 100 0 255 0\n" +
            "motion O 10 10 10 150 100 0 255 0 20 10 20 150 100 0 255 5\n" +
            "motion O 20 10 20 150 100 0 255 5 30 10 20 150 100 0 255 5\n" +
            "shape T rectangle\n" +
            "motion T 1 1 1 100 150 255 0 0 10 10 10 150 100 0 255 0\n" +
            "motion T 10 10 10 150 100 0 255 0 30 10 20 150 100 0 255 5\n", scene.toString());
  }

  @Test
  public void testRemoveCommand() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("O", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 20, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.removeCommand("O", 10, 20);
    assertEquals("shape O ellipse\n" +
            "motion O 1 1 1 100 150 255 0 0 10 10 10 150 100 0 255 0\n", scene.toString());
  }

  @Test
  public void testRemoveShape() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("O", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 20, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    scene.createRectangle("T", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("T", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("T", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 30, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    scene.removeShape("T");
    assertEquals("shape O ellipse\n" +
            "motion O 1 1 1 100 150 255 0 0 10 10 10 150 100 0 255 0\n" +
            "motion O 10 10 10 150 100 0 255 0 20 10 20 150 100 0 255 5\n", scene.toString());
  }

  @Test
  public void testGetShapes() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("O", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 20, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    scene.createRectangle("T", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("T", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("T", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 30, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    assertEquals(2, scene.getShapes().size());
  }

  @Test
  public void testGetCommands() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("O", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 20, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    scene.createRectangle("T", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("T", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.command("T", 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0), 30, new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));

    assertEquals(4, scene.getCommands().size());
  }

  @Test
  public void testSetAndGetCanvas() {
    Scene scene = new SceneModel();
    scene.setCanvas(1, 2, 3, 4);
    assertTrue(scene.getCanvas().get(1).equals(2));
  }

  @Test
  public void testAddKeyFrame() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.addKeyFrame("O", 10,  new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    assertEquals("shape O ellipse\n" +
            "motion O 10 10 20 150 100 0 255 5 10 10 20 150 100 0 255 5\n", scene.toString());
  }

  @Test
  public void testRemoveKeyFrame() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.addKeyFrame("O", 10,  new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.removeKeyFrame("O", 10);
    assertEquals("shape O ellipse\n", scene.toString());
  }

  @Test
  public void testAddOverlapKeyFrame() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.addKeyFrame("O", 10,  new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.addKeyFrame("O", 5,  new Point(5, 10), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.addKeyFrame("O", 7,  new Point(5, 14), new Dimension(150, 100),
            new Color(250, 255, 5));
    assertEquals("shape O ellipse\n" +
            "motion O 5 5 10 150 100 0 255 5 7 5 14 150 100 250 255 5\n" +
            "motion O 7 5 14 150 100 250 255 5 10 10 20 150 100 0 255 5\n", scene.toString());
  }

  @Test
  public void testAddKeyFramesAndCommands() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.addKeyFrame("O", 7,  new Point(5, 14), new Dimension(150, 100),
            new Color(250, 255, 5));
    scene.addKeyFrame("O", 14,  new Point(5, 20), new Dimension(150, 100),
            new Color(250, 255, 5));
    assertEquals("shape O ellipse\n" +
            "motion O 1 1 1 100 150 255 0 0 7 5 14 150 100 250 255 5\n" +
            "motion O 7 5 14 150 100 250 255 5 10 10 10 150 100 0 255 0\n" +
            "motion O 10 10 10 150 100 0 255 0 14 5 20 150 100 250 255 5\n", scene.toString());
  }

  @Test
  public void testAddKeyFramesAtEnd() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.command("O", 1, new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0), 10, new Point(10, 10), new Dimension(150, 100),
            new Color(0, 255, 0));
    scene.addKeyFrame("O", 14,  new Point(5, 14), new Dimension(150, 100),
            new Color(250, 255, 5));
    assertEquals("shape O ellipse\n" +
            "motion O 1 1 1 100 150 255 0 0 10 10 10 150 100 0 255 0\n" +
            "motion O 10 10 10 150 100 0 255 0 14 5 14 150 100 250 255 5\n", scene.toString());
  }

  @Test
  public void testRemoveMiddleKeyFrame() {
    Scene scene = new SceneModel();
    scene.createOval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    scene.addKeyFrame("O", 10,  new Point(0, 0), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.addKeyFrame("O", 0,  new Point(10, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.addKeyFrame("O", 20,  new Point(30, 20), new Dimension(150, 100),
            new Color(0, 255, 5));
    scene.removeKeyFrame("O", 10);
    assertEquals("shape O ellipse\n" +
            "motion O 0 10 20 150 100 0 255 5 20 30 20 150 100 0 255 5\n", scene.toString());
  }
}