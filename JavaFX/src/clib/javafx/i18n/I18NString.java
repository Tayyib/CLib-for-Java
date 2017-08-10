package clib.javafx.i18n;


import java.nio.charset.StandardCharsets;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;


public class I18NString extends SimpleStringProperty
{
    private String key;
    private Object[] arguments;
    private ResourceBundle bundle;


    private static ResourceBundle getBundle(String bundleKey)
    {
        return I18N.getBundles().get(I18N.getLocale().toLanguageTag() + ':' + bundleKey);
    }

    private static String translate(ResourceBundle bundle, String key)
    {
        try
        {
            byte[] byteArray = bundle.getString(key).getBytes(StandardCharsets.ISO_8859_1);
            return new String(byteArray, StandardCharsets.UTF_8);
        }
        catch (MissingResourceException e) { return "i18n::null"; }
    }

    I18NString(String bundleKey)
    {
        I18N.LOCALEProperty().addListener((observable, oldValue, newValue) ->
        {
            bundle = getBundle(bundleKey);

            if (arguments != null) set(key, arguments);
            else set(key);
        });

        bundle = getBundle(bundleKey);
    }

    @Override
    public void set(String newValue)
    {
        key = newValue;
        arguments = null;
        super.set(translate(bundle, newValue));
    }

    public void set(String newValue, Object... args)
    {
        key = newValue;
        arguments = args;
        super.set(String.format(translate(bundle, newValue), args));
    }

    public void update(Object... args)
    {
        set(key, args);
    }
}
