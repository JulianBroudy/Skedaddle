package model.service;

import javafx.animation.Animation;
import javafx.scene.Node;

public class NodeAnimator {

  private final Node node;
  private Animator showNode, hideNode;

  NodeAnimator(Node node) {
    this.node = node;
  }

  public NodeAnimator(Node node, Animator showNode, Animator hideNode) {
    this(node);
    this.showNode = showNode;
    this.hideNode = hideNode;
    initializeAnimations(showNode, hideNode);
    hideNode.getAnimation().setOnFinished(e -> node.setVisible(false));
  }

  void initializeAnimations(Animator... animations) {
    for (Animator animation : animations) {
      animation.getAnimation().setNode(node);
      animation.getAnimation().setOnFinished(e -> node.setVisible(true));
    }
  }

  public synchronized void show() {
    if (hideNode.getAnimation().getStatus() == Animation.Status.RUNNING) {
      hideNode.getAnimation().stop();
    }
    node.setVisible(true);
    showNode.getAnimation().play();
  }

  public synchronized void hide() {
    if (showNode.getAnimation().getStatus() == Animation.Status.RUNNING) {
      showNode.getAnimation().stop();
    }
    hideNode.getAnimation().play();
  }


}
