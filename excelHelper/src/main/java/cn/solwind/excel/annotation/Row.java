package cn.solwind.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author chfenix
* @version 创建时间：2019-05-09
*
* 多行数据注解
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Row {

	/**
	 * 起始行号
	 * @return
	 */
	public int start();
}
