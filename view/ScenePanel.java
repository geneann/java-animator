package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import cs3500.animator.model.ReadOnlyScene;
import cs3500.animator.model.Shapes;
import cs3500.animator.model.State;

/**
 * Creates a panel in the Scene to draw shapes and their attributes on. JPanel is the class we
 * extend in order to achieve this functionality.
 */
public class ScenePanel extends JPanel {

  private SceneViewVisual view;
  private int xDifference;
  private int yDifference;
  private ReadOnlyScene model;
  private boolean displayNames;


  /**
   * Creates an instance of ScenePanel to mutate with shapes and their attributes.
   *
   * @param model       The model to take information from.
   * @param view        The view to get information about the JFrame and ticks.
   * @param xDifference The x-position at which the window is viewing the animation.
   * @param yDifference The y-position at which the window is viewing the animation.
   */
  public ScenePanel(ReadOnlyScene model, SceneViewVisual view, int xDifference, int yDifference) {
    this.model = model;
    this.view = view;
    this.xDifference = xDifference;
    this.yDifference = yDifference;
    this.displayNames = false;

  }

  @Override
  public void paintComponent(Graphics g) {
    HashMap<Integer, State> states = model.getAnimationStates();
    for (Shapes s : states.get(view.getTick()).getShapes().values()) {
      switch (s.typeStr()) {
        case "rectangle":
          g.setColor(s.getColor());
          g.fillRect(s.getPos().x - xDifference, s.getPos().y - yDifference,
                  s.getSize().width, s.getSize().height);
          if (displayNames) {
            g.setColor(Color.black);
            g.drawString(s.getName(), (int)(s.getPos().x + (.5 * s.getSize().width)) - xDifference,
                    (int)(s.getPos().y + (.5 * s.getSize().height)) - yDifference);
          }
          break;
        case "ellipse":
          g.setColor(s.getColor());
          g.fillOval(s.getPos().x - xDifference, s.getPos().y - yDifference,
                  s.getSize().width, s.getSize().height);
          if (displayNames) {
            g.setColor(Color.black);
            g.drawString(s.getName(), (int)(s.getPos().x + (.5 * s.getSize().width)) - xDifference,
                    (int)(s.getPos().y + (.5 * s.getSize().height)) - yDifference);
          }
          break;
        default:
          throw new IllegalStateException("Invalid shape to draw");
      }
    }
  }

  /**
   * Simple function to either display the shape IDs or not.
   * @param display a boolean whether to display shape names.
   */
  public void displayNames(boolean display) {
    this.displayNames = display;
  }

}
