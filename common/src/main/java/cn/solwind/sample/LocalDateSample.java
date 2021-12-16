package cn.solwind.sample;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class LocalDateSample {

    public static void main(String[] args) {

        System.out.println("加法运算");
        System.out.println("当前：" + LocalDate.now());
        System.out.println("加1天：" + LocalDate.now().plusDays(1));
        System.out.println("加1周：" + LocalDate.now().plusWeeks(1));
        System.out.println("加1月：" + LocalDate.now().plusMonths(1));
        System.out.println("加1年：" + LocalDate.now().plusYears(1));

        System.out.println("减法运算");
        System.out.println("当前：" + LocalDate.now());
        System.out.println("减1天：" + LocalDate.now().minusDays(1));
        System.out.println("减1周：" + LocalDate.now().minusWeeks(1));
        System.out.println("减1月：" + LocalDate.now().minusMonths(1));
        System.out.println("减1年：" + LocalDate.now().minusYears(1));

        /**
         * 所有的数值必须合法
         *         如果当月当天是闰年2月29日，替换年份为非闰年，则会变成2月28日
         */
        System.out.println("替换");
        System.out.println("当前：" + LocalDate.now());
        System.out.println("替换日期为1：" + LocalDate.now().withDayOfMonth(1));
        System.out.println("替换天数为1：" + LocalDate.now().withDayOfYear(1));
        System.out.println("替换月份为1：" + LocalDate.now().withMonth(1));
        System.out.println("替换年份为1：" + LocalDate.now().withYear(1));

        System.out.println("判断");
        System.out.println("当天：" + LocalDate.now());
        System.out.println("是否在当天之前：" + LocalDate.now().minusDays(1).isBefore(LocalDate.now()));
        System.out.println("是否在当天之后：" + LocalDate.now().plusDays(1).isAfter(LocalDate.now()));
        System.out.println("是否在当天：" + LocalDate.now().isEqual(LocalDate.now()));
        System.out.println("今年是否是闰年：" + LocalDate.now().isLeapYear());


        System.out.println("本月第一天：" + LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1));
        System.out.println("本月最后一天：" + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

    }
}
