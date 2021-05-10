package cn.solwind.excel.pojo;

import java.util.List;

import cn.solwind.excel.annotation.Row;
import cn.solwind.excel.annotation.Sheet;

/**
 * @author chfenix
 * @version 创建时间：2019-05-13
 *
 * 简单导出数据封装Bean
 */
@Sheet
public class SimpleSheet {

	/*
	 * Sheet名称
	 */
	private String name;

	@Row(start = 1)
	private List<?> rowData;

	public SimpleSheet(List<?> rowData,String name) {
		this.rowData = rowData;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<?> getRowData() {
		return rowData;
	}

	public void setRowData(List<?> rowData) {
		this.rowData = rowData;
	}
}
