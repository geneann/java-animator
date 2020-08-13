package cs3500.animator.view;

import cs3500.animator.controller.SceneController;

/**
 * Creates a view for the Scene in which the user can edit the animation being played with the help
 * of the controller provided.
 */
public interface SceneEditorVisual extends SceneViewVisual {

  /**
   * Adds a listener to perform mutations made by the user.
   * @param listener The listener to make mutations with.
   */
  void addListener(SceneController listener);

  /**
   * Displays an error message for the user to interpret.
   * @param error The error message to explain what happened.
   */
  void throwError(String error);

}
