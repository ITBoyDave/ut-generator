package cn.bigboydave.ut.generotor.guess;

import cn.bigboydave.ut.generotor.context.ContextBody;
import cn.bigboydave.ut.generotor.data.ClassData;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.data.SupportTemplate;
import cn.bigboydave.ut.generotor.data.TemplateData;
import cn.bigboydave.ut.generotor.interfaces.Guess;
import cn.bigboydave.ut.generotor.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/27/19 2:23 AM
 * @srcFile ServiceGuessImpl.java
 */
public class ServiceGuessImpl implements Guess {

    @Override
    public String guessClazzNamespace(ClassData classData, CommandArgs commandArgs) {
        TemplateData templateData = ContextBody.getTemplateData();
        Set<String> imports = templateData.getImports();
        StringBuilder sb = new StringBuilder();
        imports.add(IMPORT.concat("org.junit.runner.RunWith;\n"));
        imports.add(IMPORT.concat("org.mockito.runners.MockitoJUnitRunner;\n"));
        sb.append("@RunWith(MockitoJUnitRunner.class)").append("\n");
        String simpleName = classData.getClazz().getSimpleName();
        sb.append("public class ").append(simpleName).append("Test {\n");
        return sb.toString();
    }

    @Override
    public String guessInnerClass(ClassData classData, CommandArgs commandArgs) {
        return null;
    }

    @Override
    public String guessField(ClassData classData, CommandArgs commandArgs) {
        Class<?> clazz = classData.getClazz();
        String simpleName = clazz.getSimpleName();
        String varName = simpleName.replaceFirst(String.valueOf(simpleName.charAt(0)), String.valueOf(Character.toLowerCase(simpleName.charAt(0))));
        StringBuilder sb = new StringBuilder();
        if (!ReflectUtils.isStaticAllMethod(classData.getMethodData())) {
            sb.append("    private ").append(simpleName).append(" ").append(varName).append(" = new ").append(simpleName).append("();\n");
        }
        List<Field> fields = classData.getFields();
        TemplateData templateData = ContextBody.getTemplateData();
        Set<String> imports = templateData.getImports();
        if (null != fields && !fields.isEmpty() && SupportTemplate.SERVICE.equals(commandArgs.getTemplate())) {
            List<Field> fieldList = fields.stream().filter(
                    field -> !Modifier.isFinal(field.getModifiers()) && !ReflectUtils.assertFieldAutoInit(field, clazz))
                    .collect(
                            Collectors.toList());
            if (!fieldList.isEmpty()) {
                imports.add(IMPORT.concat("org.mockito.Mock;\n"));
                for (Field field : fieldList) {
                    Class<?> fieldType = field.getType();
                    imports.add(IMPORT.concat(fieldType.getCanonicalName()).concat(";\n"));
                    String fieldName = field.getName();
                    sb.append("    \n    @Mock\n").append("    private ").append(fieldType.getSimpleName()).append(" ")
                            .append(fieldName).append(";\n");
                }
            }
        }
        return sb.toString();
    }

}
