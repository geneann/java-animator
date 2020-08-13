package cs3500.animator.view;

/**
 * Creates a view for the SceneModel to display changes and mutations to the program. The view uses
 * the java swing library.
 */
public interface SceneViewVisual extends SceneView {

  /**
   * Gets the tick of the animation.
   *
   * @return the current tick.
   */
  int getTick();

  /**
   * Make the view visible to start the session.
   */
  void makeVisible();
}
