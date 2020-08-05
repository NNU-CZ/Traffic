package universe;

import file.Writer;

import java.io.*;

/**
 * @author cz
 */
public class Filter {
    public static void read(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\实验\\风险预测\\特征\\test.csv",true));
        String line = "";
        while((line = br.readLine())!=null){
            String[] lines = line.split(",");
            if(Double.isNaN(Double.parseDouble(lines[9]))||Double.parseDouble(lines[9])>15){
                continue;
            }
            String flag = "0";
            if(Integer.parseInt(lines[12])>0){
                flag = "1";
            }
            String bool = "0";
//            if(Boolean.parseBoolean(lines[10])==true){
//                bool = "1";
//            }
            String new_line = lines[2]+","+
                              lines[3]+","+
                              lines[4]+","+
                              lines[5]+","+
                              lines[6]+","+
                    lines[7]+","+ lines[8]+","+
                    lines[9]+","+ lines[10]+","+
                    lines[11] +","+
                    flag;
            bw.write(new_line);
            bw.newLine();
        }
        String new_line;
        br.close();
        bw.close();
    }

    public static void main(String[] args) {
        for(int i = 16;i<=21;i++){
            try {
                read(Writer.reName("E:\\实验\\风险预测\\track\\",i)+".csv");
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
