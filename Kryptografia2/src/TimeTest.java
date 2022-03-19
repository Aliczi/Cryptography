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


        File textFile = new File("src/tekst.txt");
        File encryptedFile = new File("src/tekstFunc.txt");
        File decryptedFile = new File("src/tekstEnd.txt");



        ArrayList<String> modes = new ArrayList<>(Arrays.asList("AES/ECB/PKCS5Padding", "AES/CBC/PKCS5Padding", "AES/CFB/PKCS5Padding", "AES/OFB/PKCS5Padding", "AES/CTR/PKCS5Padding"));

        for(String mode: modes){
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
            System.out.println("Average "+mode+" encryption time in nano seconds: "+ avg1/10);
            System.out.println("Average "+mode+" decryption time in nano seconds: "+ avg2/10);
        }

    }


    public static void noIv() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();;
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);


        FileInputStream inputStream = new FileInputStream("out/production/Kryptografia2/tekst.txt");
        FileOutputStream outputStream = new FileOutputStream("out/production/Kryptografia2/tekst2.txt");

        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }

            String s = new String(buffer);
            System.out.println(s);

        }

        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
            String s = new String(outputBytes);
            System.out.println(s);
        }


        inputStream.close();
        outputStream.close();
    }

}
