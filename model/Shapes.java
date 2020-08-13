package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is the interface for Shapes in an easy animator.
 */
public interface Shapes {

  /**
   * Move a shape from its initial position to the given destination position.
   *
   * @param pos the destination point of a shape
   */
  void move(Point pos);

  /**
   * Change a size of a shape to a given dimension.
   *
   * @param dimension the final size of a shape
   */
  void reSize(Dimension dimension);

  /**
   * Change a color of a shape to a given one.
   *
   * @param color the result color of the shape
   */
  void reColor(Color color);

  /**
   * Return a String with the name of a shape.
   *
   * @return the name of the shape
   */
  String getName();

  /**
   * Return a Point with the position of a shape.
   *
   * @return the position of the shape
   */
  Point getPos();

  /**
   * Return a Dimension with the size of a shape.
   *
   * @return the size of the shape
   */
  Dimension getSize();

  /**
   * Return a Color with the color of a shape in RGB form.
   *
   * @return the color of the shape
   */
  Color getColor();

  /**
   * Creates a copy of an instance of Shapes.
   *
   * @return a copy of the shape
   */
  Shapes copy();

  /**
   * Returns the type of shape it is in a string.
   *
   * @return a string of shape type.
   */
  String typeStr();
}
