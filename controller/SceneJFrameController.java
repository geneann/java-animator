package cs3500.animator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import cs3500.animator.model.Scene;
import cs3500.animator.view.SceneEditorVisual;

/**
 * An implementation for the Scene Controller. Allows user input to
 * mutate information safely within the model.
 */
public class SceneJFrameController implements SceneController {

  private Scene model;
  private SceneEditorVisual view;

  /**
   * Mutates information in the Scene given by the user.
   * @param model The model to manipulate information with.
   * @param view The view to send inputs to the controller.
   */
  public SceneJFrameController(Scene model, SceneEditorVisual view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Starts the instance of the controller and view.
   */
  public void start() {
    view.addListener(this);
    view.makeVisible();
    view.display();
  }

  @Override
  public void addShape(String id, ShapeType type) {
    try {
      switch (type) {
        case OVAL:
          model.createOval(id, new Point(0, 0), new Dimension(1, 1),
                  new Color(0));
          break;
        case RECTANGLE:
          model.createRectangle(id, new Point(0, 0), new Dimension(1, 1),
                  new Color(0));
          break;
        default:
          throw new IllegalArgumentException("Shape type is invalid.");
      }
      model.executeAnimation();
      view.throwError("");
    } catch (IllegalArgumentException iae) {
      view.throwError(iae.getMessage());
    }
  }

  @Override
  public void deleteShape(String id) {
    try {
      model.removeShape(id);
      model.executeAnimation();
      view.throwError("");
    } catch (IllegalArgumentException iae) {
      view.throwError(iae.getMessage());
    }
  }

  @Override
  public void addKeyFrame(String id, int tick, Point p, Dimension d, Color c) {
    try {
      model.addKeyFrame(id, tick, p, d, c);
      model.executeAnimation();
      view.throwError("");
    } catch (IllegalArgumentException iae) {
      view.throwError(iae.getMessage());
    }
  }

  @Override
  public void deleteKeyFrame(String id, int tick) {
    try {
      model.removeKeyFrame(id, tick);
      model.executeAnimation();
      view.throwError("");
    } catch (IllegalArgumentException iae) {
      view.throwError(iae.getMessage());
    }
  }

  @Override
  public void outputToSVG(String fileName) {
    //Extra Credit
  }

  @Override
  public void outputToText(String fileName) {
    //Extra Credit
  }


}
