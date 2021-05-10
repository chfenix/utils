package cn.solwind.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author chfenix
* @version 创建时间：2019-05-09
*
* Sheet注解
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {

	/**
	 * Sheet名称
	 * @return
	 */
	public String name() default "";
	
	/**
	 * 字体名称
	 * @return
	 */
	public String fontFamily() default "";
	
	/**
	 * 字体大小
	 * @return
	 */
	public int fontSize() default 0;
}
