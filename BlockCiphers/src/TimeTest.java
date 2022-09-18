import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeTest {


    public static void getResults() throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException, BadPaddingException {

        //klucz szyfrujący
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");


        File textFile = new File("src/time_testing/tekst.txt");
        File encryptedFile = new File("src/time_testing/tekstFunc.txt");
        File decryptedFile = new File("src/time_testing/tekstEnd.txt");

        File textFile2 = new File("src/time_testing/tekst2.txt");
        File encryptedFile2 = new File("src/time_testing/tekstFun2c.txt");
        File decryptedFile2 = new File("src/time_testing/tekstEnd2.txt");

        File textFile3 = new File("src/time_testing/tekst3.txt");
        File encryptedFile3 = new File("src/time_testing/tekstFunc3.txt");
        File decryptedFile3 = new File("src/time_testing/tekstEnd3.txt");


        ArrayList<String> modes = new ArrayList<>(Arrays.asList("AES/ECB/PKCS5Padding", "AES/CBC/PKCS5Padding", "AES/CFB/PKCS5Padding", "AES/OFB/PKCS5Padding", "AES/CTR/PKCS5Padding"));


        for(String mode: modes){
                System.out.println(mode);
                System.out.print("Text1   ");
                runCipher(mode,secretKey,textFile,encryptedFile,decryptedFile);
                System.out.print("Text2   ");
                runCipher(mode,secretKey,textFile2,encryptedFile2,decryptedFile2);
                System.out.print("Text3   ");
                runCipher(mode,secretKey,textFile3,encryptedFile3,decryptedFile3);
            }


    }

    private static void runCipher(String mode, SecretKey secretKey, File textFile, File encryptedFile, File decryptedFile ) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        long avg1=0;
        long avg2=0;
        for (int i=0; i<10; i++){
            String algorithm = mode;
            IvParameterSpec iv = AES.generateIv();

            long start1 = System.nanoTime();
            AES.encryptFile(algorithm,secretKey,iv,textFile,encryptedFile);
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            AES.decryptFile(algorithm,secretKey,iv,encryptedFile,decryptedFile);
            long end2 = System.nanoTime();

            avg1+=end1-start1;
            avg2+=end2-start2;

        }
        System.out.println("Encryption time in nano seconds: "+ avg1/10 + "     Decryption time: "+avg2/10) ;

    }

}
