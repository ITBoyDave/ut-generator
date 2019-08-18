package cn.bigboydave.ut.generotor.context;

import cn.bigboydave.ut.generotor.data.TemplateData;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/27/19 10:06 PM
 * @srcFile ContextBody.java
 */
public class ContextBody {

    private static final ThreadLocal<TemplateData> THREAD_LOCAL = ThreadLocal.withInitial(() -> new TemplateData());

    public static TemplateData getTemplateData() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
