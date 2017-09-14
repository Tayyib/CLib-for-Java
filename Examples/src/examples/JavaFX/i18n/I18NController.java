package examples.JavaFX.i18n;


import clib.javafx.i18n.I18N;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class I18NController implements Initializable
{
    public Label labelTitle;
    public Label label1;
    public Button button;
    public ProgressBar progressBar;
    public ComboBox<StringProperty> comboBox;
    private StringProperty cbStringAR = new SimpleStringProperty();
    private StringProperty cbStringEN = new SimpleStringProperty();
    private StringProperty cbStringTR = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        button.setStyle("-fx-opacity: 1.0;");

        I18N.createBinding("MainWindow", labelTitle.textProperty(), "title");
        I18N.createBinding("MainWindow", label1.textProperty(), "label1", "Tayyib");
        I18N.createBinding("MainWindow", button.textProperty(), "button");

        I18N.createBinding("App", cbStringAR, "langAR");
        I18N.createBinding("App", cbStringEN, "langEN");
        I18N.createBinding("App", cbStringTR, "langTR");

        comboBox.setCellFactory(new ComboBoxCellFactory());
        comboBox.setButtonCell(new ComboBoxButtonCell());
        comboBox.setItems(FXCollections.observableArrayList(cbStringAR, cbStringEN, cbStringTR));
        comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals(0)) I18N.setLocale(new Locale("ar"));
            else if (newValue.equals(1)) I18N.setLocale(new Locale("en"));
            else if (newValue.equals(2)) I18N.setLocale(new Locale("tr"));
        });
    }

    public void startRunnable()
    {
        new Thread(() ->
        {
            button.setDisable(true);
            Platform.runLater(() -> I18N.updateBinding(button.textProperty(), "button.loading"));

            for (double i = 0; i < 101; i++)
            {
                double finalI = i;
                Platform.runLater(() -> I18N.updateArgs(button.textProperty(), finalI));
                progressBar.setProgress(i / 100);

                try { Thread.sleep(100); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }

            Platform.runLater(() -> I18N.updateBinding(button.textProperty(), "button.completed"));

            try { Thread.sleep(2000); }
            catch (InterruptedException e) { e.printStackTrace(); }

            Platform.runLater(() -> I18N.updateBinding(button.textProperty(), "button"));

            progressBar.setProgress(0);
            button.setDisable(false);
        }).start();
    }
}