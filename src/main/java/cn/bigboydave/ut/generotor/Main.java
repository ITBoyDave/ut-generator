package cn.bigboydave.ut.generotor;

import cn.bigboydave.ut.generotor.api.JavaApi;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.parser.CommandArgsParser;
import org.apache.commons.lang3.StringUtils;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/17/19 10:18 PM
 * @srcFile Main.java
 */
public class Main {

    public static void main(String[] args) {
        CommandArgs commandArgs = new CommandArgsParser().parse(args);
        checkCommandArgs(commandArgs);
        JavaApi.generate(commandArgs);
    }

    private static void checkCommandArgs(CommandArgs commandArgs) {
        if (StringUtils.isEmpty(commandArgs.getTemplate())) {
            throw new IllegalArgumentException("template is null or empty!");
        }
        if (StringUtils.isEmpty(commandArgs.getClazzName())) {
            throw new IllegalArgumentException("clazzName is null or empty!");
        }

    }

}
