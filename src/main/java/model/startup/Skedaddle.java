package model.startup;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Skedaddle extends Application {

  private static final Logger logger = LogManager.getLogger(Skedaddle.class);


    public static Stage stage = null;

    private double xPos = 0;
    private double yPos = 0;

    public static void main(String[] args) {
        logger.debug("Skedaddle main started.");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.debug("Skedaddle start was entered.");
        Skedaddle.stage = primaryStage;
        try {
            logger.debug("Loading \"MainUI.fxml\"...");
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainUI.fxml"));

            Objects.requireNonNull(root).setOnMousePressed(e -> {
                xPos = e.getSceneX();
                yPos = e.getSceneY();
            });

            root.setOnMouseDragged(e -> {
                stage.setOpacity(0.7f);
                stage.setX(e.getScreenX() - xPos);
                stage.setY(e.getScreenY() - yPos);
            });

            root.setOnMouseReleased(e -> stage.setOpacity(1f));

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            logger.debug("Loading \"/css/UIStyleSheet.css\"...");


            scene.getStylesheets().add(getClass().getResource("/css/UIStyleSheet.css").toExternalForm());
            primaryStage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);

            logger.debug("Showing stage...");
            primaryStage.show();

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
