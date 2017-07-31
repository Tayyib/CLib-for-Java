package clib.javafx.dialogs;


import javafx.stage.Stage;


public class ErrorDialogBox extends DialogBox
{
    public ErrorDialogBox(Stage owner)
    {
        super(owner);

        controller.buttonClose.setVisible(true);
        controller.buttonMinimize.setVisible(false);
        controller.buttonExit.setVisible(true);
        controller.buttonExit.setOnAction(e -> controller.close(ExitCode.CLOSE));

        setIcon("error72px.png");
        setStyleClass("mode-error");
    }
}
