package examples.JavaFX;


import clib.javafx.dialogs.ErrorDialogBox;
import clib.javafx.dialogs.ExitCode;
import clib.javafx.dialogs.InfoDialogBox;
import clib.javafx.dialogs.WarningDialogBox;

import javafx.application.Application;
import javafx.stage.Stage;


public class E_Dialogs extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ErrorDialogBox errorDialogBox = new ErrorDialogBox(primaryStage);
        InfoDialogBox infoDialogBox = new InfoDialogBox(primaryStage);
        WarningDialogBox warningDialogBox = new WarningDialogBox(primaryStage);

        errorDialogBox.setTitle("Hata!");
        errorDialogBox.setMessage("Bilinmeyen bir hata meydana geldi!");
        errorDialogBox.showAndWait();

        if (warningDialogBox.showAndWait().equals(ExitCode.YES))
        {
            infoDialogBox.setTitle("Uygulama güncellendi!");
            infoDialogBox.setMessage("Yenilikler: \n...\n...\n...");
            infoDialogBox.show();
        }
    }
}
