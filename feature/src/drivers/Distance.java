package drivers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * @author cz
 * @Date: 2020/7/23 13:53
 * @Description:
 */
public class Distance{
    private static DecimalFormat d = new DecimalFormat("0.00000");
    /**
     * 计算国内坐标系两点间距离 （高德地图）
     *
     * @return double 距离 单位公里,精确到米
     */
    public static double calculateLineDistance(Location old_location,Location new_location) {
        if (old_location != null && new_location != null) {
            double var2 = 0.01745329251994329D;
            double var4 = old_location.lon;
            double var6 = old_location.lat;
            double var8 = new_location.lon;
            double var10 = new_location.lat;
            var4 *= 0.01745329251994329D;
            var6 *= 0.01745329251994329D;
            var8 *= 0.01745329251994329D;
            var10 *= 0.01745329251994329D;
            double var12 = Math.sin(var4);
            double var14 = Math.sin(var6);
            double var16 = Math.cos(var4);
            double var18 = Math.cos(var6);
            double var20 = Math.sin(var8);
            double var22 = Math.sin(var10);
            double var24 = Math.cos(var8);
            double var26 = Math.cos(var10);
            double[] var28 = new double[3];
            double[] var29 = new double[3];
            var28[0] = var18 * var16;
            var28[1] = var18 * var12;
            var28[2] = var14;
            var29[0] = var26 * var24;
            var29[1] = var26 * var20;
            var29[2] = var22;
            double var30 = Math.sqrt((var28[0] - var29[0]) * (var28[0] - var29[0]) + (var28[1] - var29[1]) * (var28[1] - var29[1]) + (var28[2] - var29[2]) * (var28[2] - var29[2]));
            return (Math.asin(var30 / 2.0D) * 1.27420015798544E7D);
        } else {
            try {
                throw new IOException("非法坐标值");
            } catch (IOException var32) {
                var32.printStackTrace();
                return 0.0F;
            }
        }
    }
}
