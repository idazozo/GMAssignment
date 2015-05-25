/**
 * Created by admin on 21/5/15.
 */
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        /*int[] x = {0, 1, 2, 3};
        int[] y = new int[4];
        int i = 0;
        for (int element : x) {
            y[i] = element * element;
            i++;
            //System.out.println(y[i]);
        }
        System.out.println(Arrays.toString(y));*/

        System.out.println(Arrays.toString(division(13, 3)));
    }

    /*
 * Returns an array with the quotient and remainder of the
 * integer division
 *
 * @param dividend a positive int
 * @param divisor a positive int
 */
    static int[] division(int dividend, int divisor){

        int[] result = {0, dividend};

        if ( dividend < divisor ){
            return result;
        }else{

            // complete the code
            int tmp = dividend - divisor;
            result = division(tmp, divisor);
            result[0]++;
            return result;
        }
    }
}



