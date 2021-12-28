package cn.solwind.common;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * 按照时间周期拆分时间阶段
 * 例如:2021-01-15 ~ 2021-05-14 拆分为
 * 2021-01-15 ~ 2021-02-14
 * 2021-02-15 ~ 2021-03-14
 * 2021-03-15 ~ 2021-04-14
 * 2021-04-15 ~ 2021-05-14
 */
public class TimeCircleSplit {

    public static void main(String[] args) {
        TimeCircleSplit splitUtil = new TimeCircleSplit();
        LocalDate beginDate = LocalDate.of(2021,1,15);
        LocalDate endDate = LocalDate.of(2021,5,14);
        System.out.println(splitUtil.splitTime(beginDate, endDate, 1, ChronoUnit.MONTHS));
    }

    /**
     * 按拆分时间阶段
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param step
     * @param unit
     * @return
     */
    public List<TimePeriod> splitTime(LocalDate beginDate, LocalDate endDate, Integer step, ChronoUnit unit) {
        List<TimePeriod> result = new ArrayList<>();
        while (!beginDate.isAfter(endDate)) {
//            LocalDate prev = getCycleBeginDatePrevious(firstClauseItemBeginDate, step, unit, beginDate);
            LocalDate cycleBeginDate = getCycleBeginDate(beginDate, step, unit, beginDate);
            TimePeriod costOrderTime = new TimePeriod(beginDate, getEndDate(cycleBeginDate, endDate));
//            costOrderTime.setVirtualBeginDate(prev);
//            costOrderTime.setVirtualEndDate(cycleBeginDate.minusDays(1));
            result.add(costOrderTime);
            beginDate = costOrderTime.getEndDate().plusDays(1);
        }
        return result;
    }

    public LocalDate getEndDate(LocalDate cycleBeginDate, LocalDate endDate) {
        return min(cycleBeginDate.minusDays(1), endDate);
    }

    public LocalDate getCycleBeginDate(LocalDate firstClauseItemBeginDate, Integer step, ChronoUnit unit, LocalDate targetDate) {
        long diffEnd = transformWithUnit(firstClauseItemBeginDate, unit).until(transformWithUnit(targetDate, unit), unit) / step;
        LocalDate cycleBeginDate = firstClauseItemBeginDate.plus(diffEnd * step, unit);
        if (!cycleBeginDate.isAfter(targetDate)) {
            cycleBeginDate = firstClauseItemBeginDate.plus(diffEnd * step + step, unit);
        }
        return cycleBeginDate;
    }

    private LocalDate getCycleBeginDatePrevious(LocalDate firstClauseItemBeginDate, Integer step, ChronoUnit unit, LocalDate targetDate) {
        long diffEnd = transformWithUnit(firstClauseItemBeginDate, unit).until(transformWithUnit(targetDate, unit), unit) / step;
        LocalDate cycleBeginDate = firstClauseItemBeginDate.plus(diffEnd * step, unit);
        if (cycleBeginDate.isAfter(targetDate)) {
            cycleBeginDate = firstClauseItemBeginDate.plus(diffEnd * step - step, unit);
        }
        return cycleBeginDate;
    }

    public static Temporal transformWithUnit(LocalDate date, ChronoUnit unit) {
        if (ChronoUnit.DAYS == unit) {
            return date;
        } else if (ChronoUnit.MONTHS == unit) {
            return YearMonth.from(date);
        } else if (ChronoUnit.YEARS == unit) {
            return Year.from(date);
        }
        return date;
    }

    public static LocalDate min(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2) ? date1 : date2;
    }

    public static LocalDate max(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2) ? date1 : date2;
    }
}
