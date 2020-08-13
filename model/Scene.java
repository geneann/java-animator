package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is the interface for Scene in an easy animator. Scene represents the plane on which the
 * Shapes are stored and changed. Shapes objects are added and stored in a HashMap for easy access
 * using their IDs to mutate them.
 */
public interface Scene extends ReadOnlyScene {

  /**
   * Sets the canvas bounds to be later used by the view.
   *
   * @param x      leftmost line on the screen.
   * @param y      topmost line on the screen.
   * @param width  the width of the canvas.
   * @param height the height of the canvas.
   */
  void setCanvas(int x, int y, int width, int height);

  /**
   * Creates a shape of an oval with a given name, position, size, and color.
   *
   * @param name      name of the oval
   * @param pos       position of the oval
   * @param dimension size of the oval
   * @param color     color of the oval
   */
  void createOval(String name, Point pos, Dimension dimension, Color color);

  /**
   * Creates a shape of a rectangle with a given name, position, size, and color.
   *
   * @param name      name of the rectangle
   * @param pos       position of the rectangle
   * @param dimension size of the rectangle
   * @param color     color of the rectangle
   */
  void createRectangle(String name, Point pos, Dimension dimension, Color color);

  /**
   * Removes a shape from a scene given an id.
   *
   * @param id The id of the shape to remove.
   */
  void removeShape(String id);

  /**
   * Adds a command to a list of commands for a given scene.
   *
   * @param id             id of the command
   * @param start          start time for executing the command
   * @param startPos       start position of the shape for the command.
   * @param startDimension start size of the shape for the command.
   * @param startColor     start color of the shape for the command.
   * @param end            end time for executing the command
   * @param endPos         final position of the shape after executing the command
   * @param endDimension   final size of the shape after executing the command
   * @param endColor       final color of the shape after executing the command
   */
  void command(String id, int start, Point startPos, Dimension startDimension,
               Color startColor, int end, Point endPos, Dimension endDimension,
               Color endColor);

  /**
   * Removes a command to a list of commands for a given scene.
   *
   * @param id    of the shape in the command.
   * @param start start time of the command.
   * @param end   end time of the command.
   */
  void removeCommand(String id, int start, int end);

  /**
   * Execute all the commands from a list to create animation.
   */
  void executeAnimation();

  /**
   * Creates a key frame for an animation to display at a given tick.
   *
   * @param id   the id of the shape at a key frame.
   * @param tick the tick of the key frame.
   * @param p    the position of the shape during the key frame.
   * @param d    the dimension of the shape during the key frame.
   * @param c    the color of the shape during the key frame.
   */
  void addKeyFrame(String id, int tick, Point p, Dimension d, Color c);

  /**
   * Deletes a key frame for an animation to display at a given tick.
   *
   * @param id   the id of the shape at a key frame.
   * @param tick the tick of the key frame.
   */
  void removeKeyFrame(String id, int tick);

}
