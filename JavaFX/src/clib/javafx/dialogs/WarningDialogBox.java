package clib.javafx.dialogs;


import javafx.stage.Stage;


public class WarningDialogBox extends DialogBox
{

    public WarningDialogBox(Stage owner)
    {
        super(owner);

        controller.buttonYes.setVisible(true);
        controller.buttonCancel.setVisible(true);
        controller.buttonMinimize.setVisible(false);
        controller.buttonExit.setVisible(true);
        controller.buttonExit.setOnAction(e -> controller.close(ExitCode.REJECT));

        setIcon("warning72px.png");
        setStyleClass("mode-warning");
    }
}
