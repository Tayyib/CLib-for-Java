package examples.JavaFX.i18n;


import clib.javafx.FXML;
import clib.javafx.i18n.I18N;

import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class I18NApplication extends Application
{
    public static void main(String[] args)
    {
        // Prepare I18N class:
        I18N.getSupportedLocales().add(new Locale("ar"));
        I18N.getSupportedLocales().add(new Locale("en"));
        I18N.getSupportedLocales().add(new Locale("tr"));

        I18N.addBundle("examples.JavaFX.i18n.App");
        I18N.addBundle("examples.JavaFX.i18n.MainWindow");

        I18N.setLocale(Locale.ENGLISH, true);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = FXML.load(loader);

        assert root != null;

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);

        I18N.getOrientations().add(primaryStage.getScene().nodeOrientationProperty());
        I18N.createBinding("App", primaryStage.titleProperty(), "title");

        primaryStage.show();
    }
}
