package cn.solwind.dbgenerator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class TkMapperPlugin extends PluginAdapter{
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 修改Mapper生成规则为tk.mybatis方式
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 清除原有import和方法
        interfaze.getMethods().clear();
        interfaze.getImportedTypes().clear();

        // 增加实体类import
        FullyQualifiedJavaType typeEntity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        interfaze.addImportedType(typeEntity);

        // 继承tk.Mapper的基类
        FullyQualifiedJavaType parentClass = new FullyQualifiedJavaType("Mapper<" + introspectedTable.getBaseRecordType() + ">");
        interfaze.addSuperInterface(parentClass);

        FullyQualifiedJavaType impMapper = new FullyQualifiedJavaType("tk.mybatis.mapper.common.Mapper");
        interfaze.addImportedType(impMapper);

        // 增加注解
//        interfaze.getAnnotations().clear();
//        interfaze.addAnnotation("@Component(\"" + introspectedTable.getFullyQualifiedTable() + "\")");
        return true;
    }

    /**
     * 修改Entity增加tk.Mapper所需注解
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 如果属性名为id，则自动增加ID注解
        if (field.getName().equalsIgnoreCase("id")) {
            topLevelClass.addImportedType("javax.persistence.Id");
            field.addAnnotation("@Id");

            // 增加GeneratedKeys注解
            topLevelClass.addImportedType("tk.mybatis.mapper.annotation.KeySql");
            field.addAnnotation("@KeySql(useGeneratedKeys = true)");
        }

        // 如果属性名为version,则自动增加乐观锁注解
        if(field.getName().equalsIgnoreCase("version")) {
            topLevelClass.addImportedType("tk.mybatis.mapper.annotation.Version");
            field.addAnnotation("@Version");
        }

        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return super.contextGenerateAdditionalJavaFiles();
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }
}
