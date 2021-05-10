package cn.solwind.excel.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import cn.solwind.excel.handler.ExcelHandler.FieldType;

/**
* @author Zhouluning
* @version 创建时间：2019-05-09
*
* 类说明
*/

public class CommonTest {

	@Test
	public void testRef() {
		Object sampleData = new SampleData();
		List<LineData> listdata = new ArrayList<LineData>();
		for (long i = 0; i < 100; i++) {
			LineData lineData = new LineData();
			
			lineData.setId(i);
			lineData.setName("测试" + (new Date()).getTime());
			lineData.setAge((int)(i+1)/40);
			
			listdata.add(lineData);
		}
//		sampleData.setRowData(listdata);
		
		Class<?> clazz = sampleData.getClass();
		System.out.println(clazz);
		
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		
		// 遍历属性
		try {
			for (Field field : fields) {
				System.out.println(field.getName());
				System.out.println(getterOrSetter(clazz,field.getName(),FieldType.GETTER).invoke(sampleData));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected Method getterOrSetter(Class<?> clazz, String fieldName, FieldType methodType)
            throws IntrospectionException {

        if (null == fieldName || "".equals(fieldName))
            return null;

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor prop : props) {
            if (fieldName.equals(prop.getName())) {
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

}
