package examples.JavaFX.i18n;


import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;


public class ComboBoxButtonCell extends ListCell<StringProperty>
{
    @Override
    protected void updateItem(StringProperty item, boolean empty)
    {
        super.updateItem(item, empty);

        if (item != null) setText(item.get());
    }
}
