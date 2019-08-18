package cn.bigboydave.ut.generotor.interfaces;

import cn.bigboydave.ut.generotor.data.CommandArgs;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 7:44 PM
 * @srcFile TemplateLoader.java
 */
public interface TemplateLoader {

    /**
     * loadTemplate
     *
     * @param template
     * @return
     */
    Template loadTemplate(String template);

    /**
     * loadTemplate
     *
     * @param commandArgs
     * @return
     */
    Template loadTemplate(CommandArgs commandArgs);
}
