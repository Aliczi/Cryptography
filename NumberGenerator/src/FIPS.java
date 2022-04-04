import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
public class FIPS {

    private int[][] acceptance_regions = {
            {2315,2685},
            {1114,1386},
            {527, 723},
            {240, 384},
            {103, 209},
            {103, 209},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {2315,2685},
            {1114,1386},
            {527, 723},
            {240, 384},
            {103, 209},
            {103, 209}
    };

    public void monobitTest(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter("");

        int ones_num = 0;
        while(scanner.hasNext()){
            if (scanner.next().equals("1")) ones_num++;
        }

        System.out.println("The Monobit Test. The number of ones <9725, 10275>: "+ones_num);
        scanner.close();
    }

    public void longRunTest(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter("");
        String current, prev = "";

        int max_run = 1;
        int run_len = 1;
        while(scanner.hasNext()){
            current = scanner.next();
            if (current.equals(prev)) {run_len++; max_run=Math.max(max_run, run_len);}
            else {
                run_len=1;
                prev = current;
            }
        }

        System.out.println("The Long Run Test. The length of the longest run (x<26): "+max_run);
        scanner.close();
    }


    /**
     * Check if the number of runs of each length is within acceptance regions.
     * @param tab array with the numbers of runs
     * @return True if the number of runs of each length is within acceptance regions, else False
     */
    private boolean check_regions(int[] tab){
        for (int i=0; i<16; i++) if( tab[i] < acceptance_regions[i][0] || tab[i] > acceptance_regions[i][1] ) return false;
        return  true;
    }

    public void runsTest(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        scanner.useDelimiter("");
        String current, prev = scanner.next();


        int[] runs = new int[16];

        int run_len = 1;
        while(scanner.hasNext()){

            current = scanner.next();
            if (current.equals(prev) && run_len < 6) run_len++;// runs of length 6 or greater are counted together
            else {
                runs[Integer.parseInt(prev)*10+run_len-1]++;
                run_len = 1;
                prev = current;
            }

        }
        runs[Integer.parseInt(prev)*10+run_len-1]++;

        System.out.println("The Runs Test. The number of runs of each length is within acceptance regions: "+check_regions(runs));
        scanner.close();
    }

    public void pokerTest(File inputFile) throws IOException {

        FileInputStream inputStream = new FileInputStream(inputFile);

        int[] occurences_num = new int[16];
        byte[] buffer = new byte[4];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            if(bytesRead==4) occurences_num[Integer.parseInt(new String(buffer),2)]++;
        }

        int sum=0; for(int i=0; i<16; i++) sum+=occurences_num[i]*occurences_num[i];
        double x = (16.0/5000.0) * sum -5000;
        System.out.println("The Poker Test. Statistic x <2.16, 46.17>: "+df.format(x));

        inputStream.close();
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");


}
