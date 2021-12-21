package fileios;

import drivers.Driver;

import java.io.*;

/**
 * @author cz
 */
public class DeSerializer
{
    public static void deSerialize(String filename)
    {
        Driver driver;
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
        System.out.println("Deserialized Driver...");
       // System.out.println(driver.driver_id);
        //System.out.println(driver.orders);
//        for(Track order: driver.orders){
//            for (Location location : order.track){
//                System.out.println(location.date);
//                System.out.println(location.lon);
//                System.out.println(location.lat);
//            }
        }

    public static void main(String[] args) {
       deSerialize("F:\\实验\\track\\01\\0b4bce21bdd1bb9e73f8aa456e0b3957.ser");
    }
}
