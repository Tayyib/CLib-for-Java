package clib.javafx.dialogs;


import clib.javafx.UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


@SuppressWarnings("FieldCanBeLocal")
class DialogBox
{
    private final Stage stage;
    private final Scene scene;
    final DialogBoxController controller;


    DialogBox(Stage owner)
    {
        FXMLLoader loader = UI.loadFXML(DialogBox.class, "/clib/javafx/dialogs/fxml/DialogBox.fxml");

        controller = loader.getController();
        stage = new Stage();
        scene = new Scene(controller.window);
        controller.stage = stage;

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.centerOnScreen();
    }

    // Auto...

    void setIcon(String iconName)
    {
        controller.imageView.setImage(new Image(
                DialogBox.class.getResourceAsStream("/clib/javafx/dialogs/icons/" + iconName)));
    }

    void setStyleClass(String mode)
    {
        controller.window.getStyleClass().add(mode);
    }

    // Manual...

    public ExitCode showAndWait()
    {
        stage.showAndWait();
        return controller.exitCode;
    }

    @SuppressWarnings("WeakerAccess")
    public void close()
    {
        stage.close();
    }

    public void setTitle(String title)
    {
        stage.setTitle(title);
        controller.labelTitle.setText(title);
    }

    public void setMessage(String message)
    {
        controller.labelMessage.setText(message);
    }

    public void show()
    {
        stage.show();
    }
}
