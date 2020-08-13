import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs3500.animator.model.Scene;
import cs3500.animator.model.SceneModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.SceneView;
import cs3500.animator.view.SceneViewSVG;
import cs3500.animator.view.SceneViewText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the SceneOut class and all of its public methods. Used for outputting
 * animations to file readable formats.
 */
public class SceneOutTest {

  @Test
  public void testToSVG() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/smalldemo.txt");
    String output = "";

    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      SceneView view = new SceneViewSVG("out", model, 20);
      output = view.toString();
    } catch (IOException io) {
      output = "Error";
    }

    assertEquals(66, output.split("\n").length);

  }

  @Test
  public void testToSVG2() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/hanoi.txt");
    String output = "";

    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      SceneView view = new SceneViewSVG("out", model, 20);
      output = view.toString();
    } catch (IOException io) {
      output = "Error";
    }

    assertEquals(24622, output.split("\n").length);
  }

  @Test
  public void testToFile() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    AnimationBuilder<Scene> builder2 = new SceneModel.Builder();
    File file = new File("resources/smalldemo.txt");
    String output = "";
    String output2 = "";

    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      output = model.toString();
      SceneView view = new SceneViewText("resources/SceneOutTest.txt", model);
      view.display();
      Readable fileReader2 = new FileReader(new File("resources/SceneOutTest.txt"));
      AnimationReader.parseFile(fileReader2, builder2);
      Scene model2 = builder.build();
      SceneView view2 = new SceneViewText("out", model2);
      output2 = view2.toString();
    } catch (IOException io) {
      output = "Error";
    }

    assertEquals(output, output2);
  }

  @Test
  public void testToSVGToFile() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/smalldemo.txt");
    String output = "";
    File file2 = new File("");

    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      SceneView view = new SceneViewSVG("resources/SceneOutTest.svg", model, 20);
      view.display();
      file2 = new File("resources/SceneOutTest.svg");
    } catch (IOException io) {
      output = "Error";
    }

    assertTrue(file2.exists());
  }
}