package cn.bigboydave.ut.generotor.guess;

import cn.bigboydave.ut.generotor.context.ContextBody;
import cn.bigboydave.ut.generotor.data.ClassData;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.data.TemplateData;
import cn.bigboydave.ut.generotor.interfaces.Guess;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 9:53 PM
 * @srcFile DaoGuessImpl.java
 */
public class DaoGuessImpl implements Guess {

    private List<String> initBaseImport() {
        List<String> baseImports = Lists.newArrayList();
        baseImports.add(IMPORT.concat("org.junit.FixMethodOrder;\n"));
        baseImports.add(IMPORT.concat("org.junit.runner.RunWith;\n"));
        baseImports.add(IMPORT.concat("org.junit.runners.MethodSorters;\n"));
        baseImports.add(IMPORT.concat("org.mybatis.spring.boot.test.autoconfigure.MybatisTest;\n"));
        baseImports.add(IMPORT.concat("org.springframework.beans.factory.annotation.Autowired;\n"));
        baseImports.add(IMPORT.concat("org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;\n"));
        baseImports.add(IMPORT.concat("org.springframework.context.annotation.Import;\n"));
        baseImports.add(IMPORT.concat("org.springframework.test.context.ActiveProfiles;\n"));
        baseImports.add(IMPORT.concat("org.springframework.test.context.ContextConfiguration;\n"));
        baseImports.add(IMPORT.concat("org.springframework.test.context.junit4.SpringRunner;\n"));
        return baseImports;
    }

    @Override
    public List<String> guessImport(ClassData classData, CommandArgs commandArgs) {
        final List<String> imports = initBaseImport();
        String configClazzImport = commandArgs.getConfigClazzImport();
        if (!StringUtils.isBlank(configClazzImport)) {
            configClazzImport = StringUtils.strip(configClazzImport);
            configClazzImport = StringUtils.endsWith(configClazzImport, SEMICOLON) ? configClazzImport : configClazzImport.concat(SEMICOLON);
            if (configClazzImport.startsWith(IMPORT)) {
                imports.add(configClazzImport.concat("\n"));
            } else {
                imports.add(IMPORT.concat(configClazzImport).concat("\n"));
            }
        }
        return imports;
    }

    @Override
    public String guessClazzNamespace(ClassData classData, CommandArgs commandArgs) {
        String simpleName = classData.getClazz().getSimpleName();
        String configClazz = commandArgs.getConfigClazz();
        if (StringUtils.isBlank(configClazz)) {
            configClazz = simpleName.concat("Application".concat(CLASS_SUFFIX));
        } else if (!configClazz.endsWith(CLASS_SUFFIX)) {
            configClazz = configClazz.concat(CLASS_SUFFIX);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("@RunWith(SpringRunner.class)").append("\n");
        sb.append("@ContextConfiguration(classes = ").append(configClazz).append(")\n");
        sb.append("@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)\n");
        sb.append("@MybatisTest\n");
        sb.append("@Import(").append("value = {").append(simpleName.concat(CLASS_SUFFIX)).append("})\n");
        sb.append("@ActiveProfiles(\"dao\")\n");
        sb.append("@FixMethodOrder(MethodSorters.NAME_ASCENDING)\n");
        sb.append("public class ").append(simpleName).append("Test {\n");
        return sb.toString();
    }

    @Override
    public String guessInnerClass(ClassData classData, CommandArgs commandArgs) {
        Class<?> clazz = classData.getClazz();
        String basePackages = commandArgs.getBasePackages();
        if (basePackages == null) {
            basePackages = "{}";
        } else {
            StringBuilder sb = new StringBuilder("{");
            String[] split = basePackages.split(",");
            for (String pkg : split) {
                sb.append("\"").append(pkg).append("\",");
            }
            basePackages = sb.deleteCharAt(sb.length() - 1).toString();
        }

        StringBuilder sb = new StringBuilder();
        String innerClazzName = clazz.getSimpleName().concat("Application");
        sb.append("    @MapperScan(basePackages = ").append(basePackages).append(")\n");
        sb.append("    @SpringBootApplication\n");
        sb.append("    @Profile(\"dao\")\n");
        sb.append("    public static class ").append(innerClazzName).append(" {\n\n");
        sb.append("        public static void main(String[] args) {\n");
        sb.append("            SpringApplication.run(").append(innerClazzName).append(".class);\n");
        sb.append("        }\n\n");
        sb.append("    }\n");
        TemplateData templateData = ContextBody.getTemplateData();
        Set<String> imports = templateData.getImports();
        imports.add(IMPORT.concat("org.mybatis.spring.annotation.MapperScan;\n"));
        imports.add(IMPORT.concat("org.springframework.boot.autoconfigure.SpringBootApplication;\n"));
        imports.add(IMPORT.concat("org.springframework.context.annotation.Profile;\n"));
        return sb.toString();
    }

    @Override
    public String guessField(ClassData classData, CommandArgs commandArgs) {
        Class<?> clazz = classData.getClazz();
        String simpleName = clazz.getSimpleName();
        String varName = simpleName.replaceFirst(String.valueOf(simpleName.charAt(0)), String.valueOf(Character.toLowerCase(simpleName.charAt(0))));
        StringBuilder sb = new StringBuilder();
        sb.append("    @Autowired\n").append("    private ").append(simpleName).append(" ").append(varName).append(";\n");
        return sb.toString();
    }
}
