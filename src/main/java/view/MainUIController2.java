//package view;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXTextField;
//import com.jfoenix.controls.JFXToggleButton;
//import controller.GameState;
//import controller.GameState.GameMode;
//import java.io.File;
//import java.util.Objects;
//import javafx.application.Platform;
//import javafx.beans.Observable;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.beans.property.StringProperty;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.PixelReader;
//import javafx.scene.image.WritableImage;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.stage.FileChooser;
//import javafx.stage.FileChooser.ExtensionFilter;
//import javafx.util.converter.NumberStringConverter;
//import model.service.Animator;
//import model.startup.Skedaddle;
//
//public class MainUIController2 {
//
//  // FXML variables
//  @FXML
//  private HBox parent;
//
//  @FXML
//  private JFXButton newGameBUTTON;
//
//  @FXML
//  private Pane newGameMainPANE;
//
//  @FXML
//  private JFXTextField gridSizeTF;
//
//  @FXML
//  private JFXToggleButton picModeTGL;
//
//  @FXML
//  private Pane uploadPANE;
//
//  @FXML
//  private JFXToggleButton picUploadTGL;
//
//  @FXML
//  private Pane browsePANE;
//
//  @FXML
//  private JFXButton browseBUTTON;
//
//  @FXML
//  private BorderPane gridBORDERPANE;
//
//  @FXML
//  private Pane peekPANE;
//
//  @FXML
//  private JFXButton peekBUTTON;
//
//  @FXML
//  private Pane shuffleButtonPANE;
//
//  @FXML
//  private JFXButton shuffleBUTTON;
//
//  @FXML
//  private Pane shufflePANE;
//
//  @FXML
//  private JFXButton doItBUTTON;
//
//  @FXML
//  private JFXTextField shufflesTF;
//
//  @FXML
//  private Pane goButtonPANE;
//
//  @FXML
//  private JFXButton goBUTTON;
//
//  @FXML
//  private Pane exitPANE;
//
//  @FXML
//  private JFXButton exitBUTTON;
//
//  @FXML
//  private Pane movesPANE;
//
//  @FXML
//  private Label movesLABEL;
//
//  @FXML
//  private BorderPane peekBORDERPANE;
//
//
//  private BooleanProperty isActive;
//
//  private Image initialImage;
//  private Image uploadedPic;
//
//  @FXML
//  private void initialize() {
//
//    isActive = new SimpleBooleanProperty();
//
//    initializeListeners();
//
////    hide all unneeded panes
//    setVisibility(false, goButtonPANE, newGameMainPANE, peekPANE, uploadPANE, movesPANE, browsePANE,
//        gridBORDERPANE, peekBORDERPANE, shuffleButtonPANE, shufflePANE,exitPANE);
//
////    set buttons' behavior
//    newGameBUTTON.setOnMouseClicked(e -> {
//      if (isActive.get()) {
//        validateNewGame();
//      } else {
//        setVisibility(true, newGameMainPANE, goButtonPANE);
////        showNewGameOptions();
//      }
//    });
//    goBUTTON.setOnAction(e -> startNewGame());
//    gridSizeTF.setOnKeyPressed(e -> {
//      if (e.getCode().equals(KeyCode.ENTER)) {
//        startNewGame();
//      }
//    });
//    shufflesTF.setOnKeyPressed(e -> {
//      if (e.getCode().equals(KeyCode.ENTER)) {
//        GameState.shuffleBoard(
//            shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText()));
//      }
//    });
//    shuffleBUTTON.setOnMouseClicked(e -> shuffleButtonPANE.setVisible(!shuffleBUTTON.isVisible()));
//    doItBUTTON.setOnMouseClicked(e -> GameState
//        .shuffleBoard(shufflesTF.getText().isEmpty() ? 1 : Integer.parseInt(shufflesTF.getText())));
//    peekBUTTON.setOnMouseClicked(e -> peekBORDERPANE.setVisible(!peekBORDERPANE.isVisible()));
//    browseBUTTON.setOnMouseClicked(e -> {
//      FileChooser fileChooser = new FileChooser();
//      fileChooser.setTitle("Upload an image");
//      fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
//      File tmp = fileChooser.showOpenDialog(Skedaddle.stage);
//      if (tmp != null) {
//        uploadedPic = new Image(tmp.toURI().toString());
//        browsePANE.setStyle("-fx-background-color: #7FFF00");
//        uploadedPic = getOptimizedImage(uploadedPic);
//        picUploadTGL.getStyleClass().add("toggle-button-picUploaded");
//      }
//    });
//    exitBUTTON.setOnMouseClicked(e -> System.exit(0));
//
//    initialImage = getOptimizedImage(new Image("images/cat.jpg", 400, 400, true, true));
//
//  }
//
//  private void initializeListeners() {
////    bind pane's visibility to its animation
//    newGameMainPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_OPTIONS: Animator.HIDE_OPTIONS,
//            newGameMainPANE);
//      }
//    });
//    goButtonPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_GO : Animator.HIDE_GO, goButtonPANE);
//      }
//    });
//    peekBORDERPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_PEEK : Animator.HIDE_PEEK, peekBORDERPANE);
//      }
//    });
//    movesPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_MOVES : Animator.HIDE_MOVES, movesPANE);
//      }
//    });
//    gridBORDERPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_BOARD : Animator.HIDE_BOARD, gridBORDERPANE);
//      }
//    });
//    shuffleButtonPANE.visibleProperty().addListener((observable, was, isVisible) -> {
//      if (was != isVisible) {
//        animationExecuter(isVisible ? Animator.SHOW_SHUFFLE : Animator.HIDE_SHUFFLE,
//            shuffleButtonPANE);
//      } else {
//        animationExecuter(Animator.NUDGE, shuffleButtonPANE);
//      }
//    });
//
////    initialize other bindings
//    isActive.bindBidirectional(GameState.isActiveProperty());
//    gridSizeTF.textProperty()
//        .bindBidirectional(GameState.gridSizeProperty(), new NumberStringConverter());
//    movesLABEL.textProperty().bind(GameState.currentMovesProperty().asString());
//    picModeTGL.selectedProperty().addListener((observable, was, isSelected) -> {
//      if (isSelected.booleanValue()) {
//        GameState.setMode(GameMode.PICTURE);
//      } else {
//        GameState.setMode(GameMode.NORMAL);
//      }
//      setVisibility(isSelected.booleanValue(), uploadPANE);
//    });
//    picUploadTGL.selectedProperty().addListener((observable, was, isSelected) -> {
//      setVisibility(isSelected.booleanValue(), browsePANE);
//    });
//    gridSizeTF.textProperty().addListener((observable, oldText, newText) -> {
////      TODO: Take this out of here and bind to a boolean expression
//      validateInput(observable, oldText, newText);
//      if (!((StringProperty) observable).getValue().isBlank()
//          && (Integer.parseInt(((StringProperty) observable).getValue()) > 18
//          || Integer.parseInt(((StringProperty) observable).getValue()) == 0)) {
//        ((StringProperty) observable).setValue(oldText);
//      }
////      if (!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText)) {
////        showGoBUTTON();
////        if (exitPosition == 1)
////          moveExitBUTTON(12);
////          TODO: fix exit button animation
////      } else {
////        if (exitPosition == 2)
////          moveExitBUTTON(21);
////          TODO: fix exit button animation
////        hideGoBUTTON();
////      }
////    });
//      setVisibility(!gridSizeTF.getText().isEmpty() && !Objects.equals(newText, oldText),
//          goButtonPANE);
//    });
//    shufflesTF.textProperty().addListener(this::validateInput);
//
//
//  }
//
//  private void showNewGameOptions() {
//    if (newGameMainPANE.isVisible()) {
//      animationExecuter(Animator.NUDGE, newGameMainPANE, goButtonPANE);
//    } else {
//      startNewGame();
//    }
//  }
//
//  private void validateNewGame() {
//    setVisibility(true, gridBORDERPANE, movesPANE);
////    animationExecuter(Animator.SHOW_BOARD, gridBORDERPANE, movesPANE);
////    set isActive to false
//    isActive.setValue(false);
//    closeCurrentGame();
//  }
//
//  private void closeCurrentGame() {
//    setVisibility(false, peekBORDERPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE);
//  }
//
//  private void startNewGame() {
//    isActive.set(true);
//  }
//
//  /*Inner Services*/
//  private void animationExecuter(Animator animation, Node... nodes) {
//    for (Node node : nodes) {
//      animation.getAnimation().setNode(node);
//      Platform.runLater(() -> {
//        animation.getAnimation().play();
//      });
//    }
//  }
//
//
//  private void setVisibility(boolean visible, Node... nodes) {
//    for (Node node : nodes) {
//      if (node.isVisible() != visible) {
//        node.setVisible(visible);
//      }
//    }
//  }
//
//  private WritableImage getOptimizedImage(Image image) {
//    System.out.println("W: " + image.getWidth() + " h: " + image.getHeight());
//
//    double w = image.getWidth(), h = image.getHeight();
//
//    image = w < h ? new Image(image.getUrl(), 400, 0, true, true)
//        : new Image(image.getUrl(), 0, 400, true, true);
//    w = (image.getWidth() - 400) / 2;
//    h = (image.getHeight() - 400) / 2;
//
//    System.out.println("W: " + image.getWidth() + " h: " + image.getHeight());
//
//    PixelReader reader = image.getPixelReader();
//    return new WritableImage(reader, (int) w, (int) h, 400, 400);
//  }
//
//  private void validateInput(Observable observable, String oldText, String newText) {
//    if (!newText.isEmpty()) {
//      for (int i = newText.length() - 1; i >= 0; i--) {
//        if (!((newText.charAt(i)) <= '9' && (newText.charAt(i)) >= '0')) {
//          ((StringProperty) observable).setValue(oldText);
//          return;
//        }
//      }
//    }
//  }
//
//}
