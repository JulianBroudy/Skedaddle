package model.service;

import javafx.animation.PauseTransition;
import javafx.scene.Node;

public class NodeAnimator {

  private Node node;
  private Animator showNode, hideNode;

  public NodeAnimator(Node node, Animator showNode,
      Animator hideNode) {
    this.node = node;
    this.showNode = showNode;
    this.hideNode = hideNode;
    showNode.getAnimation().setNode(node);
    hideNode.getAnimation().setNode(node);
    hideNode.getAnimation().setOnFinished(e -> node.setVisible(false));
  }

  public void show() {
    node.setVisible(true);
    showNode.getAnimation().play();
  }

  public void hide() {
    hideNode.getAnimation().play();
  }


}
