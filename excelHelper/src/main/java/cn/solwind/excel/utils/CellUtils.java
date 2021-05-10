package cn.solwind.excel.utils;

/**
* @author chfenix
* @version 创建时间：2019-05-07
*
* 单元格工具类
*/

public class CellUtils {

	/**
	 * 转换列名称至索引号
	 * 如A->1  B->2
	 * 
	 * @param colName 列名称
	 * @return 索引号
	 */
	public static int colNameToNum(String colName) {
		colName = colName.toUpperCase();
		int length = colName.length();
		int num = 0;
		int number = 0;
		for (int i = 0; i < length; i++) {
			char ch = colName.charAt(length - i - 1);
			num = (int) (ch - 'A' + 1);
			num *= Math.pow(26, i);
			number += num;
		}
		return number;
	}
}
