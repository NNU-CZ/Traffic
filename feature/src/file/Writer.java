package file;

import drivers.Track;

import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 */
public class Writer {
    public static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {

        String stable = "E:\\实验\\风险预测\\data\\gps_201611";
        for(int i = 1;i<=30;i++){

            String filename = reName(stable,i);
            File newpath = new File(reName("E:\\实验\\风险预测\\track\\",i));
            newpath.mkdir();
            Track.path = newpath.getPath();
            FileLoader fl = new FileLoader(filename);
        }
    }
}
