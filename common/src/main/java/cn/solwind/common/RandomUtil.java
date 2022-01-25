package cn.solwind.common;

public class RandomUtil {

    /**
     * 获取范围内的随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    /**
     * 获取范围内的随机double
     *
     * @param min
     * @param max
     * @return
     */
    public static double getRandom(double min, double max) {
        return min + (Math.random() * (max - min));
    }
}
