import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TimeTest {


    public static void getResults() throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException, BadPaddingException {

        String encryptionKeyString =  "thisisa128bitkey";
        byte[] keyBytes = encryptionKeyString.getBytes();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        File inputFile = new File("out/production/Kryptografia2/tekst.txt");
        File outputFile = new File("out/production/Kryptografia2/tekstFunc.txt");
        File endFile = new File("out/production/Kryptografia2/tekstEnd.txt");

        long avg=0;
        for (int i=0; i<10; i++){
            String algorithm = "AES/CBC/PKCS5Padding";
            IvParameterSpec iv = AES.generateIv();
            long start1 = System.nanoTime();

            AES.encryptFile(algorithm,secretKey,iv,inputFile,outputFile);
            AES.decryptFile(algorithm,secretKey,iv,outputFile,endFile);
            long end1 = System.nanoTime();
            avg+=end1-start1;
            System.out.println(end1-start1);
        }


        System.out.println("Elapsed Time in nano seconds: "+ avg/10);

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
