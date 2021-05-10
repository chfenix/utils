package cn.solwind.excel.handler;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.solwind.excel.annotation.Cell;
import cn.solwind.excel.annotation.Foot;
import cn.solwind.excel.annotation.Row;
import cn.solwind.excel.annotation.Sheet;
import cn.solwind.excel.annotation.Title;
import cn.solwind.excel.converter.DefaultConvertible;
import cn.solwind.excel.pojo.CellDef;
import cn.solwind.excel.pojo.LineDef;
import cn.solwind.excel.pojo.SheetDef;

/**
 * @author chfenix
 * @version 创建时间：2019-05-07
 *
 * Excel导出实现接口
 */

public abstract class ExcelHandler {
	
	/*
     * 方法类型
     */
	public enum FieldType {
		GETTER, SETTER
	}
	
    /**
     * 导出Excel
     * 
     * @param data
     * @param os
     * @throws IOException
     */
    public void export(Object data,OutputStream os) throws IOException{}
    
    /**
     * 多Sheet导出
     * 
     * @param data
     * @param os
     * @throws IOException
     */
    public void multiSheetExport(List<?> data,OutputStream os) throws IOException{}

    /**
     * 简单导出多行Excel
     * 
     * @param data
     * @param sheetName
     * @param os
     */
    public void simpleExport(List<?> data, String sheetName,OutputStream os) throws IOException{}

    /* -----------------------------------------
     * 通用基类方法
     * -----------------------------------------
     */
    /**
     * 根据注解生成Sheet定义
     * @param bean 数据实例
     * @return
     */
    protected SheetDef getSheetDef(Object bean) {
    	
    	Class<?> defClazz = bean.getClass();
    	
    	SheetDef sheetDef = new SheetDef();
    	
    	// Sheet定义
    	if(defClazz.isAnnotationPresent(Sheet.class)) {
    		Sheet sheet = defClazz.getAnnotation(Sheet.class);
    		
    		sheetDef.setName(sheet.name());	// Sheet名称
    		sheetDef.setFontFamily(sheet.fontFamily());		// 默认字体
    		sheetDef.setFontSize(sheet.fontSize());			// 默认字体大小
    	}

		List<Field> fields = new ArrayList<Field>();
		
		// 读取全部属性
		for (Class<?> clazz = defClazz; clazz != Object.class; clazz = clazz.getSuperclass()) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		
		// 遍历属性
		for (Field field : fields) {
			if (field.isAnnotationPresent(Cell.class)) {
				// 普通单元格
				Cell cell = field.getAnnotation(Cell.class);
				sheetDef.addCell(new CellDef(
							cell,
							field.getName(),
							field.getType()));
			}
			else if(field.isAnnotationPresent(Row.class)) {
				// 多行数据
				LineDef lineDef = new LineDef();
				Row row = field.getAnnotation(Row.class);
				lineDef.setStart(row.start());
				lineDef.setFiled(field.getName());
				lineDef.setClazz(field.getType());
				try {
					Method method = getterOrSetter(defClazz, field.getName(), FieldType.GETTER);
					List<?> lineData = (List<?>)method.invoke(bean);
					
					// 无数据
					if(lineData == null) {
						continue;
					}
					
					// 获取定义中第一个元素的类，增加多行数据定义
					Class<?> clazzLineDef = lineData.get(0).getClass();
					
					getLineDef(lineDef, clazzLineDef);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sheetDef.addLineDef(lineDef);
			}
		}
		return sheetDef;
	}
    
	/**
	 * 生成多行数据定义
	 * 
	 * @param lineDef
	 * @param defClazz
	 * @return
	 */
	protected LineDef getLineDef(LineDef lineDef, Class<?> defClazz) {

		List<Field> fields = new ArrayList<Field>();

		for (Class<?> clazz = defClazz; clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}

		for (Field field : fields) {
			// 获取 Cell注解
			if (field.isAnnotationPresent(Cell.class)) {
				Cell cell = field.getAnnotation(Cell.class);

				lineDef.addDataDef(
						new CellDef(cell, 
								field.getName(),field.getType()));

				/*--------------------------------------------
				 * 标题和行尾注解基于Cell注解而存在，不能单独存在
				 * -------------------------------------------
				 */
				// 获取标题注解
				if (field.isAnnotationPresent(Title.class)) {
					Title title = field.getAnnotation(Title.class);
					lineDef.addTitleDef(
							new CellDef(title, cell,
									lineDef.getFiled() + ".Title." + field.getName(), field.getType()));
				}

				// 获取行尾注解
				if (field.isAnnotationPresent(Foot.class)) {
					Foot foot = field.getAnnotation(Foot.class);
					lineDef.addTitleDef(
							new CellDef(foot, cell,
									lineDef.getFiled() + ".Foot." + field.getName(), field.getType()));
				}
			}
		}
		return lineDef;
	}
	
	/**
     * 获取行数据
     *
     * @param bean	对象
	 * @param fieldName	字段名
     * @return
     */
	protected Object getLineData(Object bean, String fieldName) {

		if (bean == null || fieldName == null) {
			throw new IllegalArgumentException("Operating bean or filed class must not be null");
		}

		Method method;
		Object object = null;
		try {
			method = getterOrSetter(bean.getClass(), fieldName,
					FieldType.GETTER);
			object = method.invoke(bean);
		} catch (Exception e) {
			// FIXME 抛出自定义异常
			e.printStackTrace();
		}
		return object;
	}
	
	/**
     * 获取属性值
     *
     * @param bean	对象
	 * @param cellDef	字段定义
     * @return
     */
	protected Object getDataValue(Object bean, CellDef cellDef) {

		if (bean == null || cellDef == null) {
			throw new IllegalArgumentException("Operating bean or filed class must not be null");
		}

		Method method;
		Object object = null;
		try {
			method = getterOrSetter(bean.getClass(), cellDef.getFiled(),FieldType.GETTER);
			object = method.invoke(bean);
		} catch (Exception e) {
			// FIXME 抛出自定义异常
			e.printStackTrace();
		}
		
		/*if(!cellDef.getFormat().isEmpty()) {
			// format不为空，进行转换
			if(cellDef.getClazz().equals(Date.class)) {
				// 日期类型
				SimpleDateFormat sdf = new SimpleDateFormat(cellDef.getFormat());
				object = sdf.format(object);
			}
			
			// 数字类型
			if(cellDef.getClazz().equals(Integer.class)
					|| cellDef.getClazz().equals(Double.class)
					|| cellDef.getClazz().equals(Long.class)
					|| cellDef.getClazz().equals(Float.class)
					|| cellDef.getClazz().equals(BigDecimal.class)) {
				DecimalFormat df = new DecimalFormat(cellDef.getFormat());
				object = df.format(object);
			}
		}*/

		if (null != cellDef.getWriteConverter() && cellDef.getWriteConverter().getClass() != DefaultConvertible.class) {
			// 写入转换器
			object = cellDef.getWriteConverter().execWrite(object);
		}
		return object;
	}
    
    /**
     * 获取类Get或Set方法
     * 
     * @param clazz
     * @param fieldName
     * @param methodType
     * @return
     * @throws IntrospectionException
     */
    protected Method getterOrSetter(Class<?> clazz, String fieldName, FieldType methodType)
            throws IntrospectionException {

        if (null == fieldName || "".equals(fieldName))
            return null;

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            if (fieldName.equalsIgnoreCase(prop.getName())) {
                if (FieldType.SETTER == methodType) {
                    return prop.getWriteMethod();
                }
                if (FieldType.GETTER == methodType) {
                    return prop.getReadMethod();
                }
            }
        }
        throw new IntrospectionException("Can not get the getter or setter method");
    }
    
    /**
     * 获取数据clazz
     * 
     * @param data
     * @return
     */
    protected Class<?> getClazz(Object data) {
		
    	Object obj4Class;
    	if(data instanceof List<?>) {
    		// 数据为列表，获取第一个元素类型
    		obj4Class =((List<?>)data).get(0);
    	}
    	else {
    		// 数据为普通Object
    		obj4Class = data;
    	}
    	
    	return obj4Class.getClass();
	}
    
    /**
     * 获取行定义中的最大行高设置
     * 
     * @param definitions
     * @return
     */
    protected int getMaxHeight(List<CellDef> definitions) {
    	Integer[] arrHeight = new Integer[definitions.size()];
		for (int i = 0; i < definitions.size(); i++) {
			arrHeight[i] = definitions.get(i).getHeight();
		}
		return Collections.max(Arrays.asList(arrHeight));
	}
}
