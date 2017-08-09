package examples.JavaFX.i18n;


import javafx.beans.property.StringProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class ComboBoxCellFactory implements Callback<ListView<StringProperty>, ListCell<StringProperty>>
{
    @Override
    public ListCell<StringProperty> call(ListView<StringProperty> param)
    {
        return new ListCell<StringProperty>()
        {
            private final Label label;
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                label = new Label();
            }

            @Override
            protected void updateItem(StringProperty item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null || empty)
                {
                    setGraphic(null);
                }
                else
                {
                    label.textProperty().bind(item);
                    setGraphic(label);
                }
            }
        };
    }
}
