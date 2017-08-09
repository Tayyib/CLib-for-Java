package clib.javafx.i18n;


import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.geometry.NodeOrientation;
import javafx.stage.Stage;


@SuppressWarnings("WeakerAccess")
public class I18N
{
    private static final ObjectProperty<Locale> LOCALE = new SimpleObjectProperty<>();
    private static final SetProperty<Locale> SUPPORTED_LOCALES = new SimpleSetProperty<>();
    private static final MapProperty<String, ResourceBundle> BUNDLES = new SimpleMapProperty<>();
    private static final MapProperty<StringProperty, I18NString> BINDINGS = new SimpleMapProperty<>();

    static
    {
        SUPPORTED_LOCALES.set(FXCollections.observableSet());
        BUNDLES.set(FXCollections.observableHashMap());
        BINDINGS.set(FXCollections.observableHashMap());
    }

    public static void addBundle(String baseName)
    {
        getSupportedLocales().forEach(locale ->
                BUNDLES.put(
                        locale.toLanguageTag() + ':' + baseName.substring(baseName.lastIndexOf('.') + 1),
                        ResourceBundle.getBundle(baseName, locale)));
    }

    private static boolean getLocaleAutomatically()
    {
        Locale defaultLocale = Locale.getDefault();
        Locale defaultLanguage = new Locale(defaultLocale.getLanguage());

        if (getSupportedLocales().contains(defaultLocale)) LOCALE.set(defaultLocale);
        else if (getSupportedLocales().contains(defaultLanguage)) LOCALE.set(defaultLanguage);
        else return false;

        return true;
    }

    public static void setLocale(Locale locale)
    {
        setLocale(locale, false);
    }

    public static void setLocale(Locale locale, boolean autoDetect)
    {
        //noinspection StatementWithEmptyBody
        if (autoDetect && getLocaleAutomatically()) ;
        else I18N.LOCALE.set(locale);
    }

    public static void setOrientation(Stage primaryStage)
    {
        ComponentOrientation orientation = ComponentOrientation.getOrientation(LOCALE.get());

        if (orientation.equals(ComponentOrientation.RIGHT_TO_LEFT))
        {
            primaryStage.getScene().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        else
        {
            primaryStage.getScene().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }

    public static void createBinding(String bundleKey, StringProperty textProperty, String key, Object... args)
    {
        I18NString i18NString = new I18NString(bundleKey);
        textProperty.bind(i18NString);

        if (args.length > 0) i18NString.set(key, args);
        else i18NString.set(key);

        BINDINGS.put(textProperty, i18NString);
    }

    // GETTERS

    static Locale getLocale()
    {
        return LOCALE.get();
    }

    static ObjectProperty<Locale> LOCALEProperty()
    {
        return LOCALE;
    }

    public static ObservableSet<Locale> getSupportedLocales()
    {
        return SUPPORTED_LOCALES.get();
    }

    static ObservableMap<String, ResourceBundle> getBundles()
    {
        return BUNDLES.get();
    }
}
