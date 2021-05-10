package cn.solwind.excel.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.solwind.excel.annotation.Cell;
import cn.solwind.excel.annotation.Row;
import cn.solwind.excel.annotation.Sheet;
import cn.solwind.excel.type.Link;

/**
* @author Zhouluning
* @version 创建时间：2019-05-09
*
* 类说明
*/

//@Sheet(name = "测试1", fontFamily = "微软雅黑", fontSize = 10)
@Sheet
public class SampleData {

	@Cell(col = "A", row = 1)
	private int count;
	
	@Cell(col = "A", row = 2, value = "日期",border = {Cell.BORDER_BOLD_LEFT, Cell.BORDER_BOLD_TOP, Cell.BORDER_BOLD_RIGHT,Cell.BORDER_BOLD_BOTTOM })
	private String nowTitle;

	@Cell(col = "B", row = 2,height=20,width=Cell.WIDTH_AUTO)
	private Date now;
	
	@Cell(col = "C", row = 2,height=20,width=Cell.WIDTH_AUTO,writeConverter=DateConverter.class)
	private Date createTime;
	
	@Cell(col = "D", row = 2,format="yyyy年MM月dd日")
	private Date updateTime;
	
	// format 可以使用任何excel支持的内嵌格式，需要与数据类型对应
	@Cell(col = "F", row = 2,format="0.00%")
	private BigDecimal rate;
	
	@Cell(col = "C", row = 1)
	private Link file;
	
	@Row(start=5)
	private List<LineData> rowData;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public List<LineData> getRowData() {
		return rowData;
	}

	public void setRowData(List<LineData> rowData) {
		this.rowData = rowData;
	}

	public String getNowTitle() {
		return nowTitle;
	}

	public void setNowTitle(String nowTitle) {
		this.nowTitle = nowTitle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Link getFile() {
		return file;
	}

	public void setFile(Link file) {
		this.file = file;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
