package model.startup;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Skedaddle extends Application {

    public static Stage stage = null;

    private double xPos = 0;
    private double yPos = 0;

    @Override
    public void start(Stage primaryStage) {

        Skedaddle.stage = primaryStage;
        try {
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
            scene.getStylesheets().add(getClass().getResource("/css/UIStyleSheet.css").toExternalForm());
            primaryStage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
