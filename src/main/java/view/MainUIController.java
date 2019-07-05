package view;

import animatefx.animation.Tada;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RegexValidator;
import controller.GamePlayManager;
import controller.GameState;
import controller.GameState.GameMode;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.service.Animator;
import model.service.ExitButtonAnimator;
import model.service.NodeAnimator;
import model.service.TransitionsGenerator;
import model.startup.Skedaddle;
import model.tiles.FXTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SuppressWarnings("Duplicates")
public class MainUIController {


  private static final Logger logger = LogManager.getLogger(MainUIController.class);

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
  private Group tilesGroup;
  private Group peekGroup;
  private ConcurrentHashMap<Node, NodeAnimator> nodesAnimations;
  private BooleanProperty isActive;
  private Image initialImage;
  private Image uploadedPic;
  private GamePlayManager gamePlayManager;
  private FileChooser fileChooser = new FileChooser();
  private RegexValidator gridSizeValidator;
  private RegexValidator numberOfShufflesValidator;
  private IntegerBinding numberOfShuffles;

  @FXML
  private void initialize() {

    logger.traceEntry("initialize");

    gamePlayManager = GamePlayManager.getInstance();
    gamePlayManager.setMainUIController(this);

    // Hide all unneeded panes
    setVisibility(false, goButtonPANE, gameOptionsPANE, peekPANE, uploadPANE, movesPANE, browsePANE,
        gridBORDERPANE, peekBORDERPANE, shuffleButtonPANE, shufflePANE);

    // Initialize needed variables
    isActive = new SimpleBooleanProperty();

    fileChooser.setTitle("Upload an image");
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
    initialImage = optimizeImage(new Image("images/cat.jpg", 400, 400, true, true));

    // Initialize TextFields' validators
    gridSizeValidator = new RegexValidator("3-18");
    gridSizeValidator.setRegexPattern("(^$)|([1-9]|1[0-8])");
    gridSizeTF.getValidators().add(gridSizeValidator);
    numberOfShufflesValidator = new RegexValidator("Currently 1000 is the max!");
    numberOfShufflesValidator.setRegexPattern("^([1-9][0-9]{0,2}|1000)$");
    shufflesTF.getValidators().add(numberOfShufflesValidator);

    // Initialize tiles holders
    tilesGroup = new Group();
    peekGroup = new Group();
    gridBORDERPANE.setCenter(tilesGroup);
    peekBORDERPANE.setCenter(peekGroup);

    // Initialize needed bindings, listeners & other...
    initializeAnimations();

    initializeBindings();

    setEventHandlers();

    addListeners();

    logger.traceExit("initialize");

  }

  /**
   * Activates required bindings.
   */
  private void initializeBindings() {

    logger.traceEntry("initializing bindings.");

    isActive.bindBidirectional(GameState.isActiveProperty());

    GameState.gridSizeProperty().bind(Bindings.createIntegerBinding(
        () -> gridSizeTF.getText().isEmpty() ? Integer.parseInt(gridSizeTF.getPromptText())
            : Integer.parseInt(gridSizeTF.getText()), gridSizeTF.textProperty()));

    numberOfShuffles = Bindings.createIntegerBinding(
        () -> shufflesTF.getText().isEmpty() ? Integer.parseInt(shufflesTF.getPromptText())
            : Integer.parseInt(shufflesTF.getText()), shufflesTF.textProperty());

    movesLABEL.textProperty().bind(GameState.currentMovesProperty().asString());

    logger.traceExit("done with bindings.");

  }

  /**
   * Sets required event handlers.
   */
  private void setEventHandlers() {

    logger.traceEntry("initializing event handlers.");

    newGameBUTTON.setOnAction(handle -> {
      if (isActive.get()) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.showAndWait().filter(response -> response == ButtonType.OK)
            .ifPresent(response -> {
              isActive.set(false);
              animationActivator(false, peekBORDERPANE, movesPANE, gridBORDERPANE,
                  shuffleButtonPANE, shufflePANE);
            });
      } else {
        if (gameOptionsPANE.isVisible()) {
          animationExecutor(Animator.NUDGE,
              gameOptionsPANE/*TODO check why <-- this pane doesn't nudge*/, goButtonPANE);
        } else {
          setVisibility(true, gameOptionsPANE, goButtonPANE, exitPANE);
        }
      }
    });

    browseBUTTON.setOnAction(handle -> {
      File tmp = fileChooser.showOpenDialog(Skedaddle.stage);
      if (tmp != null) {
        browsePANE.setStyle("-fx-background-color: #7FFF00");
        uploadedPic = new Image(tmp.toURI().toString());
        uploadedPic = optimizeImage(uploadedPic);
        picUploadTGL.getStyleClass().add("toggle-button-picUploaded");
      }
    });

    goBUTTON.setOnAction(handle -> {
      if (gridSizeTF.getText() == "1") {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setContentText("With 1 tile it's not really a game!");
        errorAlert.showAndWait();
      } else {
        isActive.set(true);
      }
    });


    /*TODO: check why it doesn't show*/
    peekBUTTON.setOnAction(handle -> setVisibility(!peekBORDERPANE.isVisible(), peekBORDERPANE));

    shuffleBUTTON.setOnAction(handle -> setVisibility(!shufflePANE.isVisible(), shufflePANE));

    doItBUTTON.setOnAction(
        handle -> gamePlayManager.shuffleBoard(numberOfShuffles.get()));

    exitBUTTON.setOnAction(handle -> System.exit(0));

    logger.traceExit("done with event handlers.");

  }


  /**
   * Adds required listeners.
   */
  private void addListeners() {

    logger.traceEntry("adding listeners.");

    isActive.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        setVisibility(false, gameOptionsPANE, goButtonPANE, uploadPANE, browsePANE);
        PauseTransition pause = TransitionsGenerator.generaPauseTransition(0.3, doThis -> {
          picModeTGL.setSelected(false);
          picUploadTGL.setSelected(false);
        });
        pause.play();
        setVisibility(true, exitPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE, peekPANE,
            peekBORDERPANE);
      } else {
        setVisibility(false, exitPANE, movesPANE, gridBORDERPANE, shuffleButtonPANE, peekPANE);
        setVisibility(true, gameOptionsPANE, goButtonPANE);
      }
    });
    // Listen to grid size in order to enforce 1-18 input
    gridSizeTF.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!gridSizeTF.validate()) {
        gridSizeTF.setText(oldValue);
      }
    });

    // Listen to changes of picture mode toggle button
    picModeTGL.selectedProperty().addListener((observable, was, isSelected) -> {
      if (isSelected) {
        GameState.setMode(GameMode.PICTURE);
      } else {
        GameState.setMode(GameMode.NORMAL);
        picUploadTGL.setSelected(false);
      }
      setVisibility(isSelected, uploadPANE);
    });

    // Listen to changes of picture upload toggle button
    picUploadTGL.selectedProperty().addListener((observable, was, isSelected) -> {
      setVisibility(isSelected, browsePANE);
    });

    logger.traceExit("done with listeners.");

  }


  public void loadSolution(ArrayList<? extends FXTile> tiles) {
    // TODO: make it a snapshot of the board before shuffling and show the image?
    //  depends on what will the "peek pane" include and how it will function later on.

    peekGroup.getChildren().clear();
    peekGroup.getChildren().addAll(tiles);
  }


  public void loadBoard(ArrayList<? extends FXTile> tiles) {
    tilesGroup.getChildren().clear();
    tilesGroup.getChildren().addAll(tiles);
    gridBORDERPANE.setCenter(tilesGroup);
  }

//------------------------------------------------------------------------------------------------//
//   _____                           _                       __  __      _   _               _
//  / ____|                         (_)                     |  \/  |    | | | |             | |
// | |     ___  _ ____   _____ _ __  _  ___ _ __   ___ ___  | \  / | ___| |_| |__   ___   __| |___
// | |    / _ \| '_ \ \ / / _ \ '_ \| |/ _ \ '_ \ / __/ _ \ | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
// | |___| (_) | | | \ V /  __/ | | | |  __/ | | | (_|  __/ | |  | |  __/ |_| | | | (_) | (_| \__ \
//  \_____\___/|_| |_|\_/ \___|_| |_|_|\___|_| |_|\___\___| |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
//
//------------------------------------------------------------------------------------------------//


  /**
   * Sets the nodes' visibility and activates its animation.
   *
   * @param visible boolean indicating nodes' new visible state.
   * @param nodes to set visibility to and activate animation on.
   */
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

  /**
   * Activates animation on passed nodes.
   *
   * @param makeVisible should be false to hide the node.
   * @param nodes nodes to activate animation on.
   */
  private void animationActivator(boolean makeVisible, Node... nodes) {
    for (Node node : nodes) {
      Platform.runLater(() -> {
        Platform.runLater(() -> {

          if (makeVisible) {
            nodesAnimations.get(node).show();
          } else {
            nodesAnimations.get(node).hide();
          }
        });
      });

    }
  }

  /**
   * Plays passed animation on all nodes.
   *
   * @param animation to be played.
   * @param nodes to be animated.
   */
  private void animationExecutor(Animator animation, Node... nodes) {
    for (Node node : nodes) {
      animation.getAnimation().setNode(node);
      Platform.runLater(() ->
          animation.getAnimation().play()
      );
    }
  }

  /**
   * Optimizes passed image by stretching it by the smaller side so it covers 400 x 400 then takes
   * its center.
   *
   * @param image to be optimized.
   * @return {@see WritableImage} 400 width 400 height.
   */
  private WritableImage optimizeImage(Image image) {
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

  public void showWinnerScreen() {
    Text wellDoneText = new Text("Well Done");
    Text smileyFace = new Text(":)");
    VBox vBoxContainer = new VBox(wellDoneText, smileyFace);
    smileyFace.setRotate(90);
    wellDoneText.setFont(Font.font("Eras Bold ITC", 70));
    wellDoneText.setFill(Color.WHITE);
    smileyFace.setFont(Font.font("Eras Bold ITC", 120));
    smileyFace.setFill(Color.DARKORANGE);
    vBoxContainer.setAlignment(Pos.CENTER);
    vBoxContainer.setSpacing(10);
    new Tada(gridBORDERPANE).play();
    gridBORDERPANE.setCenter(vBoxContainer);
  }
}