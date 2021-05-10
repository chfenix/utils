package cn.solwind.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.solwind.excel.converter.DefaultConvertible;
import cn.solwind.excel.converter.ReadConvertible;
import cn.solwind.excel.converter.WriteConvertible;


/**
 * Excel 单元格注解
 * 
 * @author chfenix
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Cell {
	
	/*
	 * 常量定义
	 */
	// 宽度
	public static final int WIDTH_AUTO = -1;		// 自适应列宽
	
	// 颜色
	public static final int COLOR_BLACK = 1;			// 黑
	public static final int COLOR_RED = 2;				// 红
	public static final int COLOR_GREEN = 3;			// 绿
	public static final int COLOR_BLUE = 4;				// 蓝
	public static final int COLOR_YELLOW = 5;			// 黄
	public static final int COLOR_PINK = 6;				// 粉
	public static final int COLOR_TURQUOISE = 7;		// 绿松石 
	public static final int COLOR_GREY_25_PERCENT = 8;	// 25%灰
	public static final int COLOR_GREY_50_PERCENT = 9;	// 50%灰
	public static final int COLOR_GREY_80_PERCENT = 10;	// 80%灰
	
	// 水平对齐
	public static final int ALIGN_LEFT = 1;		// 左对齐
	public static final int ALIGN_CENTER = 2;	// 剧终
	public static final int ALIGN_RIGHT = 3;	// 右对齐
	
	// 边框
	public static final int BORDER_THIN_BOTTOM = 1;	// 底边框
	public static final int BORDER_THIN_LEFT = 2;	// 左边框
	public static final int BORDER_THIN_TOP = 3;		// 顶边框
	public static final int BORDER_THIN_RIGHT = 4;	// 右边框
	
	public static final int BORDER_BOLD_BOTTOM = 5;	// 底边框
	public static final int BORDER_BOLD_LEFT = 6;	// 左边框
	public static final int BORDER_BOLD_TOP = 7;		// 顶边框
	public static final int BORDER_BOLD_RIGHT = 8;	// 右边框

	/**
	 * 所在行
	 * 默认为0，导出多行数据时不需要指定行号
	 * @return
	 */
	public int row() default 0;

    /**
     * 所在列
     * @return
     */
	public String col();
	
	/**
	 * 单元格内容
	 * 设置了value后，属性无需赋值，生成Excel会直接写入value值
	 * @return
	 */
	public String value() default "";
	
	/**
	 * 宽度，单位为1英文字符宽度
	 * 默认0，不指定列宽；可使用常量WIDTH_AUTO设定自适应列宽
	 * @return
	 */
	public int width() default 0;
	
	/**
	 * 高度
	 * 默认为0，不指定行高；同一行不同行高以最大值为准
	 * @return
	 */
	public int height() default 0;
	
	/**
	 * 背景色
	 * @return
	 */
	public int background() default 0;
	
	/**
	 * 边框
	 * @return
	 */
	public int[] border() default {};
	
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
	 * 默认:false
	 * @return
	 */
	public boolean bold() default false;
	
	/**
	 * 水平对齐
	 * @return
	 */
	public int align() default 0;
	
	/**
	 * 格式化
	 * 目前仅针对日期、数字有效
	 * @return
	 */
	public String format() default "";
	
	/**
     * 写数据转换器
     *
     * @return 写入Excel数据转换器
     */
    Class<? extends WriteConvertible> writeConverter()
            default DefaultConvertible.class;

    /**
     * 读数据转换器
     *
     * @return 读取Excel数据转换器
     */
    Class<? extends ReadConvertible> readConverter()
            default DefaultConvertible.class;
	
}
