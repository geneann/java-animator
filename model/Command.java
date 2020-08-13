package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * This is the class for Command in an easy animator. It represents a command for a shape. Every
 * command has an id represented with a String, start and end time, start and end positions, start
 * snd end dimensions, which is the initial and final sizes of the shape, initial and final colors
 */
public class Command {

  private String id;
  private int start;
  private Point startPos;
  private Dimension startDimension;
  private Color startColor;
  private int end;
  private Point endPos;
  private Dimension endDimension;
  private Color endColor;

  /**
   * Constructor for a Command.
   *
   * @param id             type of a command
   * @param start          start time
   * @param startPos       initial position
   * @param startDimension initial size
   * @param startColor     initial color
   * @param end            end time
   * @param endPos         final position
   * @param endDimension   final size
   * @param endColor       final color
   */
  public Command(String id, int start, Point startPos, Dimension startDimension, Color startColor,
                 int end, Point endPos, Dimension endDimension, Color endColor) {

    // Add error checking for invalid moves here

    this.id = id;
    this.start = start;
    this.startPos = startPos;
    this.startDimension = startDimension;
    this.startColor = startColor;
    this.end = end;
    this.endPos = endPos;
    this.endDimension = endDimension;
    this.endColor = endColor;
  }

  /**
   * Return a String with id of the command.
   *
   * @return the id of the command
   */
  public String getId() {
    return id;
  }

  /**
   * Get an int with start time of the command.
   *
   * @return the start tick
   */
  public int getStartTick() {
    return start;
  }

  public Point getStartPos() {
    return new Point(startPos);
  }

  public Dimension getStartDimension() {
    return new Dimension(startDimension);
  }

  public Color getStartColor() {
    return new Color(startColor.getRGB());
  }

  /**
   * Get an int with end time of the command.
   *
   * @return the end tick
   */
  public int getEndTick() {
    return end;
  }

  /**
   * Get the final position of the shape from the command.
   *
   * @return the final position from the command
   */
  public Point getEndPos() {
    return new Point(endPos);
  }

  /**
   * Get a final size of the shape after performing the command.
   *
   * @return the final size of the shape
   */
  public Dimension getEndDimension() {
    return new Dimension(endDimension);
  }

  /**
   * Get a final color of a shape after performing the command.
   *
   * @return the final color of a shape
   */
  public Color getEndColor() {
    return new Color(endColor.getRGB());
  }

  /**
   * Get the exact amount that a shape should move in the x direction per tick.
   *
   * @return distance per tick
   */
  public double getPosXDelta() {
    return getDelta(this.startPos.x, this.endPos.x);
  }

  /**
   * Get the exact amount that a shape should move in the y direction per tick.
   *
   * @return distance per tick
   */
  public double getPosYDelta() {
    return getDelta(this.startPos.y, this.endPos.y);
  }

  /**
   * Get the exact amount that a shape should change in size in width per tick.
   *
   * @return change in width per tick
   */
  public double getWidthDelta() {
    return getDelta(this.startDimension.width, this.endDimension.width);
  }

  /**
   * Get the exact amount that a shape should change in size in height per tick.
   *
   * @return change in height per tick
   */
  public double getHeightDelta() {
    return getDelta(this.startDimension.height, this.endDimension.height);
  }

  /**
   * Get the exact amount that a shape should change in red color per tick.
   *
   * @return change in red color per tick
   */
  public double getColorRDelta() {
    return getDelta(this.startColor.getRed(), this.endColor.getRed());
  }

  /**
   * Get the exact amount that a shape should change in green color per tick.
   *
   * @return change in green color per tick
   */
  public double getColorGDelta() {
    return getDelta(this.startColor.getGreen(), this.endColor.getGreen());
  }

  /**
   * Get the exact amount that a shape should change in blue color per tick.
   *
   * @return change in blue color per tick
   */
  public double getColorBDelta() {
    return getDelta(this.startColor.getBlue(), this.endColor.getBlue());
  }

  /**
   * Calculates delta per tick. Delta is used for change in color, position, and size to see how
   * much it should be changes in one unit of time
   *
   * @param startVal the initial value
   * @param endVal   the final value
   * @return delta per tick
   */
  private double getDelta(int startVal, int endVal) {
    return ((double) (endVal - startVal)) / ((double) (end - start));
  }

  /**
   * Returns a copy of this commands with no memory references to its objects.
   *
   * @return this Command being copied.
   */
  public Command copy() {
    return new Command(id, start, new Point(startPos.x, startPos.y),
            new Dimension(startDimension.width, startDimension.height),
            new Color(startColor.getRGB()), end, new Point(endPos.x, endPos.y),
            new Dimension(endDimension.width, endDimension.height),
            new Color(endColor.getRGB()));
  }
}
