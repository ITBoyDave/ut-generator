package cn.bigboydave.ut.generotor.interfaces;

import cn.bigboydave.ut.generotor.context.ContextBody;
import cn.bigboydave.ut.generotor.data.*;
import cn.bigboydave.ut.generotor.guess.DaoGuessImpl;
import cn.bigboydave.ut.generotor.guess.ServiceGuessImpl;
import cn.bigboydave.ut.generotor.utils.ReflectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/17/19 11:40 PM
 * @srcFile Guess.java
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME, property = "template", visible = true)
@JsonSubTypes(value = {
        @Type(name = "dao", value = DaoGuessImpl.class),
        @Type(name = "service", value = ServiceGuessImpl.class)
})
public interface Guess {

    String CLASS_SUFFIX = ".class";
    String PACKAGE = "package ";
    String IMPORT = "import ";
    String SEMICOLON = ";";

    /**
     * guessPackage
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    default String guessPackage(ClassData classData, CommandArgs commandArgs) {
        String prefixPackage = commandArgs.getPrefixPackage();
        if (StringUtils.isEmpty(prefixPackage)) {
            return PACKAGE.concat(classData.getClazz().getPackage().getName()).concat(";\n");
        } else {
            return PACKAGE.concat(prefixPackage).concat(";\n");
        }
    }

    /**
     * guessSignature
     *
     * @param commandArgs
     * @return
     */
    default String guessSignature(CommandArgs commandArgs) {
        StringBuilder sb = new StringBuilder("/**\n");
        String author = commandArgs.getAuthor();
        if (StringUtils.isEmpty(author)) {
            sb.append(" * @author ").append(System.getenv("user")).append("\n");
        } else {
            sb.append(" * @author ").append(author).append("\n");
        }
        String description = commandArgs.getDescription();
        sb.append(" * @description : ").append(description).append("\n");
        String email = commandArgs.getEmail();
        sb.append(" * @email ").append(email).append("\n");
        String dateFormatPattern = commandArgs.getDateFormatPattern();
        DateFormat dateFormat;
        if (StringUtils.isEmpty(dateFormatPattern)) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
            dateFormat = new SimpleDateFormat(dateFormatPattern);
        }
        sb.append(" * @date ").append(dateFormat.format(new Date())).append("\n");
        return sb.append(" */\n").toString();
    }

    /**
     * guessImport
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    default List<String> guessImport(ClassData classData, CommandArgs commandArgs) {
        return Lists.newArrayList();
    }

    /**
     * guessClazzNamespace
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    String guessClazzNamespace(ClassData classData, CommandArgs commandArgs);

    /**
     * guessInnerClass
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    default String guessInnerClass(ClassData classData, CommandArgs commandArgs) {
        return null;
    }

    /**
     * guessField
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    String guessField(ClassData classData, CommandArgs commandArgs);

    /**
     * guessTestMethods
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    default String guessTestMethods(ClassData classData, CommandArgs commandArgs) {
        Class<?> clazz = classData.getClazz();
        String simpleName = clazz.getSimpleName();
        List<MethodData> methodData = classData.getMethodData();
        TemplateData templateData = ContextBody.getTemplateData();
        Set<String> imports = templateData.getImports();
        String template = commandArgs.getTemplate();
        StringBuilder sb = new StringBuilder();
        List<Field> fields = classData.getFields();
        String varName = simpleName.replaceFirst(String.valueOf(simpleName.charAt(0)), String.valueOf(Character.toLowerCase(simpleName.charAt(0))));
        if (SupportTemplate.SERVICE.equals(template) && fields != null && !fields.isEmpty()) {
            List<Field> fieldList = fields.stream().filter(field -> {
                int fieldModifiers = field.getModifiers();
                return !Modifier.isFinal(fieldModifiers) && !ReflectUtils.assertFieldAutoInit(field, clazz);
            }).collect(Collectors.toList());
            if (null != fieldList && !fieldList.isEmpty()) {
                imports.add(IMPORT.concat("org.junit.Before;\n"));
                imports.add(IMPORT.concat("org.mockito.internal.util.reflection.Whitebox;\n"));
                sb.append("    @Before\n").append("    public void setup() {\n");
                for (Field field : fieldList) {
                    String fieldName = field.getName();
                    sb.append("        Whitebox.setInternalState(").append(varName).append(", \"").append(fieldName).append("\", ").append(fieldName).append(");\n");
                }
                sb.append("    }\n\n");
            }
        }
        int mi = 0;
        Set<String> ms = new HashSet<>();
        for (MethodData md : methodData) {
            if (md.isSignature()) {
                Method method = md.getMethod();
                String methodName = method.getName();
                Parameter[] parameters = method.getParameters();
                sb.append("    /**\n     * {@linkplain ").append(simpleName).append("#").append(methodName).append("(");
                for (int index = 0; index < parameters.length; ) {
                    index++;
                    Parameter parameter = parameters[index - 1];
                    Class c = parameter.getType();
                    if (!ReflectUtils.assertBaseDataType(c)) {
                        imports.add(IMPORT.concat(c.getCanonicalName()).concat(";\n"));
                    }
                    if (index == parameters.length) {
                        sb.append(c.getSimpleName());
                    } else {
                        sb.append(c.getSimpleName()).append(", ");
                    }
                }
                sb.append(")}\n     */\n");
                String testMethodName = "test" + String.valueOf(Character.toUpperCase(methodName.charAt(0))).concat(methodName.substring(1));
                if (ms.contains(testMethodName)) {
                    mi++;
                    testMethodName = testMethodName + mi;
                }
                ms.add(testMethodName);
                sb.append("    @Test\n    ");
                if (SupportTemplate.DAO.equals(template)) {
                    sb.append("@Rollback\n    ");
                }
                sb.append("public void ").append(testMethodName).append("() {\n        // TODO\n    \n    ").append("}\n\n");
            }
        }
        if (!methodData.isEmpty()) {
            imports.add(IMPORT.concat("org.junit.Test;\n"));
        }
        if (SupportTemplate.DAO.equals(template) && !methodData.isEmpty()) {
            imports.add(IMPORT.concat("org.springframework.test.annotation.Rollback;\n"));
        }
        return sb.toString();
    }

}
