package drivers;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author cz
 */
public class Track implements Serializable {
    public String order_id;
    public LinkedList<Location> track;
    public Track(String order){
        this.order_id = order;
        track = new LinkedList<>();
    }
    public void addLocation(String date,String lon,String lat){
        Location location = new Location(date,lon,lat);
        track.add(location);
    }
}
