package cs3500.animator.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cs3500.animator.model.Command;
import cs3500.animator.model.KeyFrame;
import cs3500.animator.model.ReadOnlyScene;
import cs3500.animator.model.Shapes;

/**
 * Creates a SVG representation of the given Scene model. The class extends SceneViewText to take
 * advantage of the output methods it offers.
 */
public class SceneViewSVG extends SceneViewText {

  /**
   * Creates an instance of a SVG representation with the output location.
   *
   * @param output the output location of the textual view.
   * @param model  the model to read information from.
   */
  public SceneViewSVG(String output, ReadOnlyScene model, int tickPerSec) {
    super(output, model);
    this.outputString = toSVG(model, tickPerSec);
  }

  /**
   * Converts a model into a SVG compliant String format to be read by an application.
   *
   * @param model      A scene model to be read from.
   * @param tickPerSec An int to represent ticks per second.
   * @return a String containing the formatted SVG text.
   */
  public String toSVG(ReadOnlyScene model, int tickPerSec) {
    StringBuilder str = new StringBuilder();
    int canvasWidth = model.getCanvas().get(2);
    int canvasHeight = model.getCanvas().get(3);
    str.append("<svg width=\"" + canvasWidth + "\" height=\"" + canvasHeight + "\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\">\n");
    for (Shapes s : model.getShapes().values()) {
      switch (s.typeStr()) {
        case "rectangle":
          str.append("<rect id=\"" + s.getName() + "\" x=\"" + s.getPos().x
                  + "\" y=\"" + s.getPos().y + "\" width=\"" + 0
                  + "\" height=\"" + s.getSize().height + "\" " +
                  "fill=\"rgb(" + s.getColor().getRed() + "," + s.getColor().getGreen() + ","
                  + s.getColor().getBlue() + ")\" visibility=\"visible\" >\n");
          for (Command c : getRelevantCommands(s, model)) {
            int duration = (c.getEndTick() - c.getStartTick()) * (1000 / tickPerSec);
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"x\" from=\"" + c.getStartPos().x + "\" " +
                    "to=\"" + c.getEndPos().x + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"y\" from=\"" + c.getStartPos().y + "\" " +
                    "to=\"" + c.getEndPos().y + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"width\" from=\"" + c.getStartDimension().width + "\" " +
                    "to=\"" + c.getEndDimension().width + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"height\" from=\"" + c.getStartDimension().height +
                    "\" to=\"" + c.getEndDimension().height + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"fill\" from=\"rgb(" + c.getStartColor().getRed() +
                    "," + c.getStartColor().getGreen() + "," + c.getStartColor().getBlue() + ")" +
                    "\" to=\"rgb(" + c.getEndColor().getRed() +
                    "," + c.getEndColor().getGreen() + "," + c.getEndColor().getBlue() + ")"
                    + "\" fill=\"freeze\" />\n");
            str.append("\n");
          }
          str.append("</rect>\n");
          break;
        case "ellipse":
          str.append("<ellipse id=\"" + s.getName() + "\" cx=\""
                  + Math.addExact(s.getPos().x, s.getSize().width / 2)
                  + "\" cy=\"" + Math.addExact(s.getPos().y, s.getSize().height / 2)
                  + "\" rx=\"" + s.getSize().width / 2
                  + "\" ry=\"" + s.getSize().height / 2 + "\" " +
                  "fill=\"rgb(" + s.getColor().getRed() + "," + s.getColor().getGreen() + ","
                  + s.getColor().getBlue() + ")\" visibility=\"visible\" >\n");
          for (Command c : getRelevantCommands(s, model)) {
            int duration = (c.getEndTick() - c.getStartTick()) * (1000 / tickPerSec);
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"cx\" from=\"" +
                    Math.addExact(c.getStartPos().x, c.getStartDimension().width / 2) + "\" " +
                    "to=\"" + Math.addExact(c.getEndPos().x, c.getEndDimension().width / 2)
                    + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"cy\" from=\""
                    + Math.addExact(c.getStartPos().y, c.getStartDimension().height / 2) + "\" " +
                    "to=\"" + Math.addExact(c.getEndPos().y, c.getEndDimension().height / 2)
                    + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"rx\" from=\"" + c.getStartDimension().width / 2
                    + "\" to=\"" + c.getEndDimension().width / 2 + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"ry\" from=\"" + c.getStartDimension().height / 2 +
                    "\" to=\"" + c.getEndDimension().height / 2 + "\" fill=\"freeze\" />\n");
            str.append("<animate attributeType=\"xml\" begin=\"" + c.getStartTick()
                    * (1000 / tickPerSec) + "ms\" dur=\"" + duration
                    + "ms\" attributeName=\"fill\" from=\"rgb(" + c.getStartColor().getRed() +
                    "," + c.getStartColor().getGreen() + "," + c.getStartColor().getBlue() + ")" +
                    "\" to=\"rgb(" + c.getEndColor().getRed() +
                    "," + c.getEndColor().getGreen() + "," + c.getEndColor().getBlue() + ")"
                    + "\" fill=\"freeze\" />\n");
            str.append("\n");
          }
          str.append("</ellipse>\n");
          break;
        default:
          throw new IllegalArgumentException("Shape type not supported");
      }
    }
    str.append("</svg>\n");
    return str.toString();
  }

  /**
   * Gets relevant commands to a given Shape in a model.
   *
   * @param s     the Shape to compare commands with.
   * @param model the model to take the commands from.
   * @return A list of commands that are only applicable to the given shape.
   */
  private List<Command> getRelevantCommands(Shapes s, ReadOnlyScene model) {
    List<Command> idCommands = new ArrayList<>();
    for (Command c : convertKeyFramesToCommands(model)) {
      if (c.getId().equals(s.getName())) {
        idCommands.add(c);
      }
    }
    return idCommands;
  }

  private List<Command> convertKeyFramesToCommands(ReadOnlyScene model) {
    List<Command> newCmds = new ArrayList<>();
    LinkedHashMap<String, List<KeyFrame>> keyFrames = model.getKeyFrames();
    KeyFrame nextFrame;
    for (List<KeyFrame> keyList : keyFrames.values()) {
      if (keyList.size() > 1) {
        for (KeyFrame k : keyList) {
          nextFrame = getNextKeyFrame(k, keyFrames);
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

  private KeyFrame getNextKeyFrame(KeyFrame k, LinkedHashMap<String, List<KeyFrame>> keyFrames) {
    int indexOfFrame = keyFrames.get(k.getId()).indexOf(k);
    if (indexOfFrame == keyFrames.get(k.getId()).size() - 1) {
      return null;
    } else {
      return keyFrames.get(k.getId()).get(indexOfFrame + 1);
    }
  }

}
