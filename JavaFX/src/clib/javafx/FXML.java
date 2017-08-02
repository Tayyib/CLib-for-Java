package clib.javafx;


import clib.javafx.transitions.FadeTransition;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXML
{
    public static Parent load(FXMLLoader fxmlLoader)
    {
        try { return fxmlLoader.load(); }
        catch (IOException e) { e.printStackTrace(); return null; }
    }

    public static void switchPane(Pane nextPane, Pane parent)
    {
        nextPane.setOpacity(0);
        nextPane.prefWidthProperty().bind(parent.widthProperty());
        nextPane.prefHeightProperty().bind(parent.heightProperty());

        parent.getChildren().clear();
        parent.getChildren().add(nextPane);

        new FadeTransition(nextPane, 510, 1).play();
    }

    public static void switchScene(Stage stage, Scene currentScene, Scene nextScene)
    {
        FadeTransition effect1 = new FadeTransition(currentScene.getRoot(), 300, 0);
        FadeTransition effect2 = new FadeTransition(nextScene.getRoot(), 510, 0, 1);

        nextScene.getRoot().setOpacity(0);

        effect1.setOnFinished(event -> { stage.setScene(nextScene); effect2.play(); });
        effect1.play();
    }
}
