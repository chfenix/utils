package cn.solwind.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* @author chfenix
* @version 创建时间：2019-05-09
*
* 表格行尾注解
*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Foot{

	/**
	 * 行尾固定值
	 * @return
	 */
	public String value();
	
	/**
	 * 背景色
	 * @return
	 */
	public int background() default 0;
	
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
	
	/**
	 * 颜色
	 * @return
	 */
	public int color()	default 0;
	
	/**
	 * 加粗
	 * @return
	 */
	public boolean bold() default false;
	
	/**
	 * 水平对齐
	 * @return
	 */
	public int align() default 0;
}
