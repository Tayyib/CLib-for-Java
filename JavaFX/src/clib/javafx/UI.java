package clib.javafx;


import clib.javafx.transitions.FadeTransition;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



@SuppressWarnings("unused")
public class UI
{
    public static FXMLLoader loadFXML(Class baseClass, String source)
    {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlURL = baseClass.getResource(source);

        loader.setLocation(fxmlURL);
        loader.setBuilderFactory(new JavaFXBuilderFactory());

        try { loader.load(fxmlURL.openStream()); }
        catch (IOException e) { e.printStackTrace(); }

        return loader;
    }

    public static Image image(Class Class, String source)
    {
        return new Image(Class.getResourceAsStream(source));
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
        FadeTransition effect2 = new FadeTransition(nextScene.getRoot(), 510, 0,1);

        nextScene.getRoot().setOpacity(0);

        effect1.setOnFinished(event -> { stage.setScene(nextScene); effect2.play(); });
        effect1.play();
    }

    public static void inThread(Runnable runnable)
    {
        Platform.runLater(runnable);
    }
}
