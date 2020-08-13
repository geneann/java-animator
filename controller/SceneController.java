package cs3500.animator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Controller for the animation stored in a Scene model. Allows user input to mutate information
 * safely within the model.
 */
public interface SceneController {

  /**
   * Starts the instance of the controller and view.
   */
  void start();

  /**
   * Adds a shape to the model.
   * @param id The id of which the Shape should be known as.
   * @param type The type of shape being built (rectangle or
   */
  void addShape(String id, ShapeType type);

  /**
   * Deletes a shape from a model.
   * @param id the id of the shape being deleted.
   */
  void deleteShape(String id);

  /**
   * Adds a key frame to the model.
   * @param id The id of the key frame.
   * @param tick the tick at which the key frame takes place.
   * @param p the point at which the shape is located.
   * @param d the dimension of the shape being affected.
   * @param c the color of the shape being affected.
   */
  void addKeyFrame(String id, int tick, Point p, Dimension d, Color c);

  /**
   * Deletes a key frame from the model.
   * @param id the id of the shape being affected.
   * @param tick the tick of the key frame.
   */
  void deleteKeyFrame(String id, int tick);

  /**
   * Outputs the current model with the new mutations given by the user to a usable SVG file.
   * @param fileName name and location of the file being outputted.
   */
  void outputToSVG(String fileName);

  /**
   * Outputs the current model with the new mutations given by the user to a readable txt file.
   * @param fileName name and location of the file being outputted.
   */
  void outputToText(String fileName);

}
