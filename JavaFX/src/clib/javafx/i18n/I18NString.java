package clib.javafx.i18n;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;


public class I18NString extends SimpleStringProperty
{
    private String key;
    private Object[] arguments;
    private ResourceBundle bundle;

    private static Method translateMethod;
    static
    {
        int jvnumber = Character.getNumericValue(System.getProperty("java.version").charAt(0));

        try
        {
            translateMethod = jvnumber < 9
                    ? I18NString.class.getDeclaredMethod("translate", ResourceBundle.class, String.class)
                    : I18NString.class.getDeclaredMethod("translate9", ResourceBundle.class, String.class);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }


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
        catch (MissingResourceException e)
        {
            return String.format("null::%s", key);
        }
    }

    private static String translate9(ResourceBundle bundle, String key)
    {
        try
        {
            return bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return String.format("null::%s", key);
        }
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

        try
        {
            super.set((String) translateMethod.invoke(I18NString.class, bundle, newValue));
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    void set(String newValue, Object... args)
    {
        key = newValue;
        arguments = args;

        try
        {
            super.set(String.format((String) translateMethod.invoke(I18NString.class, bundle, newValue), args));
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    void update(Object... args)
    {
        set(key, args);
    }
}
