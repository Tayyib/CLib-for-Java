package clib.javafx.dialogs;


import clib.javafx.i18n.BundleControl;
import clib.javafx.FXML;

import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        ResourceBundle bundle = ResourceBundle.getBundle("clib.javafx.dialogs.l10n.Buttons", new BundleControl());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/DialogBox.fxml"), bundle);
        Parent root = FXML.load(loader);

        assert root != null;

        scene = new Scene(root);
        stage = new Stage();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.centerOnScreen();

        controller = loader.getController();
        controller.stage = stage;
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
