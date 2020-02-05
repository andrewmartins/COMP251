import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
        // result contains two values: the result and the number of multiplcations to get to that result
        int result[] = new int[2];
        int[] e, f, g, h = new int[2];
        //System.out.print("x: " + x + " y : " + y);
        if(size == 1){
            result[0] = (x%2)*(y%2);
            result[1] = 1;
        }
        else{
            int m = (int)Math.ceil(size/2.0);
            int a = x >> m;
            int b = x%(1<<m);
            int c = y>>m;
            int d = y%(1<<m);

            e = naive(m, a, c);
            f = naive(m, b, d);
            g = naive(m, b, c);
            h = naive(m, a, d);

            result[0] = ((e[0]<<(2*m)) + ((g[0]+h[0])<<m) + f[0]);
            result[1] = (e[1] + f[1] + g[1] + h[1]) + 3*m;
        }
        return result;
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
        int[] result = new int[2];
        int[] e, f, g = new int[2];
        if(size == 1){
            result[0] = (x%2)*(y%2);
            result[1] = 1;
        }
        else{
            int m = (int)Math.ceil(size/2.0);
            int a = x >> m;
            int b = x%(1<<m);
            int c = y >> m;
            int d = y%(1<<m);

            e = karatsuba(m, a, c);
            f = karatsuba(m, b, d);
            g = karatsuba(m, Math.abs(a-b), Math.abs(c-d));

            int sign = 1;
            if(a<b){
                sign *= -1;
            }
            if(c<d){
                sign *= -1;
            }
            g[0] *= sign;

            result[0] = (e[0]<<(2*m))  + (((e[0] + f[0] - g[0])<<m) + f[0]);
            result[1] = (e[1] + f[1] + g[1]) + (6*m);
        }
        return result;
        
    }
    
    public static void main(String[] args){
        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
