import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class BBS {

    public static void numberGenerator(long blum_num, long random_a, long bits_num, File outputFile) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(outputFile);

        long x0,xi;
        x0 = (random_a*random_a)%blum_num;

        if(((int)x0&1) == 1) outputStream.write('1');
        else outputStream.write('0');

        for(int i=1; i<bits_num; i++) {

            xi=(x0*x0)%blum_num;

            if(((int)xi&1) == 1) outputStream.write('1');
            else outputStream.write('0');

            x0=xi;
        }
        outputStream.close();
    }


}

