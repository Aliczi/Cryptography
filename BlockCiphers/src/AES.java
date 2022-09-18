import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AES {

    public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
                                   File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);

        if(algorithm.contains("/ECB/")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }

    public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
                                   File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);

        if(algorithm.contains("/ECB/")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }


    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    public static void simpleAES(){
                // Create String variables
        String originalString = "AlaMaKota";

        // Call encryption method
        String encryptedString
                = AES.encrypt(originalString);

        // Call decryption method
        String decryptedString
                = AES.decrypt(encryptedString);

        // Print all strings
        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }

    public static String encrypt(String strToEncrypt)
    {
        try {

            // Create default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,
                    ivspec);
            // Return encrypted string
            return Base64.getEncoder().encodeToString(
                    cipher.doFinal(strToEncrypt.getBytes(
                            StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: "
                    + e.toString());
        }
        return null;
    }

    private static final String SECRET_KEY
            = "my_super_secret_key_ho_ho_ho";

    private static final String SALT = "ssshhhhhhhhhhh!!!!";

    // This method use to decrypt to string
    public static String decrypt(String strToDecrypt) {
        try {

            // Default byte array
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0 };
            // Create IvParameterSpec object and assign with
            // constructor
            IvParameterSpec ivspec
                    = new IvParameterSpec(iv);

            // Create SecretKeyFactory Object
            SecretKeyFactory factory
                    = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");

            // Create KeySpec object and assign with
            // constructor
            KeySpec spec = new PBEKeySpec(
                    SECRET_KEY.toCharArray(), SALT.getBytes(),
                    65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(
                    tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,
                    ivspec);
            // Return decrypted string
            return new String(cipher.doFinal(
                    Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: "
                    + e.toString());
        }
        return null;
    }

    private static int getHowManyBlocks(File file) {
        return (int) (file.length()/16);
    }

    public static void encryptCBC(SecretKey key, byte[] iv, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,  InvalidKeyException {
//        System.out.print("Iv        "); for(int i=0; i<iv.length;i++) System.out.print(iv[i]+" "); System.out.println();


        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);



        byte[] buffer = new byte[16];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] xor_res;
            if(bytesRead==16) xor_res = xorFunc(iv,buffer);
            else{
                byte[] padded_buffer = pad(buffer, bytesRead);
                xor_res = xorFunc(iv,padded_buffer);
            }

            byte[] output = cipher.update(xor_res, 0, xor_res.length);
            if (output != null) {
                outputStream.write(output);
                iv=output;
            }

        }

        inputStream.close();
        outputStream.close();
    }

    public static void decryptCBC(SecretKey key, byte[] iv, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
//        System.out.print("Iv        "); for(int i=0; i<iv.length;i++) System.out.print(iv[i]+" "); System.out.println();


        int blocks = getHowManyBlocks(inputFile);
        int counter = 1;

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[16];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                byte[] xor_res = xorFunc(iv,output);
                if (counter==blocks) xor_res=trim(xor_res); else counter++;
                outputStream.write(xor_res);
                for(int i=0; i< iv.length ; i++) iv[i]=buffer[i];

               }
            }


        inputStream.close();
        outputStream.close();
    }


    public static void encryptPBC(SecretKey key, byte[] iv, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,  InvalidKeyException {
//        System.out.print("Iv        "); for(int i=0; i<iv.length;i++) System.out.print(iv[i]+" "); System.out.println();


        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);



        byte[] buffer = new byte[16];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] xor_res;
            if(bytesRead==16) xor_res = xorFunc(iv,buffer);
            else{
                byte[] padded_buffer = pad(buffer, bytesRead);
                xor_res = xorFunc(iv,padded_buffer);
            }

            byte[] output = cipher.update(xor_res, 0, xor_res.length);
            if (output != null) {
                outputStream.write(output);
                iv= Arrays.copyOf(buffer, buffer.length);;
//                for(int i=0; i<iv.length; i++ ) iv[i]=buffer[i];
            }

        }

        inputStream.close();
        outputStream.close();
    }

    public static void decryptPBC(SecretKey key, byte[] iv, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
//        System.out.print("Iv        "); for(int i=0; i<iv.length;i++) System.out.print(iv[i]+" "); System.out.println();


        int blocks = getHowManyBlocks(inputFile);
        int counter = 1;

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[16];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                byte[] xor_res = xorFunc(iv,output);
                if (counter==blocks) xor_res=trim(xor_res); else counter++;
                outputStream.write(xor_res);
                iv = Arrays.copyOf(xor_res, xor_res.length);

            }
        }


        inputStream.close();
        outputStream.close();
    }

    public static void encryptECB(SecretKey key, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,  InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[16];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            if(bytesRead!=16) buffer = pad(buffer, bytesRead);
            byte[] output = cipher.update(buffer, 0, 16);
            if (output != null) outputStream.write(output);
        }

        inputStream.close();
        outputStream.close();
    }

    public static void decryptECB(SecretKey key, File inputFile, File outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        int blocks = getHowManyBlocks(inputFile);
        int counter = 1;

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[16];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                if (counter==blocks) output=trim(output); else counter++;
                outputStream.write(output);
            }
        }
        inputStream.close();
        outputStream.close();
    }

    private static byte[] xorFunc(byte[] a, byte[] b){
        int i=0;
        byte[] res = new byte[16];
        for (byte by : b){
            res[i] = (byte) (by ^ a[i++]);}
        return  res;
    }

    private static byte[] pad(byte[] a, int read){
        byte[] res = new byte[16];
        int i;
        for(i=0;i<read;i++) res[i]= a[i];
        for(i=read;i<16;i++) res[i]= (byte) (16-read);

        return res;
    }

    private static byte[] trim(byte[] a) {
        if( a[15]==(byte)15) { byte[] res = new byte[1]; for(int i=0; i<1; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)14) { byte[] res = new byte[2]; for(int i=0; i<2; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)13) { byte[] res = new byte[3]; for(int i=0; i<3; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)12) { byte[] res = new byte[4]; for(int i=0; i<4; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)11) { byte[] res = new byte[5]; for(int i=0; i<5; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)10) { byte[] res = new byte[6]; for(int i=0; i<6; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)9) { byte[] res = new byte[7]; for(int i=0; i<7; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)8) { byte[] res = new byte[8]; for(int i=0; i<8; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)7) { byte[] res = new byte[9]; for(int i=0; i<9; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)6) { byte[] res = new byte[10]; for(int i=0; i<10; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)5) { byte[] res = new byte[11]; for(int i=0; i<11; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)4) { byte[] res = new byte[12]; for(int i=0; i<12; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)3) { byte[] res = new byte[13]; for(int i=0; i<13; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)2) { byte[] res = new byte[14]; for(int i=0; i<14; i++) res[i]=a[i]; return res;}
        if( a[15]==(byte)1) { byte[] res = new byte[15]; for(int i=0; i<15; i++) res[i]=a[i]; return res;}
        return a;
    }



}



