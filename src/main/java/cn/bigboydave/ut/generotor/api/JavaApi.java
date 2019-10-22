package cn.bigboydave.ut.generotor.api;

import cn.bigboydave.ut.generotor.context.ContextBody;
import cn.bigboydave.ut.generotor.data.ClassData;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.exception.NotTargetClassException;
import cn.bigboydave.ut.generotor.interfaces.AbstractTemplateLoader;
import cn.bigboydave.ut.generotor.interfaces.Parser;
import cn.bigboydave.ut.generotor.interfaces.Template;
import cn.bigboydave.ut.generotor.interfaces.TemplateLoader;
import cn.bigboydave.ut.generotor.parser.ClassParser;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 1:02 PM
 * @srcFile JavaApi.java
 */
public class JavaApi {

    private static final Parser<Class<?>, ClassData> CLASS_PARSER = new ClassParser();
    private static final TemplateLoader TEMPLATE_LOADER = new AbstractTemplateLoader();

    public static String generate(Class<?> clazz, CommandArgs commandArgs) {
        try {
            Template template = TEMPLATE_LOADER.loadTemplate(commandArgs);
            ClassData classData = CLASS_PARSER.parse(clazz);
            fixFilePath(classData, commandArgs);
            final String render = template.render(classData, commandArgs);
            writeToOutputStream(render, commandArgs);
            if (commandArgs.isPrint()) {
                System.out.println("--------------------------------------------ut-generator-------------------------------------------------");
                System.out.println(render);
                System.out.println("--------------------------------------------ut-generator-------------------------------------------------");
            }
            return render;
        } finally {
            ContextBody.remove();
        }

    }

    private static String writeToOutputStream(String render, CommandArgs commandArgs) {
        if (commandArgs.isWrite()) {
            FileOutputStream fileOutputStream = null;
            try {
                String outputFilePath = commandArgs.getOutputFilePath();
                String dirPath = outputFilePath.substring(0,outputFilePath.lastIndexOf(DIRECT_SYMBOL));
                File fileDir = new File(dirPath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                fileOutputStream = new FileOutputStream(outputFilePath);
                fileOutputStream.write(render.getBytes(Charset.forName("utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return render;
    }

    private static final String ROOT = "/";
    private static final String SOURCE_PATH = "src/test/java/";
    private static final String DIRECT_SYMBOL = "/";

    private static void fixFilePath(ClassData classData, CommandArgs commandArgs) {
        String outputFilePath = commandArgs.getOutputFilePath();
        if (!StringUtils.isEmpty(outputFilePath)) {
            if (!outputFilePath.contains(DIRECT_SYMBOL)) {
                commandArgs.setOutputFilePath(SOURCE_PATH.concat(classData.getClazz().getPackage().getName().replace(".", DIRECT_SYMBOL).concat(DIRECT_SYMBOL).concat(outputFilePath)));
            } else if (!outputFilePath.startsWith(ROOT) && !outputFilePath.startsWith(SOURCE_PATH)) {
                commandArgs.setOutputFilePath(SOURCE_PATH.concat(outputFilePath));
            }
        } else {
            commandArgs.setOutputFilePath(SOURCE_PATH.concat(classData.getClazz().getPackage().getName().replace(".", DIRECT_SYMBOL).concat(DIRECT_SYMBOL).concat(classData.getClazz().getSimpleName().concat("Test.java"))));
        }
    }

    public static String generate(CommandArgs commandArgs) {
        String clazzName = commandArgs.getClazzName();
        if (StringUtils.isEmpty(clazzName)) {
            throw new NotTargetClassException();
        }
        try {
            Class<?> clazz = Class.forName(clazzName);
            return generate(clazz, commandArgs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NotTargetClassException(String.format("clazz = %s not found", clazzName));
        }
    }
}
