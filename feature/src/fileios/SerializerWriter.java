package fileios;

import drivers.Driver;

import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 */
public class SerializerWriter {
    public static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {

        String stable = "F:\\实验\\风险预测\\gps_201611";
        for(int i = 2;i<=2;i++){
            String filename = reName(stable,i);
            File newpath = new File(reName("F:\\实验\\track\\",i));
            newpath.mkdir();
            Driver.path = newpath.getPath();
            Serializer fl = new Serializer(filename);
        }
    }
}
