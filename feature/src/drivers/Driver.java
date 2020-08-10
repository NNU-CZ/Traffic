package drivers;

import java.io.*;
import java.util.LinkedList;


/**
 * @author cz
 * 整合原始数据中的gps数据
 */
public class Driver implements Serializable {
    public String driver_id;
    public LinkedList<Track> orders;

    public static String path;

    //写入csv中
    public void write(String out){
        LinkedList<Feature> f = new LinkedList<>();
        for (Track t: orders){
            Order of = new Order(t);
            f.add(of.feature);
        }
        int order_num = orders.size();
        double driver_mile=0;//驾驶里程
        double jerk_acc=0;//急加速次数
        double jerk_dec=0;//急减速次数
        double jerk_acc_max=0;//最大加速度
        double jerk_dec_max=0;//最大减速度
        double acc_rate=0;//加速率
        double dec_rate=0;//减速率
        double std_speed=0;//速度标准差
        double aver_speed=0;//平均速度
        double exceed=0; //超速次数
        int n = 0;
        int p = f.size()-1;
        for (int i = 0 ;i<p;i++ ){
            Feature ff = f.get(i);
            driver_mile += ff.driver_mile;
            jerk_acc += ff.jerk_acc;
            jerk_dec += ff.jerk_dec;
            jerk_acc_max = MathUtil.max(jerk_acc_max,ff.jerk_acc_max);
            jerk_dec_max = MathUtil.min(jerk_dec_max,ff.jerk_dec_max);

            acc_rate += ff.acc_rate;
            dec_rate += ff.dec_rate;
            std_speed += ff.std_speed;
            aver_speed += ff.aver_speed;

            n+=1;
        }
        acc_rate /= n;
        dec_rate /= n;
        std_speed /= n;
        aver_speed /= n;
        exceed = f.get(p).exceed;

        String line = driver_id+","+
                 String.valueOf(driver_mile)+","//1
                +String.valueOf(jerk_acc)+","//2
                +String.valueOf(jerk_dec)+","//3
                +String.valueOf(jerk_acc_max)+","//4
                +String.valueOf(jerk_dec_max)+","//5
                +String.valueOf(acc_rate)+","//6
                +String.valueOf(dec_rate)+","//7
                +String.valueOf(std_speed)+","//8
                +String.valueOf(aver_speed)+","//9
                +String.valueOf(exceed)+","//12
                +String.valueOf(order_num);//12

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


    //序列化
    public void writer(){
        try {
            String filename = path+"\\"+this.driver_id+".ser";
            File filter = new File(filename);
            if(filter.exists()){
                filename = filename+".ser";
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
            System.out.println("Serialized data is saved");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            out.writeObject(this.driver_id);
//            out.writeObject(this.orders);
//            for(Location location:this.locations){
//                out.writeObject(location);
//            }
//            out.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public Driver(String driver_id) {
        this.driver_id = driver_id;
        //track = new LinkedList<>();
        orders = new LinkedList<>();
        //orders.add(track);
    }

    public void addOrder(Track t){
        orders.add(t);
    }

}
