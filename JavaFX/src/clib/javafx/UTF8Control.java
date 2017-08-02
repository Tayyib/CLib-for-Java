package clib.javafx;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class UTF8Control extends ResourceBundle.Control
{
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException
    {
        ResourceBundle bundle = null;
        final String bundleName = toBundleName(baseName, locale);
        final String resourceName = toResourceName(bundleName, "properties");
        final ClassLoader classLoader = loader;
        final boolean reloadFlag = reload;

        InputStream stream;
        try
        {
            stream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>) () -> {
                InputStream is = null;
                if (reloadFlag)
                {
                    URL url = classLoader.getResource(resourceName);
                    if (url != null)
                    {
                        URLConnection connection = url.openConnection();
                        if (connection != null)
                        {
                            connection.setUseCaches(false);
                            is = connection.getInputStream();
                        }
                    }
                }
                else
                {
                    is = classLoader.getResourceAsStream(resourceName);
                }
                return is;
            });
        }
        catch (PrivilegedActionException e)
        {
            throw (IOException) e.getException();
        }

        if (stream != null)
        {
            try
            {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            }
            finally
            {
                stream.close();
            }
        }
        return bundle;
    }
}
