package cn.bigboydave.ut.generotor.exception;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 6:10 PM
 * @srcFile ArgsErrorException.java
 */
public class ArgsErrorException extends RuntimeException {
    public ArgsErrorException() {
        super();
    }

    public ArgsErrorException(String message) {
        super(message);
    }

    public ArgsErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgsErrorException(Throwable cause) {
        super(cause);
    }

    protected ArgsErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
