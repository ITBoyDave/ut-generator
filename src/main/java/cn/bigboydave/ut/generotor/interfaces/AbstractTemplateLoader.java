package cn.bigboydave.ut.generotor.interfaces;

import cn.bigboydave.ut.generotor.data.CommandArgs;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 7:57 PM
 * @srcFile AbstractTemplateLoader.java
 */
public class AbstractTemplateLoader implements TemplateLoader {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Template loadTemplate(String template) {
        String json = "{\"template\":\"".concat(template).concat("\"}");
        try {
            return objectMapper.readValue(json, Template.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Template loadTemplate(CommandArgs commandArgs) {
        return this.objectMapper.convertValue(commandArgs, Template.class);
    }

}
