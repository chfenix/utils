package cn.solwind.common;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 按照自然月周期拆分时间阶段
 * 例如:2021-01-15 ~ 2021-05-14 拆分为
 * 2021-01-15 ~ 2021-01-31
 * 2021-02-01 ~ 2021-02-28
 * 2021-03-01 ~ 2021-03-31
 * 2021-04-01 ~ 2021-04-30
 * 2021-05-01 ~ 2021-05-14
 */
public class TimeNaturalSplit extends TimeCircleSplit {

    public static void main(String[] args) {
        TimeNaturalSplit splitUtil = new TimeNaturalSplit();
        LocalDate beginDate = LocalDate.of(2021,1,15);
        LocalDate endDate = LocalDate.of(2021,5,14);
        System.out.println(splitUtil.splitTime(beginDate, endDate, 1, ChronoUnit.MONTHS));
    }

    @Override
    public LocalDate getEndDate(LocalDate cycleBeginDate, LocalDate endDate) {
        return super.getEndDate(cycleBeginDate.withDayOfMonth(1), endDate);
    }

    @Override
    public LocalDate getCycleBeginDate(LocalDate firstClauseItemBeginDate, Integer step, ChronoUnit unit, LocalDate targetDate) {
        long diffEnd = transformWithUnit(firstClauseItemBeginDate, unit).until(transformWithUnit(targetDate, unit), unit) / step;
        return firstClauseItemBeginDate.plus(diffEnd * step + step, unit);
    }
}
