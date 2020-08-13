package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import cs3500.animator.controller.SceneController;
import cs3500.animator.model.Command;
import cs3500.animator.model.ReadOnlyScene;

/**
 * View for Scene model for the JFrame implementation. Uses help of the model in order to display
 * the correct information on the screen.
 */
public class SceneViewVisualImpl extends JFrame implements SceneViewVisual {

  protected final ScenePanel panel;
  protected int tickPerSec;
  protected int tick = 0;
  protected int lastTick;
  protected Timer timer;
  protected SceneController listener;

  /**
   * Creates an instance of SceneViewImpl to read and mutate information from.
   *
   * @param windowTitle The title of the window.
   * @param tps         The ticks per second of the animation.
   * @param m           The model to read information from.
   */
  public SceneViewVisualImpl(String windowTitle, int tps, ReadOnlyScene m) {
    super(windowTitle);

    this.lastTick = getLastCommandTime(m);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.tickPerSec = tps;

    this.panel = new ScenePanel(m, this, m.getCanvas().get(0), m.getCanvas().get(1));
    this.panel.setPreferredSize(getMaxDimension(m));

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(m.getCanvas().get(2), m.getCanvas().get(3)));

    JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

    container.add(scrollPane);

    this.setContentPane(container);
    this.pack();
  }

  @Override
  public void display() {
    timer = new Timer(1000 / tickPerSec, e -> {
      tick++;
      this.repaint();
      this.panel.removeAll();
      this.panel.repaint();

      if (tick == lastTick) {
        timer.stop();
      }
    });
    timer.start();
  }

  @Override
  public int getTick() {
    return tick;
  }

  @Override
  public void makeVisible() {
    super.setVisible(true);
  }

  /**
   * Gets the last command time (last tick) of a given model.
   *
   * @param m The model to read information from.
   * @return An int representing the last tick of the animation.
   */
  private int getLastCommandTime(ReadOnlyScene m) {
    int time = 0;
    for (Command e : m.getCommands()) {
      if (e.getEndTick() > time) {
        time = e.getEndTick();
      }
    }
    return time;
  }

  /**
   * Gets the maximum dimension of a given model (farthest command in the +x +y direction).
   *
   * @param m The model to read information from.
   * @return A dimension representing the furthest command from (0, 0).
   */
  private Dimension getMaxDimension(ReadOnlyScene m) {
    int xDifference = m.getCanvas().get(0);
    int yDifference = m.getCanvas().get(1);

    Dimension maxDim = new Dimension(0, 0);
    for (Command c : m.getCommands()) {
      if (c.getStartDimension().width + c.getStartPos().x > maxDim.width) {
        maxDim.setSize(c.getStartDimension().width + c.getStartPos().x
                - xDifference, maxDim.height);
      }
      if (c.getStartDimension().height + c.getStartPos().y > maxDim.height) {
        maxDim.setSize(maxDim.width, c.getStartDimension().height
                + c.getStartPos().y - yDifference);
      }
      if (c.getEndDimension().width + c.getEndPos().x > maxDim.width) {
        maxDim.setSize(c.getEndDimension().width + c.getEndPos().x
                - xDifference, maxDim.height);
      }
      if (c.getEndDimension().height + c.getEndPos().y > maxDim.height) {
        maxDim.setSize(maxDim.width, c.getEndDimension().height
                + c.getEndPos().y - yDifference);
      }
    }
    return maxDim;
  }
}
