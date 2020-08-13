package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is an abstract class for Shapes. It implements Shapes interface and some common methods for
 * ovals and rectangle. Every shape has a position represented as a Point, size represented as
 * Dimension, color as Color, and a name as a String
 */
public abstract class AbstractShape implements Shapes {

  protected Point pos;
  protected Dimension size;
  protected Color color;
  protected String name;

  @Override
  public void move(Point p) {
    pos.setLocation(p);
  }

  @Override
  public void reSize(Dimension dimension) {
    size.setSize(dimension);
  }

  @Override
  public void reColor(Color color) {
    this.color = color;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Point getPos() {
    return new Point(pos);
  }

  @Override
  public Dimension getSize() {
    return new Dimension(size);
  }

  @Override
  public Color getColor() {
    return new Color(color.getRGB());
  }

  /**
   * Check if the object is null and throws an exception.
   *
   * @throws IllegalArgumentException if the object is null
   */
  protected void checkNull(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }
  }

  /**
   * Checks if a given dimension is non-negative and non-zero.
   *
   * @param size The given size to check before applying the the shape.
   */
  protected void checkInvalidSize(Dimension size) {
    if (size.height < 1 || size.width < 1) {
      throw new IllegalArgumentException("Size cannot be zero or negative");
    }
  }

}