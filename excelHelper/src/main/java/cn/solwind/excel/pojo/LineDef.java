package cn.solwind.excel.pojo;

import java.util.ArrayList;
import java.util.List;

/**
* @author chfenix
* @version 创建时间：2019-05-09
*
* 行定义
*/

public class LineDef {
	
	/*
	 * 起始行号
	 */
	private int start;

	/*
	 * 行高
	 */
	private int height;
	
	/*
	 * 多行标题定义
	 */
	private List<CellDef> titleDef;
	
	/*
	 * 多行数据定义
	 */
	private List<CellDef> dataDef;
	
	/*
	 * 多行数据页尾定义
	 */
	private List<CellDef> footDef;
	
	/*
     * 注解域
     */
    private String filed;

    /*
     * 属性类型
     */
    private Class<?> clazz;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<CellDef> getTitleDef() {
		return titleDef;
	}

	public void setTitleDef(List<CellDef> titleDef) {
		this.titleDef = titleDef;
	}

	public List<CellDef> getDataDef() {
		return dataDef;
	}

	public void setDataDef(List<CellDef> dataDef) {
		this.dataDef = dataDef;
	}

	public List<CellDef> getFootDef() {
		return footDef;
	}

	public void setFootDef(List<CellDef> footDef) {
		this.footDef = footDef;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	/*
	 * 自定义方法
	 */
	public void addTitleDef(CellDef cellDef) {
		if(titleDef == null) {
			titleDef = new ArrayList<>();
		}
		titleDef.add(cellDef);
	}
	
	public void addDataDef(CellDef cellDef) {
		if(dataDef == null) {
			dataDef = new ArrayList<>();
		}
		dataDef.add(cellDef);
	}
	
	public void addFootDef(CellDef cellDef) {
		if(footDef == null) {
			footDef = new ArrayList<>();
		}
		footDef.add(cellDef);
	}
}
