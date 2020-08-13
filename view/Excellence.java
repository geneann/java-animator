package cs3500.animator.view;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JDialog;

import cs3500.animator.controller.SceneController;
import cs3500.animator.controller.SceneJFrameController;
import cs3500.animator.model.Scene;
import cs3500.animator.model.SceneModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;

/**
 * Main method class to run and test the Animator. Animations are compiled from SceneModel and
 * passed to SceneView.
 */
public final class Excellence {
  /**
   * The main method to run the entire program.
   *
   * @param args Arguments to boot the .jar with through the console.
   */
  public static void main(String[] args) {
    int speed = 1;
    String output = "";
    String in = "";
    String viewArg = "";
    List<String> commands = new ArrayList<>();
    Collections.addAll(commands, args);

    if (commands.size() % 2 != 0) {
      throwErrorMessage("Invalid arguments");
      return;
    }

    for (int i = 0; i < commands.size() / 2; i++) {
      switch (commands.get(i * 2)) {
        case "-in":
          in = commands.get(i * 2 + 1);
          break;
        case "-view":
          viewArg = commands.get(i * 2 + 1);
          break;
        case "-out":
          output = commands.get(i * 2 + 1);

          break;
        case "-speed":
          speed = Integer.parseInt(commands.get(i * 2 + 1));
          break;
        default:
          throwErrorMessage("Invalid arguments");
          return;
      }
    }

    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    if (in.equals("")) {
      throwErrorMessage("Invalid arguments");
      return;
    }
    File file = new File(in);
    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      model.executeAnimation();
      switch (viewArg.toLowerCase()) {
        case "visual":
          SceneViewVisual visualView = new SceneViewVisualImpl("Animator", speed, model);
          visualView.makeVisible();
          visualView.display();
          break;
        case "text":
          SceneView textView = new SceneViewText(output, model);
          textView.display();
          break;
        case "svg":
          SceneView svgView = new SceneViewSVG(output, model, speed);
          svgView.display();
          break;
        case "edit":
          SceneEditorVisual editView = new SceneControllerViewVisual("Editor", speed, model);
          SceneController controller = new SceneJFrameController(model, editView);
          controller.start();
          break;
        default:
          throwErrorMessage("The \"-view\" argument is invalid.");
      }
    } catch (IOException io) {
      throwErrorMessage("IOException Occurred.");
    }


  }

  private static void throwErrorMessage(String errorMsg) {
    JOptionPane optionPane = new JOptionPane(errorMsg, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = optionPane.createDialog("Failure to Start Program");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
    System.exit(0);
  }
}