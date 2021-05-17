package cn.solwind.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Bean操作工具类
 *
 * @author zln
 */
public class BeanOperateUtil {

    /**
     * 获取所有字段为null的属性名
     * 用于BeanUtils.copyProperties()拷贝属性时，忽略空值
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = new HashSet<String>();
        Field[] field = source.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            {     //遍历所有属性
                String name = field[i].getName();    //获取属性的名字
                String proName = name;
                //将属性的首字符大写，方便构造get，set方法
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = field[i].getGenericType().toString();    //获取属性的类型
                try {
                    Method mGet = source.getClass().getMethod("get" + name);
                    if (mGet.invoke(source) == null) {
                        emptyNames.add(proName);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 　　* 将bean中空字符串设置为null
     * 　　* @param [bean]
     * 　　* @return void
     *
     */
    public static <T> void emptyStrToNull(T bean) {
        Field[] field = bean.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            {     //遍历所有属性
                String name = field[i].getName();    //获取属性的名字
                //将属性的首字符大写，方便构造get，set方法
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = field[i].getGenericType().toString();    //获取属性的类型
                if (type.equals("class java.lang.String")) {   //如果type是字符串类型
                    try {
                        Method mGet = bean.getClass().getMethod("get" + name);
                        String value = (String) mGet.invoke(bean);    //调用getter方法获取属性值
                        if (value != null && "".equals(value.trim())) {
                            Method mSet = bean.getClass().getMethod("set" + name, new Class[]{String.class});
                            mSet.invoke(bean, new Object[]{null});
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
