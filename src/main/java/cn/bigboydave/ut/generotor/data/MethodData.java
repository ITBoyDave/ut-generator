package cn.bigboydave.ut.generotor.data;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 9:48 AM
 * @srcFile MethodData.java
 */
public class MethodData implements Serializable {

    private Method method;
    private boolean signature;

    public MethodData (Method method,boolean signature) {
        this.method = method;
        this.signature = signature;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean isSignature() {
        return signature;
    }

    public void setSignature(boolean signature) {
        this.signature = signature;
    }
}
