package cn.solwind.excel.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.solwind.excel.annotation.Cell;
import cn.solwind.excel.pojo.CellDef;
import cn.solwind.excel.pojo.LineDef;
import cn.solwind.excel.pojo.SheetDef;
import cn.solwind.excel.pojo.SimpleSheet;
import cn.solwind.excel.type.Link;
import cn.solwind.excel.type.Link.LinkType;

/**
 * @author chfenix
 * @version 创建时间：2019-05-07
 *
 * POI实现类
 */

public class POIHandler extends ExcelHandler {
	
	private static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
	private CreationHelper creationHelper;
	
	private Map<Integer, Integer> columnWidth;	// 列宽
	
	private Map<String, CellStyle> sheetCellStyle;	// 表格样式
	
	private CellStyle defaultStyle;		// 默认样式
	private Font defaultFont;			// 默认字体
	private Font linkFont;				// 链接字体样式
	
	// FIXME 修改到配置文件中
	// 不设置默认的情况下，POI默认字体为Calibri 
	private String defaultFontFamily="宋体";	// 默认字体名称
	private int defaultFontSize=11;		// 默认字体大小
	
	@Override
	public void export(Object data,OutputStream os) throws IOException {
		Workbook workbook;
		
		workbook = new XSSFWorkbook();
		generateSheet(workbook, data, getClazz(data), null);
		workbook.write(os);
	}
	
	@Override
	public void multiSheetExport(List<?> data,OutputStream os) throws IOException{
		Workbook workbook;
		
		workbook = new XSSFWorkbook();
		
		for (Object bean : data) {
			if(bean instanceof List<?>) {
				SimpleSheet simpleSheet = new SimpleSheet((List<?>) bean, null);
				generateSheet(workbook, simpleSheet, SimpleSheet.class, null);
			}
			else if(bean.getClass().isAnnotationPresent(cn.solwind.excel.annotation.Sheet.class)) {
				String sheetName = null;
				if(bean instanceof SimpleSheet) {
					// 获取Sheet名称
					sheetName = ((SimpleSheet)bean).getName();
				}
				
				generateSheet(workbook, bean, getClazz(bean), sheetName);	
			}
		}
		workbook.write(os);
	}

	@Override
	public void simpleExport(List<?> data, String sheetName,OutputStream os) throws IOException {
		Workbook workbook;
		
		workbook = new XSSFWorkbook();
		
		SimpleSheet simpleSheet = new SimpleSheet(data, sheetName);
		
		generateSheet(workbook, simpleSheet, SimpleSheet.class, sheetName);
		workbook.write(os);
	}

	/**
	 * 生成Sheet数据
	 * 
	 * @param workbook
	 * @param data
	 * @param clazz
	 * @param sheetName
	 */
	private void generateSheet(
			Workbook workbook, 
			Object data, Class<?> clazz,
			String sheetName) {
		creationHelper = workbook.getCreationHelper();
		
		// 初始化Sheet定义
		columnWidth = null;
		sheetCellStyle = null;
				
		Sheet sheet;
		// 根据类获取表格定义
		SheetDef sheetDef = getSheetDef(data);
		
		if(sheetName != null && !sheetName.isEmpty()) {
			sheet = workbook.createSheet(sheetName);
		}
		else if(!sheetDef.getName().isEmpty()) {
			sheet = workbook.createSheet(sheetDef.getName());
		}
		else {
			sheet = workbook.createSheet();
		}
		
		// 默认样式
		// FIXME 多个Sheet的默认字体定义需要重构，现在太乱了
		if(!sheetDef.getFontFamily().isEmpty()
				&& sheetDef.getFontSize() > 0) {
			defaultStyle = workbook.createCellStyle();
			defaultFont = workbook.createFont();
			
			defaultFontFamily = sheetDef.getFontFamily();
			defaultFontSize = sheetDef.getFontSize();
			
			if(!sheetDef.getFontFamily().isEmpty()) {
				// 字体名称
				defaultFont.setFontName(sheetDef.getFontFamily());
			}
			
			if(sheetDef.getFontSize() > 0) {
				// 字体大小
				defaultFont.setFontHeightInPoints((short)sheetDef.getFontSize());
			}
			defaultStyle.setFont(defaultFont);
		}
		else {
			defaultStyle = null;
			defaultFont = null;
			
			defaultFontFamily = "宋体";
			defaultFontSize = 11;
		}
		
		Row row;
		
		int intRow = 0;
		
		// 写入普通单元格
		// 获取按行汇总过的单元格
		SortedMap<Integer, List<CellDef>> normalCell = sheetDef.getCellDefInLine();
		if(normalCell != null && !normalCell.isEmpty()) {
			for (Map.Entry<Integer, List<CellDef>> entry : normalCell.entrySet()) {
				row = sheet.createRow(entry.getKey() - 1);
				writeRow(workbook, row, data, entry.getValue(), true);
			}
		}
		
		// 写入多行单元格
		List<LineDef> lineDefs = sheetDef.getLineDefs();
		if(lineDefs != null) {
			// 遍历多个多行数据定义
			for (LineDef lineDef : lineDefs) {
				intRow = lineDef.getStart() - 1;	// 多行数据起始行
				
				if(lineDef.getTitleDef() != null) {
					// 标题
					row = sheet.createRow(intRow++);
					writeRow(workbook, row, null, lineDef.getTitleDef(), true);
				}
				
				// 获取行数据
				List<?> oneRowData = (List<?>)getLineData(data, lineDef.getFiled());
				boolean bolDefRow = true;
				for (Object oneCell : oneRowData) {
					// 写入一行数据
					row = sheet.createRow(intRow++);
					writeRow(workbook, row, oneCell, lineDef.getDataDef(),bolDefRow);
					
					bolDefRow = false;
				}
			}
		}
		
		// 处理列宽度
		if(columnWidth != null && columnWidth.size() > 0) {
			for (Map.Entry<Integer, Integer> entry : columnWidth.entrySet()) {
				if(entry.getValue() == Cell.WIDTH_AUTO) {
					// 自适应列宽
					sheet.autoSizeColumn(entry.getKey());
				}else if(entry.getValue() > 0) {
					// 指定列宽
					sheet.setColumnWidth(entry.getKey(), entry.getValue() * 256);
				}
			}
		}
	}
	
	/**
	 * 写入一行单元格数据
	 * 
	 * @param woorkbook
	 * @param row
	 * @param data
	 * @param cellDefs
	 * @param defRow 是否为定义行（普通单元格，或多行数据第一行，判断是否需要生成单元格样式等定义）
	 */
	private void writeRow(Workbook workbook, Row row, Object data,List<CellDef> cellDefs, boolean defRow) {
	
		int maxRowHeight = 0;		// 最大行高
		
		if(cellDefs == null) {
			return;
		}
		
		Set<Integer> dataCol = new HashSet<>();		// 已经有数据的列
		for (int i = 0; i < cellDefs.size(); i++) {
			
			CellStyle cellStyle = null;
			
			// 保存最大行高
			if(cellDefs.get(i).getHeight() > 0 && cellDefs.get(i).getHeight() > maxRowHeight) {
				maxRowHeight = cellDefs.get(i).getHeight();
			}
			
			// 针对定义行生成相关设置，多行数据重复行不在重复生成计算
			if(defRow) {
				// 保存最大列宽，如指定了列宽则无视自适应列宽设定
				if(cellDefs.get(i).getWidth() > 0 || cellDefs.get(i).getWidth() == Cell.WIDTH_AUTO) {
					if(columnWidth == null) {
						columnWidth = new HashMap<>();
					}
					Integer width = columnWidth.get(cellDefs.get(i).getColNumStart0());		// Mapkey为列坐标，从0开始
					if(width == null) {
						columnWidth.put(cellDefs.get(i).getColNumStart0(), cellDefs.get(i).getWidth());
					}
					else {
						if(cellDefs.get(i).getWidth() > width) {
							columnWidth.put(cellDefs.get(i).getColNumStart0(), cellDefs.get(i).getWidth());
						}
					}
				}

				// 生成样式
				cellStyle = getCellStyle(workbook, cellDefs.get(i));
				
				if(cellStyle != null) {
					if(sheetCellStyle == null) {
						sheetCellStyle = new HashMap<>();
					}
					// 保存样式至Map
					sheetCellStyle.put(cellDefs.get(i).getFiled(), cellStyle);
				}
			}
			else {
				// 非定义行，获取样式
				if(sheetCellStyle != null) {
					cellStyle = sheetCellStyle.get(cellDefs.get(i).getFiled());
				}
			}
			
			if(dataCol.contains(cellDefs.get(i).getColNumStart0())) {
				// 当前Cell已经存在数据，为定义中重复列定义，判断当前数据是否有值，如果有，则覆盖原数据Cell，如果没有，则保留原数据Cell
				if(data != null && getDataValue(data,cellDefs.get(i)) == null) {
					continue;
				}
			}
			
			org.apache.poi.ss.usermodel.Cell cell = row.createCell(cellDefs.get(i).getColNumStart0());
			
			dataCol.add(cellDefs.get(i).getColNumStart0());		// 保存已经写入值的列位置
			
			if(cellDefs.get(i).getValue() != null && !cellDefs.get(i).getValue().isEmpty()) {
				// 存在预设的value
				cell.setCellValue(cellDefs.get(i).getValue());
			}
			else {
				// 按照类型写入
				Object cellData = getDataValue(data,cellDefs.get(i));	// 获取属性值
				if(cellData != null) {
					Class<?> clazz = cellData.getClass();
					
					// 处理超链接类
					// data == null 的时候为多行标题，不做超链接样式控制
					if(clazz.equals(Link.class) && data != null) {
						// 存在超链接，获取实际数据
						Link link = (Link)cellData;
						CreationHelper helper = workbook.getCreationHelper();
						
						Hyperlink hyperlink = helper.createHyperlink(convertLinkType(link.getType()));
						hyperlink.setAddress(link.getAddress());
						cell.setHyperlink(hyperlink);
						
						cellData = link.getValue();
						clazz = cellData.getClass();
					}
					
					switch (clazz.getName()) {
					// Boolean
					case "java.lang.Boolean":
						cell.setCellValue((Boolean)cellData);
						break;
					// 日期
					case "java.util.Date":
						// 日期单元格样式处理
						cell.setCellValue((Date)cellData);
						break;
					// 数字
					case "java.lang.Integer":
						cell.setCellValue(new Double((Integer)cellData));
						break;
					case "java.lang.Double":
						cell.setCellValue((Double)cellData);
						break;
					case "java.lang.Long":
						cell.setCellValue(new Double((Long)cellData));
						break;
					case "java.lang.Float":
						cell.setCellValue(new Double((Float)cellData));
						break;
					case "java.math.BigDecimal":
						cell.setCellValue(((BigDecimal)cellData).doubleValue());
						break;
					// 默认使用字符串类型
					default:
						cell.setCellValue(cellData.toString());
						break;
					}
				}
			}
			
			// 设置样式
			if(cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}
			else if(defaultStyle != null) {
				// 无指定样式，使用默认样式
				cell.setCellStyle(defaultStyle);
			}
		}
		
		// 设置行高
		if(maxRowHeight > 0) {
			row.setHeightInPoints(maxRowHeight);
		}
	}
	
	/**
	 * 根据注解生成单元格样式
	 * 
	 * @param workbook
	 * @param definitions
	 * @return
	 */
	private CellStyle getCellStyle(Workbook workbook, CellDef def) {
		
		if(def.isCustomStyle() || def.isCustomFont() || def.getClazz().equals(Date.class)) {
			CellStyle cellStyle = workbook.createCellStyle();
			if(def.isCustomStyle()) {
				if(def.getAlign() > 0) {
					// 水平对齐
					cellStyle.setAlignment(convertAlign(def.getAlign()));
				}
				
				if(def.getBackground() > 0) {
					// 背景色
					cellStyle.setFillForegroundColor(convertColor(def.getBackground()));
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				}
				
				if(def.getBorder() != null && def.getBorder().length > 0) {
					// 边框
					for (int i = 0; i < def.getBorder().length; i++) {
						switch (def.getBorder()[i]) {
						case Cell.BORDER_THIN_BOTTOM:
							cellStyle.setBorderBottom(BorderStyle.THIN);
							break;
						case Cell.BORDER_THIN_LEFT:
							cellStyle.setBorderLeft(BorderStyle.THIN);
							break;
						case Cell.BORDER_THIN_TOP:
							cellStyle.setBorderTop(BorderStyle.THIN);
							break;
						case Cell.BORDER_THIN_RIGHT:
							cellStyle.setBorderRight(BorderStyle.THIN);
							break;
						case Cell.BORDER_BOLD_BOTTOM:
							cellStyle.setBorderBottom(BorderStyle.MEDIUM);
							break;
						case Cell.BORDER_BOLD_LEFT:
							cellStyle.setBorderLeft(BorderStyle.MEDIUM);
							break;
						case Cell.BORDER_BOLD_TOP:
							cellStyle.setBorderTop(BorderStyle.MEDIUM);
							break;
						case Cell.BORDER_BOLD_RIGHT:
							cellStyle.setBorderRight(BorderStyle.MEDIUM);
							break;
						default:
							break;
						}
					}
				}
				
				if(def.getFormat() != null && !def.getFormat().isEmpty()) {
					// 格式化
					if(BuiltinFormats.getBuiltinFormat(def.getFormat()) != -1) {
						// 内嵌格式
						cellStyle.setDataFormat((short)BuiltinFormats.getBuiltinFormat(def.getFormat()));
					}
					else {
						// 自定义格式
						cellStyle.setDataFormat(workbook.createDataFormat().getFormat(def.getFormat()));
					}
				}
			}
			
			if(def.isCustomFont()) {
				// 存在自定义字体
				Font font = workbook.createFont();
				if(!def.getFontFamily().isEmpty()) {
					// 字体名称
					font.setFontName(def.getFontFamily());
				}
				else if(defaultFontFamily != null && !defaultFontFamily.isEmpty()) {
					// 默认字体
					font.setFontName(defaultFontFamily);
				}
				
				if(def.getFontSize() > 0) {
					// 字体大小
					font.setFontHeightInPoints((short)def.getFontSize());
				}
				else if(defaultFontSize > 0){
					// 默认字体大小
					font.setFontHeightInPoints((short)defaultFontSize);
				}
				
				if(def.getColor() > 0) {
					// 字体颜色
					font.setColor(convertColor(def.getColor()));
				}
				else if(def.getClazz().equals(Link.class)) {
					// 超链接未定义字体颜色，默认蓝色
					font.setColor(IndexedColors.BLUE1.index);
				}
				
				if(def.isBold()) {
					// 加粗
					font.setBold(def.isBold());
				}
				
				if(def.getClazz().equals(Link.class)) {
					// 超链接加下划线
					font.setUnderline(Font.U_SINGLE);
				}
				
				cellStyle.setFont(font);
			}
			else if(defaultStyle  != null){
				// 有定义样式，但是未定义字体，使用默认字体
				cellStyle.setFont(defaultFont);
			}
			
			if(def.getClazz().equals(Date.class)
					&& (def.getFormat() == null || def.getFormat().isEmpty())) {
				// 日期类型
				cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DEFAULT_DATE_FORMATE));
			}
			return cellStyle;
		}
		return null;
	}
	
	/**
	 * 将颜色转换为POI颜色定义
	 * 
	 * @param color
	 * @return
	 */
	private short convertColor(int color) {
		
		switch (color) {
		case 1:
			return IndexedColors.BLACK1.index;
		case 2:
			return IndexedColors.RED1.index;
		case 3:
			return IndexedColors.BRIGHT_GREEN1.index;
		case 4:
			return IndexedColors.BLUE1.index;
		case 5:
			return IndexedColors.YELLOW1.index;
		case 6:
			return IndexedColors.PINK1.index;
		case 7:
			return IndexedColors.TURQUOISE1.index;
		case 8:
			return IndexedColors.GREY_25_PERCENT.index;
		case 9:
			return IndexedColors.GREY_50_PERCENT.index;
		case 10:
			return IndexedColors.GREY_50_PERCENT.index;
		default:
			return -1;
		}
	}
	
	/**
	 * 转换水平对齐
	 * 
	 * @param align
	 * @return
	 */
	private HorizontalAlignment convertAlign(int align) {
		switch (align) {
		case 1:
			return HorizontalAlignment.LEFT;
		case 2:
			return HorizontalAlignment.CENTER;
		case 3:
			return HorizontalAlignment.RIGHT;
		default:
			return null;
		}
	}
	
	/**
	 * 转换超链接类型
	 * 
	 * @param linkType
	 * @return
	 */
	private HyperlinkType convertLinkType(LinkType linkType) {
		
		if(linkType != null) {
			switch (linkType) {
			case URL:
				// URL
				return HyperlinkType.URL;
			case FILE:
				// 文件
				return HyperlinkType.FILE;
			case DOCUMENT:
				// 文档?
				return HyperlinkType.DOCUMENT;
			case EMAIL:
				// Email
				return HyperlinkType.EMAIL;
			default:
				// 默认为url
				return HyperlinkType.URL;
			}
		}
		else {
			// 默认为url
			return HyperlinkType.URL;
		}
	}

}
