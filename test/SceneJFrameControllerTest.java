import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cs3500.animator.controller.SceneController;
import cs3500.animator.controller.SceneJFrameController;
import cs3500.animator.controller.ShapeType;
import cs3500.animator.model.Scene;
import cs3500.animator.model.SceneModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.SceneControllerViewVisual;
import cs3500.animator.view.SceneEditorVisual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests all public methods of the JFrame controller.
 */
public class SceneJFrameControllerTest {

  @Test
  public void testAddShape() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/buildings.txt");
    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      model.executeAnimation();

      SceneEditorVisual view = new SceneControllerViewVisual("Animator", 20, model);
      SceneController controller = new SceneJFrameController(model, view);
      controller.addShape("newShape", ShapeType.OVAL);
      assertEquals("newShape", model.getShapes().get("newShape").getName());

    } catch (IOException io) {
      throw new IllegalStateException("Did not work.");
    }
  }

  @Test
  public void testDeleteShape() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/buildings.txt");
    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      model.executeAnimation();

      SceneEditorVisual view = new SceneControllerViewVisual("Animator", 20, model);
      SceneController controller = new SceneJFrameController(model, view);
      controller.addShape("newShape", ShapeType.OVAL);
      assertEquals(110, model.getShapes().size());
      controller.deleteShape("newShape");
      assertEquals(109, model.getShapes().size());

    } catch (IOException io) {
      throw new IllegalStateException("Did not work.");
    }
  }

  @Test
  public void testAddKeyFrame() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/buildings.txt");
    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      model.executeAnimation();

      SceneEditorVisual view = new SceneControllerViewVisual("Animator", 20, model);
      SceneController controller = new SceneJFrameController(model, view);
      controller.addKeyFrame("eclipse", 150, new Point(500, 500),
              new Dimension(50, 50), new Color(0, 255, 0));
      assertEquals(150, model.getKeyFrames().get("eclipse").get(3).getTick());

    } catch (IOException io) {
      throw new IllegalStateException("Did not work.");
    }
  }

  @Test
  public void testDeleteKeyFrame() {
    AnimationBuilder<Scene> builder = new SceneModel.Builder();
    File file = new File("resources/buildings.txt");
    try {
      Readable fileReader = new FileReader(file);
      AnimationReader.parseFile(fileReader, builder);
      Scene model = builder.build();
      model.executeAnimation();

      SceneEditorVisual view = new SceneControllerViewVisual("Animator", 20, model);
      SceneController controller = new SceneJFrameController(model, view);
      controller.addKeyFrame("eclipse", 150, new Point(500, 500),
              new Dimension(50, 50), new Color(0, 255, 0));
      controller.deleteKeyFrame("eclipse", 150);
      assertNotEquals(150, model.getKeyFrames().get("eclipse").get(3).getTick());

    } catch (IOException io) {
      throw new IllegalStateException("Did not work.");
    }
  }
}