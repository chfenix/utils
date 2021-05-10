package cn.solwind.excel.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import cn.solwind.excel.type.Link;
import cn.solwind.excel.utils.CellUtils;

/**
* @author Zhouluning
* @version 创建时间：2019-05-07
*
* 类说明
*/

public class TestUtils {

	@Test
	public void testColNameToNum() {
		int num = CellUtils.colNameToNum("AA");
		System.out.println(num);
	}

}
