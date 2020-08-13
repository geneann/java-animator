package cs3500.animator.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Creates a read-only version of a Scene to read information for the view.
 */
public interface ReadOnlyScene {

  /**
   * Returns the animation in a string format specified on the requirements.
   *
   * @return a string that represents the animation as a whole.
   */
  String toString();

  /**
   * Get a hashmap of all the states every tick of animation.
   *
   * @return a map with all the animation states
   */
  HashMap<Integer, State> getAnimationStates();

  /**
   * Gets a hashmap of all of the states in the animation.
   *
   * @return a map of shapes with their ids as keys.
   */
  LinkedHashMap<String, Shapes> getShapes();

  /**
   * Gets a list of commands being used in the animation.
   *
   * @return a list of commands.
   */
  List<Command> getCommands();

  /**
   * Gets the canvas boundaries to be used in the view.
   *
   * @return a list of 4 boundary values.
   */
  List<Integer> getCanvas();

  /**
   * Gets the key frames of a scene in the form of a hashmap with a shape id as a key
   * and the value is a list of all the key frames per that shape.
   *
   * @return A HashMap of key frames.
   */
  LinkedHashMap<String, List<KeyFrame>> getKeyFrames();


}
