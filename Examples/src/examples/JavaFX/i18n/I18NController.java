package examples.JavaFX.i18n;


import clib.javafx.i18n.I18N;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;


public class I18NController implements Initializable
{
    public Label labelTitle;
    public Label label1;
    public Label label2;
    public ComboBox<StringProperty> comboBox;
    private StringProperty cbStringAR = new SimpleStringProperty();
    private StringProperty cbStringEN = new SimpleStringProperty();
    private StringProperty cbStringTR = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        label2.managedProperty().bind(label2.visibleProperty());
        label2.setVisible(false);

        I18N.createBinding("MainWindow", labelTitle.textProperty(), "title");
        I18N.createBinding("MainWindow", label1.textProperty(), "label1", "Tayyib");
        I18N.createBinding("MainWindow", label2.textProperty(), "label2");

        I18N.createBinding("App", cbStringAR, "langAR");
        I18N.createBinding("App", cbStringEN, "langEN");
        I18N.createBinding("App", cbStringTR, "langTR");

        comboBox.setCellFactory(new ComboBoxCellFactory());
        comboBox.setButtonCell(new ComboBoxButtonCell());
        comboBox.setItems(FXCollections.observableArrayList(cbStringAR, cbStringEN, cbStringTR));
        comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue.equals(0)) I18N.setLocale(new Locale("ar"));
            if (newValue.equals(1)) I18N.setLocale(new Locale("en"));
            if (newValue.equals(2)) I18N.setLocale(new Locale("tr"));
        });

        // fixme! new Runnable() -> createBinding() .... update();
    }
}