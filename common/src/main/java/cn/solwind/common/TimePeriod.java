package cn.solwind.common;

import java.time.LocalDate;

public class TimePeriod {
    private LocalDate beginDate;
    private LocalDate endDate;

    public TimePeriod(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "beginDate=" + beginDate +
                ", endDate=" + endDate +
                '}';
    }
}
