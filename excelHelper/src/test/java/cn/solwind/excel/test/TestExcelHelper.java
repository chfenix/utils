package cn.solwind.excel.test;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cn.solwind.excel.ExcelHelper;
import cn.solwind.excel.pojo.SimpleSheet;
import cn.solwind.excel.type.Link;
import cn.solwind.excel.type.Link.LinkType;

/**
* @author chfenix
* @version 创建时间：2019-05-07
*
* ExcelHelper测试类
*/

public class TestExcelHelper {
	
	ExcelHelper helper;
	
	@Before
	public void setup() {
		helper = ExcelHelper.getInstance();
	}
	
	@Test
	public void testExport() {
		try {
			SampleData sampleData = new SampleData();
			sampleData.setCount(100);
			sampleData.setNow(new Date());
			sampleData.setCreateTime(new Date());
			sampleData.setUpdateTime(new Date()); 
			sampleData.setRate(new BigDecimal(0.1234));
			
			Link file = new Link("文件","201905241120/2028_90527179_1042192.png",LinkType.FILE);
			sampleData.setFile(file);
			
			List<LineData> listdata = new ArrayList<LineData>();
			for (long i = 0; i < 100; i++) {
				LineData lineData = new LineData();
				
				lineData.setId(i);
				lineData.setName("测试" + (new Date()).getTime());
				lineData.setAge((int)(i+1)/40);
				
				listdata.add(lineData);
			}
			sampleData.setRowData(listdata);
			
			
			FileOutputStream fos = new FileOutputStream("D:/tmp/test.xlsx");
			helper.export(sampleData, fos); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMulitSheetExport() {
		try {
			List<Object> listData = new ArrayList<>();
			
			SampleData sampleData = new SampleData();
			sampleData.setCount(100);
			sampleData.setNow(new Date());
			
			List<LineData> listdata = new ArrayList<LineData>();
			for (long i = 0; i < 100; i++) {
				LineData lineData = new LineData();
				
				lineData.setId(i);
				lineData.setName("测试" + (new Date()).getTime());
				lineData.setAge((int)(i+1)/40);
				
				listdata.add(lineData);
			}
			sampleData.setRowData(listdata);
			listData.add(sampleData);
			
			List<LineData> listData1 = new ArrayList<>();
			for (long i = 0; i < 100; i++) {
				LineData lineData = new LineData();
				
				lineData.setId(i);
				lineData.setName("测试2" + (new Date()).getTime());
				lineData.setAge((int)(i+1)/40);
				
				listData1.add(lineData);
			}
			listData.add(new SimpleSheet(listData1, "测试2"));
			
			
			
			FileOutputStream fos = new FileOutputStream("D:/tmp/test.xlsx");
			helper.mulitSheetExport(listData, fos); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSimpleExport() {
		try {
			
			List<LineData> listdata = new ArrayList<LineData>();
			for (long i = 0; i < 100; i++) {
				LineData sampleData = new LineData();
				
				sampleData.setId(i);
				sampleData.setName("测试" + (new Date()).getTime());
				sampleData.setAge((int)(i+1)/40);
				
				listdata.add(sampleData);
			}
			
			FileOutputStream fos = new FileOutputStream("D:/tmp/test.xlsx");
			helper.simpleExport(listdata,"测试简单导出", fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
