package clib.javafx.i18n;


import java.awt.ComponentOrientation;
import java.util.Locale;
import java.util.ResourceBundle;

import com.sun.istack.internal.NotNull;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.geometry.NodeOrientation;


/**
 * <p><b>Warning!</b> There are some reasons to think twice before using this class:</p>
 * <br>
 * <p><b>1) NOT tested</b> on complicated apps.</p>
 * <p><b>2) NOT optimized</b> very well. So, it may use too much memory.</p>
 * <p><b>3) NOT completed.</b> There are still features to be added.</p>
 * <p><b>4) NOT documented.</b> You must first read the example codes in the module "Examples"</p>
 */
@SuppressWarnings("unchecked")
public class I18N
{
    private static final ObjectProperty<Locale> LOCALE = new SimpleObjectProperty<>();
    private static final SetProperty<Locale> SUPPORTED_LOCALES = new SimpleSetProperty<>();
    private static final MapProperty<String, ResourceBundle> BUNDLES = new SimpleMapProperty<>();
    private static final MapProperty<StringProperty, I18NString> BINDINGS = new SimpleMapProperty<>();
    private static final SetProperty<ObjectProperty<NodeOrientation>> ORIENTATIONS = new SimpleSetProperty<>();
    private static final ObjectProperty<NodeOrientation> ORIENTATION = new SimpleObjectProperty<>();

    static
    {
        SUPPORTED_LOCALES.set(FXCollections.observableSet());
        BUNDLES.set(FXCollections.observableHashMap());
        BINDINGS.set(FXCollections.observableHashMap());
        ORIENTATIONS.set(FXCollections.observableSet());

        ORIENTATIONS.addListener((SetChangeListener<ObjectProperty<NodeOrientation>>) change ->
        {
            if (change.wasAdded()) change.getElementAdded().set(ORIENTATION.get());
        });

        ORIENTATION.addListener((observable, old, newValue) -> ORIENTATIONS.forEach(p -> p.set(newValue)));
        LOCALE.addListener((observable, oldValue, newValue) -> ORIENTATION.set(getOrientation(newValue)));
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

    public static void setLocale(Locale defaultLocale, boolean autoDetect)
    {
        //noinspection StatementWithEmptyBody
        if (autoDetect && getLocaleAutomatically()) ;
        else I18N.LOCALE.set(defaultLocale);
    }

    private static NodeOrientation getOrientation(Locale locale)
    {
        ComponentOrientation awtOrientation = ComponentOrientation.getOrientation(locale);

        if (awtOrientation.equals(ComponentOrientation.RIGHT_TO_LEFT)) return NodeOrientation.RIGHT_TO_LEFT;
        else return NodeOrientation.LEFT_TO_RIGHT;
    }

    public static void createBinding(String bundleKey, StringProperty textProperty, String key, Object... args)
    {
        I18NString i18NString = new I18NString(bundleKey);
        textProperty.bind(i18NString);

        if (args.length > 0) i18NString.set(key, args);
        else i18NString.set(key);

        BINDINGS.put(textProperty, i18NString);
    }

    public static void updateBinding(StringProperty textProperty, @NotNull String key, Object... args)
    {
        I18NString i18NString = BINDINGS.get(textProperty);

        if (key == null) i18NString.update(args);
        else if (args.length > 0) i18NString.set(key, args);
        else i18NString.set(key);
    }

    public static void updateArgs(StringProperty textProperty, Object... args)
    {
        updateBinding(textProperty, null, args);
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

    public static ObservableMap<StringProperty, I18NString> getBindings()
    {
        return BINDINGS.get();
    }

    public static ObservableSet<ObjectProperty<NodeOrientation>> getOrientations()
    {
        return ORIENTATIONS.get();
    }
}
