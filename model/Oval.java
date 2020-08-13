package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is a class representing a shape of Oval. It extends an abstract class of shapes. Every oval
 * has a name, position, size, and color
 */
public class Oval extends AbstractShape {

  /**
   * Constructor for a Oval.
   *
   * @param name  name of the oval
   * @param pos   position of the oval
   * @param size  size of the oval
   * @param color color of the oval
   */
  public Oval(String name, Point pos, Dimension size, Color color) {
    checkNull(pos);
    checkNull(size);
    checkNull(color);
    checkInvalidSize(size);

    this.name = name;
    this.pos = pos;
    this.size = size;
    this.color = color;
  }

  @Override
  public Shapes copy() {
    return new Oval(name, this.getPos(), this.getSize(), this.getColor());
  }

  @Override
  public String typeStr() {
    return "ellipse";
  }
}
