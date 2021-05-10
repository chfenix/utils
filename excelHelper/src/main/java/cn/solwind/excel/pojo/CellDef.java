package cn.solwind.excel.pojo;

import cn.solwind.excel.annotation.Cell;
import cn.solwind.excel.annotation.Foot;
import cn.solwind.excel.annotation.Title;
import cn.solwind.excel.converter.ReadConvertible;
import cn.solwind.excel.converter.WriteConvertible;
import cn.solwind.excel.type.Link;
import cn.solwind.excel.utils.CellUtils;

/**
 * @author chfenix
 * @version 创建时间：2019-05-07
 *
 * 单元格定义
 */

public class CellDef implements Comparable<CellDef> {
	
	/*
	 * 行号 起始行号：1
	 */
	private int row;

	/*
	 * 列名称 如：AA
	 */
	private String col;

	/*
	 * 列序号 如：27
	 */
	private int colNum;
	
	/*
	 * 列宽
	 */
	private int width;
	
	/*
	 * 行高
	 */
	private int height;
	
	/*
	 * 单元格内容
	 */
	private String value;
	
	/*
	 * 背景色
	 */
	private int background;
	
	/*
	 * 边框
	 */
	private int[] border;
	
	/*
	 * 字体名称
	 */
	private String fontFamily;
	
	/*
	 * 字体大小
	 */
	private int fontSize;
	
	/*
	 * 颜色
	 */
	private int color;
	
	/*
	 * 加粗
	 */
	private boolean bold;
	
	/*
	 * 水平对齐
	 */
	private int align;
	
	/*
	 * 格式化
	 */
	private String format;
	
	/*
     * 注解域
     */
    private String filed;

    /*
     * 属性类型
     */
    private Class<?> clazz;
    
    /**
     * 写数据转换器
     */
    private WriteConvertible writeConverter;

    /**
     * 读数据转换器
     */
    private ReadConvertible readConverter;
	
	public CellDef(Cell cell,String filed,Class<?> clazz) {
		this.row = cell.row();
		this.col = cell.col();
		this.value = cell.value();
		this.width = cell.width();
		this.height = cell.height();
		this.background = cell.background();
		this.border = cell.border();
		this.fontFamily = cell.fontFamily();
		this.fontSize = cell.fontSize();
		this.color = cell.color();
		this.bold = cell.bold();
		this.align = cell.align();
		this.format = cell.format();
		this.filed = filed;
		this.clazz = clazz;
		try {
			this.writeConverter = cell.writeConverter().newInstance();
			this.readConverter = cell.readConverter().newInstance();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 实例化标题定义
	 * @param title
	 * @param cell
	 * @param filed
	 * @param clazz
	 */
	public CellDef(Title title,Cell cell,String filed,Class<?> clazz) {
		this.col = cell.col();
		this.value = title.value();
		this.background = title.background();
		this.border = title.border();
		this.fontFamily = title.fontFamily();
		this.fontSize = title.fontSize();
		this.color = title.color();
		this.bold = title.bold();
		this.align = title.align();
		this.filed = filed;
		this.clazz = clazz;
	}
	
	/**
	 * 实例化页尾定义
	 * @param foot
	 * @param cell
	 * @param filed
	 * @param clazz
	 */
	public CellDef(Foot foot,Cell cell,String filed,Class<?> clazz) {
		this.col = cell.col();
		this.value = foot.value();
		this.background = foot.background();
		this.fontFamily = foot.fontFamily();
		this.fontSize = foot.fontSize();
		this.color = foot.color();
		this.bold = foot.bold();
		this.align = foot.align();
		this.filed = filed;
		this.clazz = clazz;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public int[] getBorder() {
		return border;
	}

	public void setBorder(int[] border) {
		this.border = border;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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

	public WriteConvertible getWriteConverter() {
		return writeConverter;
	}

	public void setWriteConverter(WriteConvertible writeConverter) {
		this.writeConverter = writeConverter;
	}

	public ReadConvertible getReadConverter() {
		return readConverter;
	}

	public void setReadConverter(ReadConvertible readConverter) {
		this.readConverter = readConverter;
	}

	/*-------------------------
	 * 自定义方法
	 * ------------------------
	 */
	/**
	 * 获取列序号，第一列为1
	 * @return
	 */
	public int getColNum() {
		return CellUtils.colNameToNum(col);
	}
	
	/**
	 * 获取列序号，第一列为0
	 * @return
	 */
	public int getColNumStart0() {
		return CellUtils.colNameToNum(col) - 1;
	}
	
	/**
	 * 是否自定义样式
	 * @return
	 */
	public boolean isCustomStyle() {
		if(align == 0			// 水平对齐
				&& background == 0	// 背景色
				&& (border == null || border.length == 0)	// 边框
				&& (format == null || format.isEmpty())		// 格式化
				) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * 是否自定义字体
	 * @return
	 */
	public boolean isCustomFont() {
		
		if((fontFamily == null || fontFamily.isEmpty())	// 字体
				&& fontSize == 0	// 字体大小
				&& color == 0		// 颜色
				&& bold == false	// 加粗
				&& !clazz.equals(Link.class)	// 超链接
				) {	
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * 比较排序
	 */
	public int compareTo(CellDef o) {
		return colNum - o.colNum;
	}
	
}
