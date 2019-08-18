package cn.bigboydave.ut.generotor.exception;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 7:19 PM
 * @srcFile NotTargetClassException.java
 */
public class NotTargetClassException extends RuntimeException {
    public NotTargetClassException() {
        super();
    }

    public NotTargetClassException(String message) {
        super(message);
    }

    public NotTargetClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotTargetClassException(Throwable cause) {
        super(cause);
    }

    protected NotTargetClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
