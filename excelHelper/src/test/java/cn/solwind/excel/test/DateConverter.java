package cn.solwind.excel.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.solwind.excel.converter.WriteConvertible;

/**
* @author Zhouluning
* @version 创建时间：2019-04-30
*
* 类说明
*/

public class DateConverter implements WriteConvertible {

	@Override
	public Object execWrite(Object object) {
		
		if(object != null) {
			Date date = (Date) object;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
		}
		return null;
	}

}
