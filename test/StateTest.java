import org.junit.Test;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.util.LinkedHashMap;

import cs3500.animator.model.Oval;
import cs3500.animator.model.Shapes;
import cs3500.animator.model.State;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests State class and all of its public methods.
 */
public class StateTest {

  @Test
  public void createState() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    Shapes oval2;
    oval2 = new Oval("T", new Point(3, 1), new Dimension(100, 150),
            new Color(88, 0, 0));
    LinkedHashMap<String, Shapes> shapes = new LinkedHashMap<>();
    shapes.put("O", oval1);
    shapes.put("T", oval2);
    State state = new State(1, shapes);
    assertEquals(2, state.getShapes().size());
  }

  @Test
  public void testGetTick() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    Shapes oval2;
    oval2 = new Oval("T", new Point(3, 1), new Dimension(100, 150),
            new Color(88, 0, 0));
    LinkedHashMap<String, Shapes> shapes = new LinkedHashMap<>();
    shapes.put("O", oval1);
    shapes.put("T", oval2);
    State state = new State(1, shapes);

    assertEquals(1, state.getTick());
  }

  @Test
  public void testGetShapes() {
    Shapes oval1;
    oval1 = new Oval("O", new Point(1, 1), new Dimension(100, 150),
            new Color(255, 0, 0));
    Shapes oval2;
    oval2 = new Oval("T", new Point(3, 1), new Dimension(100, 150),
            new Color(88, 0, 0));
    LinkedHashMap<String, Shapes> shapes = new LinkedHashMap<>();
    shapes.put("O", oval1);
    shapes.put("T", oval2);
    State state = new State(1, shapes);

    assertNotEquals(shapes, state.getShapes());
    assertEquals(new Point(1, 1), state.getShapes().get("O").getPos());
  }

}