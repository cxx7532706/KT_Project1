import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by charles on 31/3/17.
 */
public class GED {

    private int Minimum(int a, int b, int c) {
        int mi;

        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;
    }

    private double Min2(double a, double b, double c) {
        double mi;

        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;
    }

    public int GEDCalculation1(String s, String t) {

        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost
        int replaceCost = 1;
        int deleteCost = 1;
        int insertCost = 1;


        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];


        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }


        for (i = 1; i <= n; i++) {

            s_i = s.charAt(i - 1);


            for (j = 1; j <= m; j++) {

                t_j = t.charAt(j - 1);

                if ((s_i == t_j) || (t_j - s_i == 32)) {
                    cost = 0;
                } else {
                    cost = replaceCost;
                }
                d[i][j] = Minimum(d[i - 1][j] + insertCost, d[i][j - 1] + deleteCost, d[i - 1][j - 1] + cost);
            }
        }
        return d[n][m];

    }

    public double GEDCalculation2(String s, String t, double[][] table, double total) {

        double d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        double cost; // cost
        double replaceCost = 1;
        double deleteCost = 1;
        double insertCost = 1;


        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new double[n + 1][m + 1];


        for (i = 0; i <= n; i++) {
            d[i][0] = i * insertCost;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j * deleteCost;
        }

        for (i = 1; i <= n; i++) {

            s_i = s.charAt(i - 1);


            for (j = 1; j <= m; j++) {

                t_j = t.charAt(j - 1);

                if ((s_i == t_j) || (t_j - s_i == 32)) {
                    cost = 0;
                }
                else{
                    cost = (1 - table[s_i][t_j]/total) * replaceCost;
                }
                d[i][j] = Min2(d[i - 1][j] + insertCost, d[i][j - 1] + deleteCost, d[i - 1][j - 1] + cost);
            }
        }
        return d[n][m];

    }

}

