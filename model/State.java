package cs3500.animator.model;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This is the class for State in an easy animator. It represents a state at every tick. Every state
 * has a tick and a map of shapes.
 */
public class State {

  private int tick;
  private HashMap<String, Shapes> shapes;

  /**
   * Constructor for a State.
   *
   * @param tick   time of state
   * @param shapes a map of all the shapes at the current state
   */
  public State(int tick, LinkedHashMap<String, Shapes> shapes) {

    this.tick = tick;
    this.shapes = shapes;

  }

  /**
   * Get a time of the state.
   *
   * @return the tick of the state
   */
  public int getTick() {
    return tick;
  }

  /**
   * Get all the shapes of the state.
   *
   * @return a hashmap with all the shapes
   */
  public LinkedHashMap<String, Shapes> getShapes() {
    LinkedHashMap<String, Shapes> copyShapes = new LinkedHashMap<>();
    for (String s : shapes.keySet()) {
      copyShapes.put(s, shapes.get(s).copy());
    }
    return copyShapes;
  }
}
