package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * A KeyFrame represents a data type used in the SceneModel that stores information about a shape
 * at a given tick. This, in essence, replaces the function of the Command class.
 */
public class KeyFrame {

  private String id;
  private int tick;
  private Point p;
  private Dimension d;
  private Color c;

  /**
   * Creates a Key Frame which is a short hand version of storing information about mutations on
   * a shape.
   * @param id ID of the shape being mutated.
   * @param tick The tick of the mutation.
   * @param p The new Position of the shape.
   * @param d The new Dimension of the shape.
   * @param c The new Color of the shape.
   */
  public KeyFrame(String id, int tick, Point p, Dimension d, Color c) {
    this.id = id;
    this.tick = tick;
    this.p = p;
    this.d = d;
    this.c = c;
  }

  /**
   * Returns the string id of the key frame.
   * @return a string representing the id.
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the tick that the key frame is displayed at.
   * @return an int representing the tick.
   */
  public int getTick() {
    return tick;
  }

  /**
   * Returns the position of the shape in the key frame.
   * @return a point representing the position.
   */
  public Point getPos() {
    return new Point(p.x, p.y);
  }

  /**
   * Returns the dimension of the shape in the key frame.
   * @return a dimension representing the size.
   */
  public Dimension getDimension() {
    return new Dimension(d.width, d.height);
  }

  /**
   * Returns the color of the shape in the key frame.
   * @return a color representing the color of the shape.
   */
  public Color getColor() {
    return new Color(c.getRGB());
  }

  /**
   * Get the exact amount that a shape should move in the x direction per tick.
   *
   * @return distance per tick
   */
  public double getPosXDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getPos().x, this.p.x, that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should move in the y direction per tick.
   *
   * @return distance per tick
   */
  public double getPosYDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getPos().y, this.p.y, that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should change in size in width per tick.
   *
   * @return change in width per tick
   */
  public double getWidthDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getDimension().width, this.d.width, that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should change in size in height per tick.
   *
   * @return change in height per tick
   */
  public double getHeightDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getDimension().height, this.d.height, that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should change in red color per tick.
   *
   * @return change in red color per tick
   */
  public double getColorRDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getColor().getRed(), this.c.getRed(), that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should change in green color per tick.
   *
   * @return change in green color per tick
   */
  public double getColorGDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getColor().getGreen(), this.c.getGreen(), that.getTick(), tickElapsed);
  }

  /**
   * Get the exact amount that a shape should change in blue color per tick.
   *
   * @return change in blue color per tick
   */
  public double getColorBDelta(KeyFrame that, int tickElapsed) {
    return getDelta(that.getColor().getBlue(), this.c.getBlue(), that.getTick(), tickElapsed);
  }

  /**
   * Calculates delta per tick. Delta is used for change in color, position, and size to see how
   * much it should be changes in one unit of time
   * @param thatVal The value of the other KeyFrame being compared.
   * @param thisVal The value of this KeyFrame being compared.
   * @param thatTick The tick of the other KeyFrame.
   * @param tickElapsed The ticks elapsed since the start of the move.
   * @return
   */
  private double getDelta(int thatVal, int thisVal, int thatTick, int tickElapsed) {
    return ((double) (thatVal - thisVal)) / ((double) (thatTick - this.tick));
  }

  /**
   * Creates a copy of a Key Frame.
   * @return a copy of this Key Frame.
   */
  public KeyFrame copy() {
    return new KeyFrame(id, tick, new Point(p.x, p.y), new Dimension(d.width, d.height), new
            Color(c.getRGB()));
  }
}
