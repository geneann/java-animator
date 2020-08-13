package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is a class representing a shape of Rectangle. It extends an abstract class of shapes. Every
 * oval has a name, position, size, and color
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructor for a Rectangle.
   *
   * @param name  name of the rectangle
   * @param pos   position of the rectangle
   * @param size  size of the rectangle
   * @param color color of the rectangle
   */
  public Rectangle(String name, Point pos, Dimension size, Color color) {
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
    return new Rectangle(name, this.getPos(), this.getSize(), this.getColor());
  }

  @Override
  public String typeStr() {
    return "rectangle";
  }
}