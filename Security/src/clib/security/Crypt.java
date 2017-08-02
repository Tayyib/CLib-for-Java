package clib.security;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;


/**
 * Forked from CodeJava.net.
 * A utility class that encrypts/decrypts texts and files.
 *
 * @author M. Tayyib Yel (m.tayyib.yel@gmail.com)
 */


public class Crypt
{
    private final String algorithm;
    private final String transformation;


    public Crypt(String algorithm)
    {
        this.algorithm = algorithm;
        this.transformation = algorithm;
    }

    public byte[] step(int cipherMode, byte[] inputBytes, String key) throws Exception
    {
        Key secretKey = new SecretKeySpec(key.getBytes(), algorithm);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(cipherMode, secretKey);

        return cipher.doFinal(inputBytes);
    }

    public void step(int cipherMode, File file, String key) throws Exception
    {
        step(cipherMode, file, file, key);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess"})
    public void step(int cipherMode, File inputFile, File outputFile, String key) throws Exception
    {
        try (FileInputStream inputStream = new FileInputStream(inputFile))
        {
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(step(cipherMode, inputBytes, key));
            outputStream.close();
        }
    }
}
