package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.events.JFXDialogEvent;
import controller.GameState;
import controller.GameState.GameMode;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;
import model.service.Animator;
import model.service.ExitButtonAnimator;
import model.service.NodeAnimator;
import model.startup.Skedaddle;

@SuppressWarnings("Duplicates")

public class MainUIController3 {

  static MainUIController3 instance = new MainUIController3();
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

  public static MainUIController3 getInstance() {
    return instance;
  }

  @FXML
  private void initialize() {

    // Initialize needed variables
    isActive = new SimpleBooleanProperty(false);
    initialImage = getOptimizedImage(new Image("images/cat.jpg", 400, 400, true, true));

    // Hide all unneeded panes
    setVisibility(false, goButtonPANE, gameOptionsPANE, peekPANE, uploadPANE, movesPANE, browsePANE,
        gridBORDERPANE, peekBORDERPANE, shuffleButtonPANE, shufflePANE);

    // Initialize needed bindings, listeners & other...
    initializeAnimations();

    initializeBindings();

    initializeEventHandlers();

    initializeListeners();

  }

  private void initializeBindings() {

    isActive.bindBidirectional(GameState.isActiveProperty());

    gridSizeTF.textProperty()
        .bindBidirectional(GameState.gridSizeProperty(), new NumberStringConverter());

    movesLABEL.textProperty().bind(GameState.currentMovesProperty().asString());
  }

  private void initializeEventHandlers() {

    newGameBUTTON.setOnMouseClicked(e -> {
      if (isActive.get()) {
        validateNewGame();
      } else {
        showNewGameOptions();
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
        GameState.shuffleBoard(
            shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText()));
      }
    });
    shuffleBUTTON.setOnMouseClicked(e -> setVisibility(!shufflePANE.isVisible(), shufflePANE));
    doItBUTTON.setOnMouseClicked(e -> GameState
        .shuffleBoard(shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText())));
    peekBUTTON.setOnMouseClicked(e -> setVisibility(!peekBORDERPANE.isVisible(), peekBORDERPANE));
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
  }

  private void initializeListeners() {

    isActive.addListener(((observable, oldValue, newValue) -> {
      if (observable.getValue()) {
        setVisibility(false, gameOptionsPANE, goButtonPANE);
        setVisibility(true, exitPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE);
      } else {
        setVisibility(false, exitPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE);
        setVisibility(true, gameOptionsPANE, goButtonPANE);
      }
    }));

    picModeTGL.selectedProperty().addListener((observable, was, isSelected) -> {
      if (isSelected.booleanValue()) {
        GameState.setMode(GameMode.PICTURE);
        // setVisibility(isSelected.booleanValue(), uploadPANE);
      } else {
        GameState.setMode(GameMode.NORMAL);
        picUploadTGL.setSelected(false);
        // setVisibility(isSelected.booleanValue(), uploadPANE,browsePANE);
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
      if (!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText)) {
        if (oldText.isEmpty()) {
          setVisibility(true, goButtonPANE, exitPANE);
        } else {
          setVisibility(true, goButtonPANE);
        }
      } else {
        setVisibility(false, goButtonPANE, exitPANE);
      }
      setVisibility(!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText),
          goButtonPANE);
    });

    shufflesTF.textProperty().addListener(this::validateInput);
  }

  private void showNewGameOptions() {
    if (gameOptionsPANE.isVisible()) {
      animationExecutor(Animator.NUDGE, gameOptionsPANE, goButtonPANE);
    } else {
      startNewGame();
    }
  }

  private void validateNewGame() {
    isActive.setValue(false);
    closeCurrentGame();
  }

  private void closeCurrentGame() {
    animationActivator(false, peekBORDERPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE,
        shufflePANE);
  }

  private void startNewGame() {
    isActive.set(true);
  }


  /*Inner Services*/
  private void animationExecutor(Animator animation, Node... nodes) {
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
      }
      animationActivator(visible, node);
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


  /**
   * Sensitive method, for now depends on the order of enums in Animator.
   *
   * TODO: implement a safer and better solution..
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
    nodesAnimations.put(gridBORDERPANE,
        new NodeAnimator(gridBORDERPANE, Animator.SHOW_BOARD, Animator.HIDE_BOARD));
    nodesAnimations.put(shuffleButtonPANE,
        new NodeAnimator(shuffleButtonPANE, Animator.SHOW_SHUFFLEBUTTONPANE,
            Animator.HIDE_SHUFFLEBUTTONPANE));
    nodesAnimations.put(shufflePANE,
        new NodeAnimator(shufflePANE, Animator.SHOW_SHUFFLEPANE, Animator.HIDE_SHUFFLEPANE));
    nodesAnimations.put(exitPANE, new ExitButtonAnimator(exitPANE, Animator.EXIT_INITIAL_TO_STANDBY,
        Animator.EXIT_STANDBY_TO_INITIAL, Animator.EXIT_STANDBY_TO_INGAME,
        Animator.EXIT_INGAME_TO_STANDBY));
  }

  public void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> controls,
      String header, String body) {
    BoxBlur blur = new BoxBlur(3, 3, 3);
    if (controls.isEmpty()) {
      controls.add(new JFXButton("Okay"));
    }
    JFXDialogLayout dialogLayout = new JFXDialogLayout();
    JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

    controls.forEach(controlButton -> {
      controlButton.getStyleClass().add("dialog-button");
      controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
        dialog.close();
      });
    });

    dialogLayout.setHeading(new Label(header));
    dialogLayout.setBody(new Label(body));
    dialogLayout.setActions(controls);
    dialog.show();
    dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
      nodeToBeBlurred.setEffect(null);
    });
    nodeToBeBlurred.setEffect(blur);
  }


}