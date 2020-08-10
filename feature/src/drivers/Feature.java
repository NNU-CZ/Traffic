package drivers;

/**
 * @author cz
 */
public class Feature {
    public String  order_id;
    public double driver_mile;//驾驶里程
    public double jerk_acc;//急加速次数
    public double jerk_dec;//急减速次数
    public double jerk_acc_max;//最大加速度
    public double jerk_dec_max;//最大减速度
    public double acc_rate;//加速率
    public double dec_rate;//减速率
    public double std_speed;//速度标准差
    public double aver_speed;//平均速度
    public double exceed; //超速次数

    /*    public int d_night;
* */
    public Feature(String order_id) {
       this.order_id = order_id;
    }
}
