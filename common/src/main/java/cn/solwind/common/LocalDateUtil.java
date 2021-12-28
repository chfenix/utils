package cn.solwind.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateUtil {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 解析LocalDateTime字符串，格式为yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        return parseDateTime(dateTime, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 解析LocalDateTime字符串
     * @param dateTime
     * @param format
     * @return
     */
    public static LocalDateTime parseDateTime(String dateTime, String format) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析LocalDate字符串，格式为yyyy-MM-dd
     * @param date
     * @return
     */
    public static LocalDate parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 解析LocalDate字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static LocalDate parseDate(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化LocalDate，格式为yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(LocalDate date) {
        return formatDate(date,DEFAULT_DATE_FORMAT);
    }

    /**
     * 格式化LocalDate
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化LocalDateTime，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String formatTime(LocalDateTime dateTime){
        return formatTime(dateTime,DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 格式化LocalDateTime
     *
     * @param dateTime
     * @param format
     * @return
     */
    public static String formatTime(LocalDateTime dateTime,String format){
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

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
