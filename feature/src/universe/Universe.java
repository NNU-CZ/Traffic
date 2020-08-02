package universe;

import file.Writer;

import java.io.*;

/**
 * @author cz
 */
public class Universe {
    public static double max = 0;

    public static void read(String filename) throws IOException {
        System.out.println(filename);
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        while((line = br.readLine())!=null){
            String[] lines = line.split(",");
            Double temp = Double.parseDouble(lines[9]);
            if(Double.isNaN(temp)||temp>=100){
                continue;
            }
            else if(temp>max){
                max = temp;
            }
        }
        System.out.println(filename+"完毕");
    }
    public static void write(String filename,String out) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename,true));
        bw.write(out);
        bw.newLine();
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        for(int i = 10;i<=30;i++){
            String in = Writer.reName("E:\\实验\\风险预测\\特征\\test",i) +".csv";
            read(in);
        }
        System.out.println(max);
    }
}
