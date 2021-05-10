package cn.solwind.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import cn.solwind.excel.handler.ExcelHandler;
import cn.solwind.excel.handler.POIHandler;

/**
 * @author chfenix
 * @version 创建时间：2019-05-05
 *
 * ExcelHelper操作类
 */
public final class ExcelHelper {

	private static volatile ExcelHelper excelUtils;
	
	ExcelHandler handler;

	private ExcelHelper() {}
	
	private ExcelHelper(ExcelHandler handler) {
		this.handler = handler;
	}

	public static ExcelHelper getInstance() {
		if (null == excelUtils) {
			synchronized (ExcelHelper.class) {
				if (null == excelUtils) {
					// FIXME 此处增加导出实现类控制
					excelUtils = new ExcelHelper(new POIHandler());
				}
			}
		}
		return excelUtils;
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param bean
	 * @param os
	 * @throws IOException
	 */
	public void export(Object bean,OutputStream os) throws IOException {
		handler.export(bean,os);
	}
	
	/**
	 * 导出包含多个Sheet的Excel
	 * 
	 * @param multiBean List数据为通过Sheet注解标注的类
	 * @param os
	 * @throws IOException
	 */
	public void mulitSheetExport(List<?> multiBean,OutputStream os) throws IOException {
		handler.multiSheetExport(multiBean, os);
	}

	/**
	 * 简单多行数据导出
	 * 
	 * @param data 数据
	 * @param sheetName sheet名称
	 * @param os
	 */
	public void simpleExport(List<?> data,String sheetName,OutputStream os) throws IOException {
		handler.simpleExport(data, sheetName, os);
	}
	
}
