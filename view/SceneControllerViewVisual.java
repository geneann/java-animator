package cs3500.animator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import cs3500.animator.controller.SceneController;
import cs3500.animator.controller.ShapeType;
import cs3500.animator.model.ReadOnlyScene;

/**
 * Creates a visual with an editor that uses a controller to make mutations to the model.
 */
public class SceneControllerViewVisual extends SceneViewVisualImpl implements SceneEditorVisual {

  private boolean paused = false;
  private boolean loop = false;
  private JLabel tpsLabel;
  private JLabel tickLabel;
  private JLabel errorMsg;

  /**
   * Creates an instance of SceneViewImpl to read and mutate information from.
   *
   * @param windowTitle The title of the window.
   * @param tps         The ticks per second of the animation.
   * @param m           The model to read information from.
   */
  public SceneControllerViewVisual(String windowTitle, int tps, ReadOnlyScene m) {
    super(windowTitle, tps, m);
    JPanel buttonPanel = buildButtonPanel();
    JPanel inputPanel = buildInputPanel();

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    super.getContentPane().add(buttonPanel);
    super.getContentPane().add(inputPanel);
    super.pack();
  }

  // Builds button panel for view (No controller mutations)
  // Only allows modifications to the information already being read. (Speed, pause, etc.)
  private JPanel buildButtonPanel() {

    JPanel buttonPanel = new JPanel();
    JButton pause = new JButton("Pause/Play");
    pause.addActionListener(e -> {
      pauseAnimation();
    });
    buttonPanel.add(pause);

    JButton restart = new JButton("Restart");
    restart.addActionListener(c -> {
      restartAnimation();
    });
    buttonPanel.add(restart);

    JCheckBox checkBox = new JCheckBox("Loop?");
    checkBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == 1) {
          loop = true;
          if (tick == lastTick) {
            tick = 0;
            changeTPS();
          }
        } else {
          loop = false;
        }
      }
    });
    buttonPanel.add(checkBox);

    JCheckBox displayNames = new JCheckBox("Display IDs?");
    displayNames.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == 1) {
          panel.displayNames(true);
        } else {
          panel.displayNames(false);
        }
      }
    });
    buttonPanel.add(displayNames);

    tpsLabel = new JLabel();
    tpsLabel.setText("Ticks per second: " + tickPerSec);
    buttonPanel.add(tpsLabel);

    tickLabel = new JLabel();
    tickLabel.setText("Current tick: " + tick);
    buttonPanel.add(tickLabel);


    JButton increaseTPS = new JButton("Increase TPS");
    increaseTPS.addActionListener(e -> {
      tickPerSec += 1;
      changeTPS();
      tpsLabel.setText("Ticks per second: " + tickPerSec);
    });
    buttonPanel.add(increaseTPS);

    JButton decreaseTPS = new JButton("Decrease TPS");
    decreaseTPS.addActionListener(e -> {
      tickPerSec -= 1;
      changeTPS();
      tpsLabel.setText("Ticks per second: " + tickPerSec);
    });
    buttonPanel.add(decreaseTPS);

    return buttonPanel;
  }

  // Builds input panel (with controller mutation).
  private JPanel buildInputPanel() {
    JPanel inputPanel = new JPanel();

    List<JTextField> createFrameFields = new ArrayList<>();
    JPanel createFrame = buildTextArea(9, Arrays.asList("   ID   ", "  Tick  ", " Pos(x) ",
            " Pos(y) ", "Size(x)", "Size(y)", "Color(R)", "Color(G)", "Color(B)"), 2,
            createFrameFields);
    JButton createFrameButton = new JButton("Create/Edit Frame");
    createFrameButton.addActionListener(e -> {
      listener.addKeyFrame(createFrameFields.get(0).getText(),
              Integer.parseInt(createFrameFields.get(1).getText()),
              new Point(Integer.parseInt(createFrameFields.get(2).getText()),
                      Integer.parseInt(createFrameFields.get(3).getText())), new Dimension(
                      Integer.parseInt(createFrameFields.get(4).getText()),
                      Integer.parseInt(createFrameFields.get(5).getText())), new Color(
                      Integer.parseInt(createFrameFields.get(6).getText()),
                      Integer.parseInt(createFrameFields.get(7).getText()),
                      Integer.parseInt(createFrameFields.get(8).getText())));
    });

    createFrame.add(createFrameButton);
    createFrame.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
    inputPanel.add(createFrame);

    List<JTextField> textDeleteFields = new ArrayList<>();
    JPanel deleteFrameTextArea = buildTextArea(2, Arrays.asList("ID", "Tick"),
            3,  textDeleteFields);
    JButton deleteFrame = new JButton("Delete Frame");
    deleteFrame.addActionListener(e -> {
      listener.deleteKeyFrame(textDeleteFields.get(0).getText(),
              Integer.parseInt(textDeleteFields.get(1).getText()));
    });

    deleteFrameTextArea.add(deleteFrame);
    deleteFrameTextArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
    inputPanel.add(deleteFrameTextArea);

    List<JTextField> shapeDeleteFields = new ArrayList<>();
    JPanel deleteShapeArea = buildTextArea(1, Arrays.asList("ID"), 3,
            shapeDeleteFields);
    JButton deleteShape = new JButton("Delete Shape");
    deleteShape.addActionListener(e -> {
      listener.deleteShape(shapeDeleteFields.get(0).getText());
    });

    deleteShapeArea.add(deleteShape);
    deleteShapeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
    inputPanel.add(deleteShapeArea);

    List<JTextField> shapeCreateFields = new ArrayList<>();
    JPanel createShapeArea = buildTextArea(1, Arrays.asList("ID"), 3,
            shapeCreateFields);
    String[] shapeTypes = { "Rectangle", "Oval" };
    JComboBox shapeList = new JComboBox(shapeTypes);
    shapeList.setSelectedIndex(0);

    JButton createShape = new JButton("Create Shape");
    createShape.addActionListener(e -> {
      ShapeType type;
      if (shapeList.getSelectedItem().equals("Rectangle")) {
        type = ShapeType.RECTANGLE;
      } else {
        type = ShapeType.OVAL;
      }
      listener.addShape(shapeCreateFields.get(0).getText(), type);
    });

    createShapeArea.add(createShape);
    createShapeArea.add(shapeList);
    createShapeArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
    inputPanel.add(createShapeArea);

    errorMsg = new JLabel();
    errorMsg.setBackground(Color.RED);

    inputPanel.add(errorMsg);

    return inputPanel;
  }

  // builder method for text areas to quickly produce fields and labels.
  private JPanel buildTextArea(int numberOfFields, List<String> labelList, int width,
                               List<JTextField> textList) {
    int columnsPerRow = 3;

    GridLayout layout = new GridLayout(0, 4);
    JPanel textArea = new JPanel(new GridLayout(0, 4));

    for (int i = 0; i < numberOfFields; i++) {
      textList.add(new JTextField(width));
      textArea.add(new JLabel(labelList.get(i) + ":"));
      textArea.add(textList.get(i));
    }

    int numberOfExtraFields;
    if (numberOfFields % 2 == 0) {
      numberOfExtraFields = 3;
    } else {
      numberOfExtraFields = 1;
    }

    for (int i = 0; i < numberOfExtraFields; i++) {
      textArea.add(new JLabel(" "));
    }

    return textArea;
  }

  @Override
  public void display() {
    timer = playTimer();
    timer.start();
  }

  // Creates a timer to play the animation with.
  private Timer playTimer() {
    return new Timer(1000 / tickPerSec, e -> {
      tick++;
      tickLabel.setText("Current tick: " + tick);
      this.repaint();
      this.panel.removeAll();
      this.panel.repaint();

      if (loop && tick == lastTick) {
        timer.stop();
        restartAnimation();
      } else if (tick == lastTick) {
        timer.stop();
      }
    });
  }

  // Pauses the animation.
  private void pauseAnimation() {
    if (tick == lastTick) {
      return;
    }
    if (paused) {
      paused = false;
      timer = playTimer();
      timer.start();
    } else {
      paused = true;
      timer.stop();
    }
  }

  // Restarts the animation.
  private void restartAnimation() {
    timer.stop();
    tick = 0;
    timer = playTimer();
    timer.start();
  }

  private void changeTPS() {
    if (tick == lastTick) {
      return;
    }
    timer.stop();
    timer = playTimer();
    timer.start();
  }

  @Override
  public void throwError(String error) {
    errorMsg.setBackground(Color.red);
    errorMsg.setText(error);
  }

  public void addListener(SceneController listener) {
    this.listener = listener;
  }
}
