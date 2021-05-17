package cn.solwind.common;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;

/**
 * Velocity 工具类
 *
 * @author zln
 *
 */
public class VelocityUtil {

	/**
	 * 填充内容中的velocity部分
	 *
	 * @param content
	 * @param params
	 * @return
	 */
	public static String evaluate(String content,Map<String, Object> params) {

		String strReturn = null;
		try {
			if(StringUtils.isBlank(content)) {
				return null;
			}

			//  初始化运行时引擎
		    Velocity.init();

		    VelocityContext context = new VelocityContext();

		    // 启用DateTool
		    Map<String, Object> mapParam = new HashMap<String, Object>();
		    mapParam.put("DateTool", new DateTool());
		    mapParam.put("DateUtils", new DateUtils());
		    mapParam.put("DateFormatUtils", new DateFormatUtils());
		    mapParam.put("now", new Date());

		    if(params != null) {
		    	mapParam.putAll(params);
		    }

		    for (String strKey : mapParam.keySet()) {
				context.put(strKey, mapParam.get(strKey));
			}

		    /* 解析后数据的输出目标，java.io.Writer的子类 */
		    StringWriter sw = new StringWriter();

	    	/* 进行解析 */
		    Velocity.evaluate(context,sw,"agiledata",content);
		    strReturn = sw.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strReturn;
	}
}
