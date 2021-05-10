package cn.solwind.excel.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author chfenix
 * @version 创建时间：2019-05-09
 *
 * Sheet定义
 */

public class SheetDef {

	/*
	 * 名称
	 */
	private String name;
	
	/*
	 * 字体名称
	 */
	private String fontFamily;
	
	/*
	 * 字体大小
	 */
	private int fontSize;
	
	/*
	 * 固定单元格定义
	 */
	private List<CellDef> cellDefs;
	
	/*
	 * 按照行汇总并排序的固定单元格定义
	 */
	private SortedMap<Integer, List<CellDef>> cellDefInLine;
	
	/*
	 * 多行数据定义
	 */
	private List<LineDef> lineDefs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<CellDef> getCellDefs() {
		return cellDefs;
	}

	public void setCellDefs(List<CellDef> cellDefs) {
		this.cellDefs = cellDefs;
	}

	public SortedMap<Integer, List<CellDef>> getCellDefInLine() {
		return cellDefInLine;
	}

	public void setCellDefInLine(SortedMap<Integer, List<CellDef>> cellDefInLine) {
		this.cellDefInLine = cellDefInLine;
	}

	public List<LineDef> getLineDefs() {
		return lineDefs;
	}

	public void setLineDefs(List<LineDef> lineDefs) {
		this.lineDefs = lineDefs;
	}
	
	/*---------------------
	 * 处理方法
	 * --------------------
	 */
	/**
	 * 增加单元格定义
	 * 
	 * @param cellDef
	 */
	public void addCell(CellDef cellDef) {
		if(cellDefs == null) {
			cellDefs = new ArrayList<CellDef>();
		}
		cellDefs.add(cellDef);
		
		if(cellDefInLine == null) {
			cellDefInLine = new TreeMap<Integer, List<CellDef>>();
		}
		
		List<CellDef> listDef = cellDefInLine.get(cellDef.getRow());
		if(listDef == null) {
			listDef = new ArrayList<CellDef>();
		}
		listDef.add(cellDef);
		cellDefInLine.put(cellDef.getRow(), listDef);
	}
	
	/**
	 * 增加多行数据定义
	 * 
	 * @param lineDef
	 */
	public void addLineDef(LineDef lineDef) {
		if(lineDefs == null) {
			lineDefs = new ArrayList<LineDef>();
		}
		lineDefs.add(lineDef);
	}
}
