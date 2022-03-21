import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class BlockChaining
{

    public static void test() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        String s_iv = "myinitvec128bits";
        byte[] iv = s_iv.getBytes(StandardCharsets.UTF_8);

        File textFile = new File("src/cipher_testing/message.txt");

        File encryptedFileECB = new File("src/cipher_testing/encryptedECB.txt");
        File decryptedFileECB = new File("src/cipher_testing/decryptedECB.txt");
        AES.encryptECB(secretKey, textFile, encryptedFileECB);
        AES.decryptECB(secretKey, encryptedFileECB, decryptedFileECB);

        File encryptedFilePBC = new File("src/cipher_testing/encryptedPBC.txt");
        File decryptedFilePBC = new File("src/cipher_testing/decryptedPBC.txt");
        AES.encryptPBC(secretKey, iv, textFile,encryptedFilePBC);
        AES.decryptPBC(secretKey, iv, encryptedFilePBC, decryptedFilePBC);

        File encryptedFileCBC = new File("src/cipher_testing/encryptedCBC.txt");
        File decryptedFileCBC = new File("src/cipher_testing/decryptedCBC.txt");
        AES.encryptCBC(secretKey, iv, textFile, encryptedFileCBC);
        AES.decryptCBC(secretKey, iv, encryptedFileCBC, decryptedFileCBC);

    }

    public static void modificationTestEncrypt() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        String s_iv = "myinitvec128bits";
        byte[] iv = s_iv.getBytes(StandardCharsets.UTF_8);

        File textFile = new File("src/cipher_testing/simple_text.txt");

        File encryptedFileECB = new File("src/cipher_testing/encryptedECB.txt");
        AES.encryptECB(secretKey, textFile, encryptedFileECB);

        File encryptedFilePBC = new File("src/cipher_testing/encryptedPBC.txt");
        AES.encryptPBC(secretKey, iv, textFile,encryptedFilePBC);

        File encryptedFileCBC = new File("src/cipher_testing/encryptedCBC.txt");
        AES.encryptCBC(secretKey, iv, textFile, encryptedFileCBC);

    }

    public static void modificationTestDecrypt() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        String s_iv = "myinitvec128bits";
        byte[] iv = s_iv.getBytes(StandardCharsets.UTF_8);

        File encryptedFileECB = new File("src/cipher_testing/encryptedECB.txt");
        File decryptedFileECB = new File("src/cipher_testing/decryptedECB.txt");
        AES.decryptECB(secretKey, encryptedFileECB, decryptedFileECB);

        File encryptedFilePBC = new File("src/cipher_testing/encryptedPBC.txt");
        File decryptedFilePBC = new File("src/cipher_testing/decryptedPBC.txt");
        AES.decryptPBC(secretKey, iv, encryptedFilePBC, decryptedFilePBC);

        File encryptedFileCBC = new File("src/cipher_testing/encryptedCBC.txt");
        File decryptedFileCBC = new File("src/cipher_testing/decryptedCBC.txt");
        AES.decryptCBC(secretKey, iv, encryptedFileCBC, decryptedFileCBC);
    }

    public static void testECBUser() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        System.out.print("Enter secret key (16 bytes): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String encryptionKeyString = reader.readLine();
        byte[] keyBytes = encryptionKeyString.getBytes();


        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");


        File textFile = new File("src/cipher_testing/message.txt");
        File encryptedFile = new File("src/cipher_testing/encryptedECB.txt");
        File decryptedFile = new File("src/cipher_testing/decryptedECB.txt");

        AES.encryptECB(secretKey, textFile,encryptedFile);
        AES.decryptECB(secretKey, encryptedFile, decryptedFile);
    }

    public static void testCBCUser() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        System.out.print("Enter secret key (16 bytes): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String encryptionKeyString = reader.readLine();
        byte[] keyBytes = encryptionKeyString.getBytes();


        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        System.out.print("Enter init vector (16 bytes): ");
        String s_iv = reader.readLine();
        byte[] iv = s_iv.getBytes(StandardCharsets.UTF_8);

        File textFile = new File("src/cipher_testing/message.txt");
        File encryptedFile = new File("src/cipher_testing/encryptedCBC.txt");
        File decryptedFile = new File("src/cipher_testing/decryptedCBC.txt");

        AES.encryptCBC(secretKey, iv, textFile,encryptedFile);
        AES.decryptCBC(secretKey, iv, encryptedFile, decryptedFile);
    }

    public static void testPBCUser() throws NoSuchPaddingException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //klucz szyfrujący
        System.out.print("Enter secret key (16 bytes): ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String encryptionKeyString = reader.readLine();
        byte[] keyBytes = encryptionKeyString.getBytes();


        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        System.out.print("Enter init vector (16 bytes): ");
        String s_iv = reader.readLine();
        byte[] iv = s_iv.getBytes(StandardCharsets.UTF_8);

        File textFile = new File("src/cipher_testing/message.txt");
        File encryptedFile = new File("src/cipher_testing/encryptedPBC.txt");
        File decryptedFile = new File("src/cipher_testing/decryptedPBC.txt");

        AES.encryptPBC(secretKey, iv, textFile,encryptedFile);
        AES.decryptPBC(secretKey, iv, encryptedFile, decryptedFile);
    }










}
