package cs3500.animator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import cs3500.animator.util.AnimationBuilder;

/**
 * This is a class representing a model of an scene animator. It implements Scene class. Every model
 * has a hashmap of shapes in the scene, list of commands to be executed during animation, a map of
 * all the states per tick of animation, and a tracker if the scene has been executed
 */
public class SceneModel implements Scene {

  protected LinkedHashMap<String, Shapes> shapes = new LinkedHashMap<>();
  protected List<Command> commands = new ArrayList<>();
  protected HashMap<Integer, State> states = new HashMap<>();
  protected LinkedHashMap<String, List<KeyFrame>> keyFrames = new LinkedHashMap<>();
  private List<Integer> bounds = new ArrayList<>(4);
  private boolean executed = false;

  @Override
  public void setCanvas(int x, int y, int width, int height) {
    bounds.add(x);
    bounds.add(y);
    bounds.add(width);
    bounds.add(height);
  }

  @Override
  public List<Integer> getCanvas() {
    return bounds;
  }

  @Override
  public LinkedHashMap<String, List<KeyFrame>> getKeyFrames() {
    LinkedHashMap<String, List<KeyFrame>> copyList = new LinkedHashMap();
    for (String id : keyFrames.keySet()) {
      copyList.put(id, new ArrayList<>());
      for (KeyFrame k : keyFrames.get(id)) {
        copyList.get(id).add(k.copy());
      }
    }
    return copyList;
  }

  @Override
  public void createOval(String name, Point pos, Dimension dimension, Color color) {
    createShape(new Oval(name, pos, dimension, color));
  }

  @Override
  public void createRectangle(String name, Point pos, Dimension dimension, Color color) {
    createShape(new Rectangle(name, pos, dimension, color));
  }

  private void createShape(Shapes shape) {
    if (shapes.containsKey(shape.getName())) {
      throw new IllegalArgumentException("Shape already exists!");
    }
    shapes.put(shape.getName(), shape);
    keyFrames.put(shape.getName(), new ArrayList<>());
  }

  @Override
  public void removeShape(String id) {
    if (!shapes.containsKey(id)) {
      throw new IllegalArgumentException("Shape cannot be found.");
    }
    shapes.remove(id);
    removeAllCommandsPerId(id);
    keyFrames.remove(id);
  }

  // Removes all instances of commands for a certain shape id
  private void removeAllCommandsPerId(String id) {
    for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();) {
      Command c = iterator.next();
      if (c.getId().equals(id)) {
        iterator.remove();
      }
    }
  }

  @Override
  public void command(String id, int start, Point startPos, Dimension startDimension,
                      Color startColor, int end, Point endPos, Dimension endDimension,
                      Color endColor) {
    if (start > end) {
      throw new IllegalArgumentException("Start tick cannot be greater than end tick");
    }

    if (start < 0 || end < 0) {
      throw new IllegalArgumentException("Ticks cannot be negative");
    }

    if (!shapes.containsKey(id)) {
      throw new IllegalArgumentException("Shape ID is invalid");
    }

    if (keyFrames.get(id).size() == 0) {
      addKeyFrame(id, start, startPos, startDimension, startColor);
    }

    addKeyFrame(id, end, endPos, endDimension, endColor);
  }

  @Override
  public void removeCommand(String id, int start, int end) {
    removeKeyFrame(id, end);
  }

  @Override
  public void executeAnimation() {
    executed = true;
    int runTime = getLastKeyFrameTime();
    for (int i = 0; i <= runTime; i++) {
      for (List<KeyFrame> listFrames : keyFrames.values()) {
        for (KeyFrame k : listFrames) {
          KeyFrame nextFrame = getNextKeyFrame(k);
          if (i > k.getTick() && nextFrame == null) {
            shapes.get(k.getId()).move(k.getPos());
            shapes.get(k.getId()).reSize(k.getDimension());
            shapes.get(k.getId()).reColor(k.getColor());
          } else if (nextFrame == null) {
            break;
          } else if (i > k.getTick() && i <= nextFrame.getTick()) {
            applyFrameDeltas(i - k.getTick(), k, nextFrame);
            break;
          }
        }
      }
      captureState(i);
    }
  }

  private void applyFrameDeltas(int sinceStartTick, KeyFrame currentFrame, KeyFrame nextFrame) {
    Shapes shape = shapes.get(currentFrame.getId());

    shape.move(new Point(currentFrame.getPos().x +
            (int)Math.round(currentFrame.getPosXDelta(nextFrame, sinceStartTick) * sinceStartTick),
            currentFrame.getPos().y +
            (int)Math.round(currentFrame.getPosYDelta(nextFrame, sinceStartTick)
                    * sinceStartTick)));
    shape.reSize(new Dimension(currentFrame.getDimension().width +
            (int)Math.round(currentFrame.getWidthDelta(nextFrame, sinceStartTick) * sinceStartTick),
            currentFrame.getDimension().height +
                    (int)Math.round(currentFrame.getHeightDelta(nextFrame, sinceStartTick)
                            * sinceStartTick)));
    shape.reColor(new Color(currentFrame.getColor().getRed() +
            (int)Math.round(currentFrame.getColorRDelta(nextFrame, sinceStartTick)
                    * sinceStartTick), currentFrame.getColor().getGreen() +
                    (int)Math.round(currentFrame.getColorGDelta(nextFrame, sinceStartTick)
                            * sinceStartTick), currentFrame.getColor().getBlue() +
                    (int)Math.round(currentFrame.getColorBDelta(nextFrame, sinceStartTick)
                            * sinceStartTick)));
  }

  private int getLastKeyFrameTime() {
    int lastTime = 0;
    int compareTime;
    for (List<KeyFrame> listFrames : keyFrames.values()) {
      if (!listFrames.isEmpty()) {
        compareTime = listFrames.get(listFrames.size() - 1).getTick();
        if (compareTime > lastTime) {
          lastTime = compareTime;
        }
      }
    }
    return lastTime;
  }

  /**
   * Stores the state with all the shapes at a given tick.
   *
   * @param tick the initial value
   * @throws IllegalStateException if animation hasn't started yet
   */
  private void captureState(int tick) {
    if (!executed) {
      throw new IllegalStateException("Animation has not been played, yet");
    }
    LinkedHashMap<String, Shapes> copyShapes = new LinkedHashMap<>();
    for (String s : shapes.keySet()) {
      copyShapes.put(s, shapes.get(s).copy());
    }
    states.put(tick, new State(tick, copyShapes));
  }

  private KeyFrame getKeyFrame(String id, int tick) {
    for (KeyFrame k : keyFrames.get(id)) {
      if (k.getTick() == tick) {
        return k;
      }
    }
    throw new IllegalArgumentException("Key Frame doesn't exist");
  }

  @Override
  public void addKeyFrame(String id, int tick, Point p, Dimension d, Color c) {
    try {
      KeyFrame k = getKeyFrame(id, tick);
      removeKeyFrame(k.getId(), k.getTick());
      keyFrames.get(id).add(new KeyFrame(id, tick, p, d, c));
      orderKeyFrame();
    } catch (IllegalArgumentException iae) {
      keyFrames.get(id).add(new KeyFrame(id, tick, p, d, c));
      orderKeyFrame();
    }
  }

  private KeyFrame getNextKeyFrame(KeyFrame k) {
    int indexOfFrame = keyFrames.get(k.getId()).indexOf(k);
    if (indexOfFrame == keyFrames.get(k.getId()).size() - 1) {
      return null;
    } else {
      return keyFrames.get(k.getId()).get(indexOfFrame + 1);
    }
  }

  @Override
  public void removeKeyFrame(String id, int tick) {
    for (KeyFrame k : keyFrames.get(id)) {
      if (k.getTick() == tick) {
        keyFrames.get(id).remove(k);
        return;
      }
    }
    throw new IllegalArgumentException("Key Frame does not exist");
  }

  private List<Command> convertKeyFramesToCommands() {
    List<Command> newCmds = new ArrayList<>();
    KeyFrame nextFrame;
    for (List<KeyFrame> keyList : keyFrames.values()) {
      if (keyList.size() > 1) {
        for (KeyFrame k : keyList) {
          nextFrame = getNextKeyFrame(k);
          if (nextFrame != null) {
            newCmds.add(new Command(k.getId(), k.getTick(), k.getPos(), k.getDimension(),
                    k.getColor(), nextFrame.getTick(), nextFrame.getPos(), nextFrame.getDimension(),
                    nextFrame.getColor()));
          }
        }
      } else if (keyList.size() == 1) {
        newCmds.add(new Command(keyList.get(0).getId(), keyList.get(0).getTick(),
                keyList.get(0).getPos(), keyList.get(0).getDimension(), keyList.get(0).getColor(),
                keyList.get(0).getTick(),
                keyList.get(0).getPos(), keyList.get(0).getDimension(), keyList.get(0).getColor()));
      }
    }
    return newCmds;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    if (bounds.size() != 0) {
      str.append("canvas ").append(bounds.get(0)).append("" +
              " ").append(bounds.get(1)).append(" ").append(bounds.get(2)).append("" +
              " ").append(bounds.get(3)).append("\n");
    }
    for (Shapes s : shapes.values()) {
      str.append("shape ").append(s.getName()).append(" ").append(s.typeStr()).append("\n");
      str.append(returnIntervalOfCommands(s));
    }
    return str.toString();
  }

  private int getFirstFrameTick() {
    int firstTime = getLastKeyFrameTime();
    int compareTime;
    for (List<KeyFrame> listFrames : keyFrames.values()) {
      if (!listFrames.isEmpty()) {
        compareTime = listFrames.get(0).getTick();
        if (compareTime < firstTime) {
          firstTime = compareTime;
        }
      }
    }
    return firstTime;
  }

  private String returnIntervalOfCommands(Shapes s) {
    List<Command> shapeCommands = getRelevantCommands(s);

    StringBuilder str = new StringBuilder();
    int previousTick = getFirstFrameTick();
    int lastCommandTick = getLastKeyFrameTime();

    for (Command c : shapeCommands) {
      if (c.getStartTick() - previousTick != 0) {
        str.append("motion ").append(s.getName()).append(" ");
        str.append(formatParam(c.getStartTick(), c.getStartPos(), c.getStartDimension(),
                c.getStartColor()));
        str.append(" ");
        str.append(formatParam(c.getStartTick(), c.getStartPos(), c.getStartDimension(),
                c.getStartColor())).append("\n");
      }
      str.append("motion ").append(s.getName()).append(" ");
      str.append(formatParam(c.getStartTick(), c.getStartPos(), c.getStartDimension(),
              c.getStartColor()));
      str.append(" ");
      str.append(formatParam(c.getEndTick(), c.getEndPos(), c.getEndDimension(),
              c.getEndColor())).append("\n");
      previousTick = c.getEndTick();
    }
    if (shapeCommands.size() != 0) {
      Command lastCommand = shapeCommands.get(shapeCommands.size() - 1);
      if (lastCommand.getEndTick() - lastCommandTick != 0) {
        str.append("motion ").append(s.getName()).append(" ");
        str.append(formatParam(lastCommand.getEndTick(), lastCommand.getEndPos(),
                lastCommand.getEndDimension(), lastCommand.getEndColor()));
        str.append(" ");
        str.append(formatParam(lastCommandTick, lastCommand.getEndPos(),
                lastCommand.getEndDimension(), lastCommand.getEndColor())).append("\n");
      }
    }
    return str.toString();
  }

  private String formatParam(int tick, Point p, Dimension d, Color c) {
    return tick + " " + p.x + " " + p.y + " " + d.width + " " + d.height
            + " " + c.getRed() + " " + c.getGreen() + " " + c.getBlue();
  }

  private List<Command> getRelevantCommands(Shapes s) {
    List<Command> idCommands = new ArrayList<>();
    for (Command c : convertKeyFramesToCommands()) {
      if (c.getId().equals(s.getName())) {
        idCommands.add(c);
      }
    }
    return idCommands;
  }

  @Override
  public HashMap<Integer, State> getAnimationStates() {
    HashMap<Integer, State> copyStates = new HashMap<>();
    for (Integer i : states.keySet()) {
      copyStates.put(i, new State(states.get(i).getTick(), states.get(i).getShapes()));
    }
    return copyStates;
  }

  @Override
  public LinkedHashMap<String, Shapes> getShapes() {
    HashMap<String, Shapes> copyShapes = new HashMap<>();
    for (Shapes s : shapes.values()) {
      copyShapes.put(s.getName(), s.copy());
    }
    return shapes;
  }

  @Override
  public List<Command> getCommands() {
    return convertKeyFramesToCommands();
  }

  /**
   * Orders the list of key frames by the tick point.
   */
  // Idea and code generated from:
  // https://www.techiedelight.com/sort-list-of-objects-using-comparator-java/
  private void orderKeyFrame() {
    Comparator<KeyFrame> byStart = Comparator.comparing(KeyFrame::getTick);
    for (List<KeyFrame> l : keyFrames.values()) {
      l.sort(byStart);
    }
  }

  /**
   * Builder method that uses our implementation to create an instance based on a txt file.
   */
  public static final class Builder implements AnimationBuilder<Scene> {
    Scene model = new SceneModel();

    @Override
    public Scene build() {
      return model;
    }

    @Override
    public AnimationBuilder<Scene> setBounds(int x, int y, int width, int height) {
      model.setCanvas(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<Scene> declareShape(String name, String type) {
      switch (type) {
        case "ellipse":
          model.createOval(name, new Point(0, 0), new Dimension(1, 1),
                  new Color(0));
          break;
        case "rectangle":
          model.createRectangle(name, new Point(0, 0), new Dimension(1, 1),
                  new Color(0));
          break;
        default:
          throw new IllegalArgumentException("Invalid shape type to declare");
      }
      return this;
    }

    @Override
    public AnimationBuilder<Scene> addMotion(String name, int t1, int x1, int y1, int w1, int h1,
                                             int r1, int g1, int b1, int t2, int x2, int y2, int w2,
                                             int h2, int r2, int g2, int b2) {
      model.command(name, t1, new Point(x1, y1), new Dimension(w1, h1),
              new Color(r1, g1, b1), t2, new Point(x2, y2), new Dimension(w2, h2),
              new Color(r2, g2, b2));
      return this;
    }

    @Override
    public AnimationBuilder<Scene> addKeyframe(String name, int t, int x, int y, int w, int h,
                                               int r, int g, int b) {
      model.addKeyFrame(name, t, new Point(x, y), new Dimension(w, h), new Color(r, g, b));
      return this;
    }
  }
}
