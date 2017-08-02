package clib.javafx.dialogs;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DialogBoxController implements Initializable
{
    public VBox window;
    public Label labelTitle;
    public Button buttonMinimize;
    public Button buttonExit;
    public Label labelMessage;
    public ImageView imageView;
    public Button buttonOK;
    public Button buttonNo;
    public Button buttonYes;
    public Button buttonContinue;
    public Button buttonCancel;
    public Button buttonClose;

    private double x, y;

    Stage stage;
    ExitCode exitCode = ExitCode.NULL;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        buttonOK.managedProperty().bind(buttonOK.visibleProperty());
        buttonNo.managedProperty().bind(buttonNo.visibleProperty());
        buttonYes.managedProperty().bind(buttonYes.visibleProperty());
        buttonContinue.managedProperty().bind(buttonContinue.visibleProperty());
        buttonCancel.managedProperty().bind(buttonCancel.visibleProperty());
        buttonClose.managedProperty().bind(buttonClose.visibleProperty());

        buttonOK.setOnAction(e -> close(ExitCode.OK));
        buttonNo.setOnAction(e -> close(ExitCode.NO));
        buttonYes.setOnAction(e -> close(ExitCode.YES));
        buttonContinue.setOnAction(e -> close(ExitCode.CONTINUE));
        buttonCancel.setOnAction(e -> close(ExitCode.CANCEL));
        buttonClose.setOnAction(e -> close(ExitCode.CLOSE));

        buttonMinimize.setOnAction(e -> stage.setIconified(true));
    }

    void close(ExitCode exitCode)
    {
        this.exitCode = exitCode;
        stage.close();
    }

    // Events
    public void onMousePressed(MouseEvent event)
    {
        if (event.isSecondaryButtonDown()) event.consume();

        x = stage.getX() - event.getScreenX();
        y = stage.getY() - event.getScreenY();
    }

    public void onMouseDragged(MouseEvent event)
    {
        stage.setX(event.getScreenX() + x);
        stage.setY(event.getScreenY() + y);
    }
}
