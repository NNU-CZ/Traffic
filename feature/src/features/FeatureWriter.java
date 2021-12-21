package features;

import drivers.Driver;

import java.io.*;

/**
 * @author cz
 */

public class FeatureWriter {
    public static Driver deSerialize(String filename) {
        Driver driver = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            driver = (Driver)in.readObject();
            in.close();
            fileIn.close();

        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("Driver class not found");
            c.printStackTrace();
        }
        finally{
            System.out.println("Deserialize Driver...");
            return driver;
        }
    }
    public static boolean load_files(String filepath,String out) throws FileNotFoundException, IOException {
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
                        Driver driver = deSerialize(filename);
                        //Order order = new Order(driver);
//                      System.out.println("读取");
                        driver.write(out);
                    } else if (readfile.isDirectory()) {
                        load_files(filepath + "\\" + filelist[i],out);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("load_files()   Exception:" + e.getMessage());
        }
        return true;
    }
    public static void write_csv(String line,String out) throws Exception {
        //第一步：设置输出的文件路径
        //如果该目录下不存在该文件，则文件会被创建到指定目录下。如果该目录有同名文件，那么该文件将被覆盖。
        File writeFile = new File(out);

        //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
        BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile,true));
        writeText.write(line);
        writeText.newLine();
        writeText.close();
    }


    private static String reName(String stable, int var){
        return stable + String.format("%02d",var);
    }
    public static void main(String[] args) throws IOException {
        for(int i = 2;i<=2;i++){
            load_files(reName("F:\\实验\\track\\",i),reName("F:\\实验\\feature\\",i));
            //反序列化路径,特征写入路径
        }
    }
}
