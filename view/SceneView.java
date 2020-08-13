package cs3500.animator.view;

/**
 * Displays an Animator with several different options to be viewed as. This interface encompasses
 * a plain visual view, a visual editor view, a SVG view, and a textual view.
 */

public interface SceneView {

  /**
   * Displays the current view in the manor that is most appropriate to the implementation.
   */
  void display();

}
