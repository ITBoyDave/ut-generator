package cn.bigboydave.ut.generotor.parser;

import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.exception.ArgsErrorException;
import cn.bigboydave.ut.generotor.interfaces.Parser;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 4:47 PM
 * @srcFile CommandArgsParser.java
 */
public class CommandArgsParser implements Parser<String[], CommandArgs> {

    public static final String PREFIX = "--";

    public static final String KV_SPLIT = "=";

    @Override
    public boolean isEffect(String[] args) {
        return args != null && args.length > 0;
    }

    @Override
    public CommandArgs parse(String[] args) {
        return this.parse(args, new CommandArgs());
    }

    @Override
    public CommandArgs parse(String[] args, CommandArgs commandArgs) {
        if (commandArgs == null) {
            commandArgs = new CommandArgs();
        }
        if (args == null) {
            return commandArgs;
        }
        for (String arg : args) {
            setArg(arg, commandArgs);
        }
        return commandArgs;
    }

    private CommandArgs setArg(String arg, CommandArgs commandArgs) {
        if (StringUtils.isEmpty(arg)) {
            return commandArgs;
        }
        Class<CommandArgs> commandArgsClass = CommandArgs.class;
        String keyValue = arg;
        if (arg.startsWith(PREFIX)) {
            keyValue = arg.replace(PREFIX, "");
        }
        if (!keyValue.contains(KV_SPLIT)) {
            throw new ArgsErrorException(String.format("error arg: %s", keyValue));
        }
        String[] kvArr = keyValue.split(KV_SPLIT);
        String fieldName = kvArr[0];
        String fieldValue = kvArr[1];
        String methodName = "set".concat(String.valueOf(Character.toUpperCase(fieldName.charAt(0)))).concat(fieldName.substring(1));
        try {
            Field field = commandArgsClass.getDeclaredField(fieldName);
            Method method = commandArgsClass.getMethod(methodName, field.getType());
            if (method.getParameterCount() == 1) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object cast = this.castValueToType(fieldValue, parameterTypes[0]);
                method.invoke(commandArgs, cast);
            }
        } catch (Exception ex) {
            throw new ArgsErrorException(String.format("not found field = %s or not found method = %s,error arg = %s", fieldName, methodName, arg));
        }
        return commandArgs;
    }

    private <T> T castValueToType(String fieldValue, Class<T> targetType) {
        if (targetType.equals(String.class)) {
            return (T) fieldValue;
        } else if (targetType.equals(Integer.class) || targetType.equals(int.class)) {
            return (T) Integer.valueOf(fieldValue);
        } else if (targetType.equals(Long.class) || targetType.equals(long.class)) {
            return (T) Long.valueOf(fieldValue);
        } else if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) {
            return (T) Boolean.valueOf(fieldValue);
        } else if (targetType.equals(Double.class) || targetType.equals(double.class)) {
            return (T) Double.valueOf(fieldValue);
        } else if (targetType.equals(Byte.class) || targetType.equals(byte.class)) {
            return (T) Byte.valueOf(fieldValue);
        } else if (targetType.equals(Short.class) || targetType.equals(short.class)) {
            return (T) Short.valueOf(fieldValue);
        } else if (targetType.equals(BigDecimal.class)) {
            return (T) new BigDecimal(fieldValue);
        } else if (targetType.equals(BigInteger.class)) {
            return (T) new BigInteger(fieldValue);
        } else {
            return targetType.cast(fieldValue);
        }
    }

}
