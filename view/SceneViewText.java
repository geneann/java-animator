package cs3500.animator.view;

import java.io.IOException;
import java.io.PrintWriter;

import cs3500.animator.model.ReadOnlyScene;

/**
 * Outputs the given Scene to a textual representation of what is happening in the model.
 */
public class SceneViewText implements SceneView {

  protected String outputName;
  protected String outputString;

  /**
   * Creates an instance of a textual representation with the output location.
   * @param outputName the output location of the textual view.
   * @param model the model to read information from.
   */
  public SceneViewText(String outputName, ReadOnlyScene model) {
    this.outputName = outputName;
    this.outputString = model.toString();
  }

  @Override
  public void display() {
    if (this.outputName.equals("") || this.outputName.equals("out")) {
      System.out.println(outputString);
    } else {
      toFile(outputString, outputName);
    }
  }

  private void toFile(String str, String fileName) {
    try (PrintWriter out = new PrintWriter(fileName)) {
      out.println(str);
    } catch (IOException io) {
      throw new IllegalStateException("Could not print to file" + io.getMessage());
    }
  }

  /**
   * Returns a string representing what the model text would output given a textual view. This
   * will primarily be used for testing.
   * @return the output that would be displayed.
   */
  @Override
  public String toString() {
    return outputString;
  }
}
