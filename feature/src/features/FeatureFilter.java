package features;

import fileios.SerializerWriter;

import java.io.*;

/**
 * @author cz
 */
public class FeatureFilter {
    public static void read(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\实验\\feature\\driver.csv",true));
        String line = "";
        while((line = br.readLine())!=null){
            String[] lines = line.split(",");

            Double temp = Double.parseDouble(lines[9]);
            if(Double.isNaN(temp) || temp>=100){
                continue;
            }
/*
            String flag = "1";
            if(Integer.parseInt(lines[10])>0 && Integer.parseInt(lines[10])<=5){
                flag = "2";
            }
            else if(Integer.parseInt(lines[10])>5){
                flag = "3";
            }
            else if(Integer.parseInt(lines[10])<15&&Integer.parseInt(lines[10])>=5){
                flag = "3";
            }
            else if(Integer.parseInt(lines[10])<30&&Integer.parseInt(lines[10])>=15){
                flag = "4";
            }*/
/*            else if(Integer.parseInt(lines[10])<100&&Integer.parseInt(lines[10])>=30){
                flag = "5";
            }*/

/*            String new_line =
                              lines[1]+","+
                              lines[2]+","+
                              lines[3]+","+
                              lines[4]+","+
                              lines[5]+","+
                              lines[6]+","+
                              lines[7]+","+
                              lines[8]+","+
                              lines[9]+","+
                              flag;*/
            //String new_line = lines[0]+","+ flag+","+lines[11];
            bw.write(line);
            bw.newLine();
        }
        String new_line;
        br.close();
        bw.close();
    }

    public static void main(String[] args) {
        for(int i = 2;i<=2;i++){
            try {
                read(SerializerWriter.reName("F:\\实验\\feature\\",i)+".csv");
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
