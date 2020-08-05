package drivers;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cz
 * 订单GPS数据特征提取
 */
public class Order implements Serializable {
    static double speed_limit = 22;
    static double acc_limit = 3;

    public String order;
    public String driver;
    public int new_exceed;//是否超速

    public List<Double> jerk_acc;//急加速列表
    public List<Double> jerk_dec;//急减速列表
    public List<Speed> speeds;//速度列表
    //public int old_exceed;//超速次数
    public double driver_mile;//驾驶里程
    public double std_speed;//速度标准差
    public double acc_rate;//加速率
    public double dec_rate;//减速率

    public double jerk_acc_max;//最大加速度
    public double jerk_dec_max;//最大减速度
    public double aver_speed;//平均速度
    public int d_night;//是否夜晚

    public String features(){
        String line = driver+","+order+","
                +String.valueOf(driver_mile)+","//1
                +String.valueOf(jerk_acc.size())+","//2
                +String.valueOf(jerk_dec.size())+","//3
                +String.valueOf(jerk_acc_max)+","//4
                +String.valueOf(jerk_dec_max)+","//5
                +String.valueOf(acc_rate)+","//6
                +String.valueOf(dec_rate)+","//7
                +String.valueOf(std_speed)+","//8
                +String.valueOf(aver_speed)+","//9
                //+String.valueOf(old_exceed)+","//10
                +String.valueOf(d_night)+","//11
                +String.valueOf(new_exceed);//12
        return line;
    }
    public void write(String out){
        String line = features();
        try {
            BufferedWriter csv = new BufferedWriter(new FileWriter(out+".csv",true));
            csv.write(line);
            csv.newLine();
            csv.close();
            System.out.println("写入特征");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Order(Track track) {
        this.order = track.orders;
        this.driver = track.driver_id;
        //this.old_exceed = 0;
        this.new_exceed = 0;
        this.speeds = new ArrayList<>();
        this.jerk_acc = new ArrayList<>();
        this.jerk_dec = new ArrayList<>();
        this.driver_mile = 0;
        this.aver_speed = 0;
        this.acc_rate = 0;
        this.dec_rate = 0;
        this.std_speed = 0;
        this.d_night = 0;

        int length = track.locations.size()-1;
        for (int i = 0 ; i < length; i++){
            Location start = track.locations.get(i);
            Location end = track.locations.get(i+1);
            if(start.lon==end.lon&&start.lat==end.lat){
                continue;
            }
            double distance = Distance.calculateLineDistance(start,end);
            int time = end.date - start.date;
            double date = ((double)end.date + (double)start.date)/2;
            double speed = distance/time;
            speeds.add(new Speed(distance,speed,date));
        }



        length = speeds.size()-1;
        //int pause = length*2/3;
        double distances = 0;
        int times = 0;
        List<Double> std= new ArrayList<>();
        for(int i = 0;i < length;i++){
            Speed start = speeds.get(i);
            if(isExceed(start.speed)){
                new_exceed++;//10
                //超速次数
            }
            distances += start.distance;
            std.add(start.speed);
            Speed end = speeds.get(i+1);
            double time = end.date - start.date;
            times+=time;
            double acc = getAcc(start.speed,end.speed,time);
            if(isJerk(acc)) {
                if (acc > 0) {
                    jerk_acc.add(acc);
                } else {
                    jerk_dec.add(acc);
                }
            }
        }
        driver_mile = distances;//1
        aver_speed = distances/times;//9
        Double[] douArray = std.toArray(new Double[std.size()]);
        std_speed = MathUtil.standardDiviation(douArray);//8



        if(jerk_dec.size()==0){
            jerk_dec_max=0;
        }//3 5
        else{
            Object[] DEC = jerk_dec.toArray();
            Arrays.sort(DEC);
            this.jerk_dec_max = (double) DEC[DEC.length-1];
        }
        if(jerk_acc.size()==0){
            jerk_acc_max = 0;
        }//2 4
        else {
            Object[] ACC = jerk_acc.toArray();
            Arrays.sort(ACC);
            this.jerk_acc_max = (double) ACC[0];
        }

        //夜晚
        if(isNight((int) track.locations.get(0).date)){
            this.d_night = 1;//11
        }
        acc_rate = jerk_acc.size()/driver_mile*1000;//6
        dec_rate = jerk_dec.size()/driver_mile*1000;//7

    }

    public boolean isNight(int time){
        String timestampString = String.valueOf(time);
        String formats = "HH:mm:ss yyyy-MM-dd";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        System.out.println(date);
        int dtime = Integer.parseInt(date.split(":")[0]);
        if((dtime>=20 && dtime<=24)||dtime<=6) {
            return true;
        }
        else {
            return false;
        }
    }
/*    public double getDistance(Location oldLocation, Location newLocation){
        double times = newLocation.date - oldLocation.date;
        GlobalCoordinates oldPosition = new GlobalCoordinates(oldLocation.lat,oldLocation.lon);
        GlobalCoordinates newPosition = new GlobalCoordinates(newLocation.lat,newLocation.lon);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, oldPosition, newPosition);
        double distance = geoCurve.getEllipsoidalDistance();
        return distance;
    }*/
    public double getAcc(double oldSpeed,double newSpeed,double time){
        if(oldSpeed==newSpeed){
            return 0;
        }
        else {
            return (newSpeed-oldSpeed)/time;
        }
    }
    public boolean isExceed(double speed){
        if (speed > speed_limit){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isJerk(double acc){
        if(acc>acc_limit||acc<-acc_limit){
            return true;
        }
        else {
            return false;
        }
    }
/*    public static void main(String[] args) {
        Location l1 = new Location("170","106.486654","29.490295");
        Location l2 = new Location("174","106.581515","29.615467");
        System.out.println(getSpeed(l1,l2));
    }*/
}
