import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class BlockChaining
{

    public static void testECB() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //klucz szyfrujący
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        byte[] iv = new byte[64];
        new SecureRandom().nextBytes(iv);


        File textFile = new File("src/mineCBC.txt");
        File encryptedFile = new File("src/mineCBCen.txt");
        File decryptedFile = new File("src/mineCBCde.txt");

        //AES.encryptCBC(secretKey, iv, textFile,encryptedFile);
        AES.decryptCBC(secretKey, iv, encryptedFile, decryptedFile);
    }




}
