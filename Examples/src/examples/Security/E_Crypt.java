package examples.Security;


import clib.security.Crypt;

import javax.crypto.Cipher;


public class E_Crypt
{
    public static void main(String[] args)
    {
        Crypt crypt = new Crypt("AES");

        try
        {
            byte[] encryptedText = crypt.step(Cipher.ENCRYPT_MODE, "metin".getBytes(), "ENve16ByteOlmali");
            byte[] decryptedText = crypt.step(Cipher.DECRYPT_MODE, encryptedText, "ENve16ByteOlmali");

            System.out.println(new String(encryptedText));
            System.out.println(new String(decryptedText));

            // Encrypting/decrypting files
            // crypt.step(Cipher.ENCRYPT_MODE, file, "1234567890123456");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
