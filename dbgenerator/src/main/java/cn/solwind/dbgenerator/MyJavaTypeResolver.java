package cn.solwind.dbgenerator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis generator 数据库类型映射修改
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl  {

    public MyJavaTypeResolver() {
        super();
        // SMALLINT -> Integer
        super.typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Integer.class.getName())));
        // TINYINT -> Integer
        super.typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));


    }
}
