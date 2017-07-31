package clib.javafx.dialogs;


import javafx.stage.Stage;


public class InfoDialogBox extends DialogBox
{

    public InfoDialogBox(Stage owner)
    {
        super(owner);

        controller.buttonOK.setVisible(true);
        controller.buttonMinimize.setVisible(true);
        controller.buttonExit.setVisible(true);
        controller.buttonExit.setOnAction(e -> controller.close(ExitCode.OK));

        setIcon("info72px.png");
        setStyleClass("mode-info");
    }
}
