package cn.solwind.sample;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Java8StreamDTO {

    private String id;

    private BigDecimal amount;

    private LocalDate beginDate;

    private LocalDate endDate;

    private String groupKey;

    private String nullPro;

    public Java8StreamDTO() {
    }

    public Java8StreamDTO(Object[] initData) {
        this.id = (String)initData[0];
        this.amount = (BigDecimal)initData[1];
        this.beginDate = (LocalDate)initData[2];
        this.endDate = (LocalDate)initData[3];
        this.groupKey = (String)initData[4];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getNullPro() {
        return nullPro;
    }

    public void setNullPro(String nullPro) {
        this.nullPro = nullPro;
    }

    @Override
    public String toString() {
        return "Java8StreamDTO{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", groupKey='" + groupKey + '\'' +
                '}';
    }
}
