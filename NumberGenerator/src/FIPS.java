import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FIPS {

    public void monobitTest(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter("");

        int ones_num = 0;
        while(scanner.hasNext()){
            if (scanner.next().equals("1")) ones_num++;
        }

        System.out.println("Number of ones <9725, 10275>: "+ones_num);
        scanner.close();
    }


}
