/**
 * Created by charles on 31/3/17.
 */

import java.io.*;


public class Main {

    public static void GEDrun(String output) {
        try {

            FileReader fr1 = new FileReader("src/train.txt");
            BufferedReader br1 = new BufferedReader(fr1);
            FileReader fr2 = new FileReader("src/names.txt");
            BufferedReader br2 = new BufferedReader(fr2);
            FileWriter fw = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(fw);

            int len = (int) br2.lines().count();
            br2.close();
            fr2.close();

            fr2 = new FileReader("src/names.txt");
            br2 = new BufferedReader(fr2);

            String[] names = new String[len + 1];
            for (int i = 0; i < len; i++) {
                names[i] = br2.readLine();
            }
            br2.close();
            fr2.close();

//            double[][] table = WriteTable("src/Analysis_table.txt");
//
//            double total = 0;
//
//            for(int i =0; i< 127; i++) {
//                for (int j = 0; j < 127; j++) {
//                    total += table[i][j];
//                    System.out.println(i + " " + j);
//                }
//            }

            String s;
            String[] predictedNames = new String[25910];
            int numberOfPredictedNames = 0;
            double minDistance = 100000;
            GED ged = new GED();

            while ((s = br1.readLine()) != null) {
                String[] persianLatin = s.split("\\t");
                StringBuilder sb = new StringBuilder();
                sb.append(persianLatin[0]);
                sb.append(" ");
                for (int i = 0; i < len; i++) {
                    double dis = ged.GEDCalculation1(persianLatin[0],names[i]);
                    //double dis = ged.GEDCalculation2(persianLatin[0], names[i],table,total);  // GED Calculation
                    if (dis < minDistance) {
                        minDistance = dis;
                        numberOfPredictedNames = 0;
                        predictedNames[0] = names[i];
                    } else if (dis == minDistance) {
                        numberOfPredictedNames++;
                        predictedNames[numberOfPredictedNames] = names[i];
                    }
                }
                for (int i = 0; i <= numberOfPredictedNames; i++) {
                    sb.append(predictedNames[i]);
                    sb.append(" ");
                }
                sb.append("\n");
                bw.write(sb.toString());
                minDistance = 100000;
                numberOfPredictedNames = 0;
            }
            bw.close();
            fw.close();
            br1.close();
            fr1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double Evaluation(String output) {

        try {

            FileReader fr1 = new FileReader("src/train.txt");
            BufferedReader br1 = new BufferedReader(fr1);
            FileReader fr2 = new FileReader(output);
            BufferedReader br2 = new BufferedReader(fr2);

            FileWriter fw = new FileWriter("src/evaluation1.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            String[][] source = new String[25910][];
            String ss;
            double correctNumber = 0, totalNumber = 0;
            int len = 0;
            while ((ss = br1.readLine()) != null) {
                String[] lineData = ss.split("\\t");
                source[len] = new String[lineData.length];
                source[len] = lineData;
                len++;
            }
            br1.close();
            fr1.close();

//            GED ged = new GED();

            while ((ss = br2.readLine()) != null) {
                String[] lineData = ss.split(" ");
                for (int i = 0; i < len; i++) {
                    if (source[i][0].equals(lineData[0])) {
                        for (int j = 1; j < lineData.length; j++) {
                            if (source[i][1].equals(lineData[j])) {
                                correctNumber = correctNumber + 1;
                                StringBuilder sb = new StringBuilder();
                                sb.append(lineData[0]);
                                sb.append(" ");
                                sb.append(lineData[j]);
//                                int cost = ged.GEDCalculation1(lineData[0],lineData[j]);
//                                sb.append(" ");
//                                sb.append(cost);
                                sb.append("\n");
                                fw.write(sb.toString());
                            }
                            totalNumber = totalNumber + lineData.length - 1;
                        }
                    }
                }

            }
            fw.close();
            bw.close();
            System.out.println(correctNumber);
            System.out.println(totalNumber);
            return correctNumber / totalNumber;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void Analyse(String file, String outputFile) {

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(outputFile);
            BufferedWriter bw = new BufferedWriter(fw);

            int[][] table = new int[128][128];
            for (int i = 0; i < 127; i++)
                for (int j = 0; j < 127; j++) {
                    table[i][j] = 0;
                }
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(" ");
                if (data[0].length() == data[1].length()) {
                    for (int i = 0; i < data[0].length(); i++) {
                        if ((int) data[1].charAt(i) - (int) data[0].charAt(i) != 32) {
                            table[(int) data[0].charAt(i)][(int) data[1].charAt(i)]++;
                        }
                    }
                }
            }
            br.close();
            fr.close();
            for (int i = 0; i < 127; i++)
                for (int j = 0; j < 127; j++)
                    if (table[i][j] != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append((char)i);
                        sb.append(" ");
                        sb.append((char)j);
                        sb.append(":");
                        sb.append(table[i][j]);
                        sb.append("\n");
                        fw.write(sb.toString());

                    }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double[][] WriteTable(String file){

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            double[][] table = new double[128][128];
            for (int i = 0; i < 127; i++)
                for (int j = 0; j < 127; j++)
                    table[i][j] = 0;
            while ((line = br.readLine()) != null) {
                String[] iterms = line.split(":");
                double number = Double.parseDouble(iterms[1]);
                String[] chars = iterms[0].split(" ");
                table[chars[0].charAt(0)][chars[1].charAt(0)] = number;
            }
            br.close();
            fr.close();
            return table;

        }catch(IOException e){
            e.printStackTrace();
            double[][] table = {{0}};
            return table;
        }

    }


    public static void main(String[] args) {

     //    GEDrun("src/output.txt");
     //   double result = Evaluation("src/output4.txt");
     //   System.out.println(result);

        //double result = Evaluation("src/output1.txt");
        //System.out.println(result);

    //    Analyse("src/evaluation2.txt","src/Analysis_table.txt");


    }
}
