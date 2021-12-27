package cn.solwind.common;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtil {

    /**
     * 获取两个日期中较早的那个
     *
     * @param date1
     * @param date2
     * @return
     */
    public static LocalDate min(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2) ? date1 : date2;
    }

    /**
     * 获取两个日期中较晚的一个
     * @param date1
     * @param date2
     * @return
     */
    public static LocalDate max(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2) ? date1 : date2;
    }

    /**
     * 获取两个日期间隔天数
     *
     * @param from
     * @param to
     * @return
     */
    public static long lengthOfDays(LocalDate from, LocalDate to) {
        return from.until(to, ChronoUnit.DAYS) + 1;
    }
}
