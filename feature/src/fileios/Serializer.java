package fileios;

import drivers.Driver;
import drivers.Track;

import java.io.*;

/**
 * 序列化司机对象
 * @author cz
 */
public class Serializer {

    private BufferedReader bufLoader(String filename) throws IOException {
        File fl = new File(filename);
        FileInputStream fis = new FileInputStream(fl);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(bis),5*1024*1024);
        return br;
    }

    public Serializer(String filename) throws IOException {
        Driver driver = new Driver("0");
        Track track = new Track("0");
        BufferedReader br = bufLoader(filename);
        String line = "";

        while ((line = br.readLine())!=null){
            String[] sp_line = line.split(",");
            if(!sp_line[0].equals(driver.driver_id)){
                driver.addOrder(track);
                if((driver!=null) && (!driver.driver_id.equals("0") && driver.orders.size()>2)){
                    driver.writer();
                    System.out.println("完成");
                }
                driver = new Driver(sp_line[0]);
                track = new Track(sp_line[1]);
            }
            else if(!sp_line[1].equals(track.order_id)) {
                driver.addOrder(track);
                track = new Track(sp_line[1]);
            }
            track.addLocation(sp_line[2],sp_line[3],sp_line[4]);
        }
    }

}
