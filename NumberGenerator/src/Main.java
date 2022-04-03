import java.io.File;
import java.io.IOException;

public class Main {

        public static void main(String[] args) throws IOException {

            long p, q, n,a;

        /*
        Blum's primes
        7 11 19 23 31 43 47 59 67 71 79 83 103 107 131 139 151 163 167 179
        191 199 211 223 227 239 251 263 271 283 307 311 331 347 359 367 379 383 419 431
        1707892411793 2290939653701  749 781, 789
        */

            // 1stprime number pcd d:
            p = 227L;
            // 2nd prime number q
            q = 2290939653701L;
            //Blum's number
            n = p*q;
            //a -> NWD(a, n) = 1. Between 2 and n-1
            a=157L;


            long bits_num=20000;
            String file_name="src/seq_"+bits_num+"_"+n+"_"+a+".txt";

            File outputFile = new File(file_name);
            BBS.numberGenerator(n, a,bits_num, outputFile );


            //FIPS test
            FIPS test = new FIPS();
            test.monobitTest(outputFile);


        }
}

