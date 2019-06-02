package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import controller.GameController;
import controller.GameController.GameMode;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import model.service.Animator;
import model.service.NodeAnimator;
import model.startup.Skedaddle;

public class MainUIController {

  // FXML variables
  @FXML
  private HBox parent;

  @FXML
  private JFXButton newGameBUTTON;

  @FXML
  private Pane gameOptionsPANE;

  @FXML
  private JFXTextField gridSizeTF;

  @FXML
  private JFXToggleButton picModeTGL;

  @FXML
  private Pane uploadPANE;

  @FXML
  private JFXToggleButton picUploadTGL;

  @FXML
  private Pane browsePANE;

  @FXML
  private JFXButton browseBUTTON;

  @FXML
  private BorderPane gridBORDERPANE;

  @FXML
  private Pane peekPANE;

  @FXML
  private JFXButton peekBUTTON;

  @FXML
  private Pane shuffleButtonPANE;

  @FXML
  private JFXButton shuffleBUTTON;

  @FXML
  private Pane shufflePANE;

  @FXML
  private JFXButton doItBUTTON;

  @FXML
  private JFXTextField shufflesTF;

  @FXML
  private Pane goButtonPANE;

  @FXML
  private JFXButton goBUTTON;

  @FXML
  private Pane exitPANE;

  @FXML
  private JFXButton exitBUTTON;

  @FXML
  private Pane movesPANE;

  @FXML
  private Label movesLABEL;

  @FXML
  private BorderPane peekBORDERPANE;

  private ConcurrentHashMap<Node, NodeAnimator> nodesAnimations;

  private BooleanProperty isActive;

  private Image initialImage;
  private Image uploadedPic;

  @FXML
  private void initialize() {

    isActive = new SimpleBooleanProperty();

    initializeAnimations();

    initializeListeners();

//    hide all unneeded panes
    setVisibility(false, goButtonPANE, gameOptionsPANE, peekPANE, uploadPANE, movesPANE, browsePANE,
        gridBORDERPANE, peekBORDERPANE, shuffleButtonPANE, shufflePANE, exitPANE);

//    set buttons' behavior
    newGameBUTTON.setOnMouseClicked(e -> {
      if (isActive.get()) {
        validateNewGame();
      } else {
        setVisibility(true, gameOptionsPANE, goButtonPANE);
//        showNewGameOptions();
      }
    });
    goBUTTON.setOnAction(e -> startNewGame());
    gridSizeTF.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) {
        startNewGame();
      }
    });
    shufflesTF.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) {
        GameController.shuffleBoard(
            shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText()));
      }
    });
    shuffleBUTTON.setOnMouseClicked(e -> setVisibility(!shufflePANE.isVisible(),shufflePANE));
    doItBUTTON.setOnMouseClicked(e -> GameController
        .shuffleBoard(shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText())));
    peekBUTTON.setOnMouseClicked(e -> setVisibility(!peekBORDERPANE.isVisible(),peekBORDERPANE));
    browseBUTTON.setOnMouseClicked(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Upload an image");
      fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
      File tmp = fileChooser.showOpenDialog(Skedaddle.stage);
      if (tmp != null) {
        uploadedPic = new Image(tmp.toURI().toString());
        browsePANE.setStyle("-fx-background-color: #7FFF00");
        uploadedPic = getOptimizedImage(uploadedPic);
        picUploadTGL.getStyleClass().add("toggle-button-picUploaded");
      }
    });
    exitBUTTON.setOnMouseClicked(e -> System.exit(0));

    initialImage = getOptimizedImage(new Image("images/cat.jpg", 400, 400, true, true));
  }

  /**
   * Sensitive method, for now depends on the order of enums in Animator TODO: implement a safer and
   * better solution..
   */
  private void initializeAnimations() {
    nodesAnimations = new ConcurrentHashMap<>();
    nodesAnimations.put(gameOptionsPANE,
        new NodeAnimator(gameOptionsPANE, Animator.SHOW_OPTIONS, Animator.HIDE_OPTIONS));
    nodesAnimations
        .put(uploadPANE, new NodeAnimator(uploadPANE, Animator.SHOW_UPLOAD, Animator.HIDE_UPLOAD));
    nodesAnimations
        .put(browsePANE, new NodeAnimator(browsePANE, Animator.SHOW_BROWSE, Animator.HIDE_BROWSE));
    nodesAnimations
        .put(goButtonPANE, new NodeAnimator(goButtonPANE, Animator.SHOW_GO, Animator.HIDE_GO));
    nodesAnimations
        .put(movesPANE, new NodeAnimator(movesPANE, Animator.SHOW_MOVES, Animator.HIDE_MOVES));
    nodesAnimations
        .put(peekPANE, new NodeAnimator(peekPANE, Animator.SHOW_PEEK, Animator.HIDE_PEEK));
    nodesAnimations.put(peekBORDERPANE,
        new NodeAnimator(peekBORDERPANE, Animator.SHOW_PEEKBORDER, Animator.HIDE_PEEKBORDER));
    nodesAnimations.put(shuffleButtonPANE,
        new NodeAnimator(shuffleButtonPANE, Animator.SHOW_SHUFFLEBUTTONPANE, Animator.HIDE_SHUFFLEBUTTONPANE));
    nodesAnimations.put(shufflePANE,
        new NodeAnimator(shufflePANE, Animator.SHOW_SHUFFLEPANE, Animator.HIDE_SHUFFLEPANE));


  }


  private void initializeListeners() {

//    initialize other bindings
    isActive.bindBidirectional(GameController.isActiveProperty());
    gridSizeTF.textProperty()
        .bindBidirectional(GameController.gridSizeProperty(), new NumberStringConverter());
    movesLABEL.textProperty().bind(GameController.currentMovesProperty().asString());
    picModeTGL.selectedProperty().addListener((observable, was, isSelected) -> {
      if (isSelected.booleanValue()) {
        GameController.setMode(GameMode.PICTURE);
//        setVisibility(isSelected.booleanValue(), uploadPANE);
      } else {
        GameController.setMode(GameMode.NORMAL);
        picUploadTGL.setSelected(false);
//        setVisibility(isSelected.booleanValue(), uploadPANE,browsePANE);
      }
      setVisibility(isSelected.booleanValue(), uploadPANE);
    });
    picUploadTGL.selectedProperty().addListener((observable, was, isSelected) -> {
      setVisibility(isSelected.booleanValue(), browsePANE);
    });
    gridSizeTF.textProperty().addListener((observable, oldText, newText) -> {
//      TODO: Take this out of here and bind to a boolean expression
      validateInput(observable, oldText, newText);
      if (!((StringProperty) observable).getValue().isBlank()
          && (Integer.parseInt(((StringProperty) observable).getValue()) > 18
          || Integer.parseInt(((StringProperty) observable).getValue()) == 0)) {
        ((StringProperty) observable).setValue(oldText);
      }
//      if (!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText)) {
//        showGoBUTTON();
//        if (exitPosition == 1)
//          moveExitBUTTON(12);
//          TODO: fix exit button animation
//      } else {
//        if (exitPosition == 2)
//          moveExitBUTTON(21);
//          TODO: fix exit button animation
//        hideGoBUTTON();
//      }
//    });
      setVisibility(!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText),
          goButtonPANE);
    });
    shufflesTF.textProperty().addListener(this::validateInput);
  }


  private void showNewGameOptions() {
    if (gameOptionsPANE.isVisible()) {
      animationExecuter(Animator.NUDGE, gameOptionsPANE, goButtonPANE);
    } else {
      startNewGame();
    }
  }

  private void validateNewGame() {
    animationActivator(true, gridBORDERPANE, movesPANE);
    isActive.setValue(false);
    closeCurrentGame();
  }

  private void closeCurrentGame() {
    animationActivator(false, peekBORDERPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE,shufflePANE);
  }

  private void startNewGame() {
    isActive.set(true);
  }


  /*Inner Services*/
  private void animationExecuter(Animator animation, Node... nodes) {
    for (Node node : nodes) {
      animation.getAnimation().setNode(node);
      Platform.runLater(() ->
          animation.getAnimation().play()
      );
    }
  }

  private void animationActivator(boolean show, Node... nodes) {
    for (Node node : nodes) {
      Platform.runLater(() -> {
        if (show) {
          nodesAnimations.get(node).show();
        } else {
          nodesAnimations.get(node).hide();
        }
      });
    }
  }

  private void setVisibility(boolean visible, Node... nodes) {
    for (Node node : nodes) {
      if (node.isVisible() != visible) {
        if (visible) {
          node.setVisible(true);
        }
        animationActivator(visible, node);
      }
    }
  }

  private WritableImage getOptimizedImage(Image image) {
    System.out.println("W: " + image.getWidth() + " h: " + image.getHeight());

    double w = image.getWidth(), h = image.getHeight();

    image = w < h ? new Image(image.getUrl(), 400, 0, true, true)
        : new Image(image.getUrl(), 0, 400, true, true);
    w = (image.getWidth() - 400) / 2;
    h = (image.getHeight() - 400) / 2;

    System.out.println("W: " + image.getWidth() + " h: " + image.getHeight());

    PixelReader reader = image.getPixelReader();
    return new WritableImage(reader, (int) w, (int) h, 400, 400);
  }

  private void validateInput(Observable observable, String oldText, String newText) {
    if (!newText.isEmpty()) {
      for (int i = newText.length() - 1; i >= 0; i--) {
        if (!((newText.charAt(i)) <= '9' && (newText.charAt(i)) >= '0')) {
          ((StringProperty) observable).setValue(oldText);
          return;
        }
      }
    }
  }

}