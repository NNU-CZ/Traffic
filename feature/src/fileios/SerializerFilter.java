package fileios;

import drivers.Driver;

import java.io.*;

import static features.FeatureWriter.deSerialize;

public class SerializerFilter {
    public static void del_files(String filepath) throws FileNotFoundException, IOException {
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());
            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 1; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        String filename = readfile.getPath();
                        System.out.println(readfile.getPath());
                        Driver driver = deSerialize(filename);
                        //System.out.println(driver.orders.size());
                        if (driver.orders.size() < 2) {
                            readfile.delete();
                            filelist = file.list();
                            //Order order = new Order(driver);
//                      System.out.println("读取");
                            //order.write(out);
                            //}
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       // Driver driver = deSerialize("F:\\实验\\track\\01\\0b4bce21bdd1bb9e73f8aa456e0b3957.ser");
        try {
            del_files("F:\\实验\\track\\02");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
