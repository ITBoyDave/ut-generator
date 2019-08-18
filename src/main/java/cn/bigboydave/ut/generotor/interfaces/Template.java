package cn.bigboydave.ut.generotor.interfaces;

import cn.bigboydave.ut.generotor.context.ContextBody;
import cn.bigboydave.ut.generotor.data.ClassData;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.data.TemplateData;
import cn.bigboydave.ut.generotor.guess.GuessLoader;
import cn.bigboydave.ut.generotor.template.ControllerTemplateImpl;
import cn.bigboydave.ut.generotor.template.DaoTemplateImpl;
import cn.bigboydave.ut.generotor.template.ServiceTemplateImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 7:46 PM
 * @srcFile Template.java
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME, visible = true, property = "template")
@JsonSubTypes({
        @Type(value = DaoTemplateImpl.class, name = "dao"),
        @Type(value = ServiceTemplateImpl.class, name = "service"),
        @Type(value = ControllerTemplateImpl.class, name = "controller")
})
public interface Template {

    GuessLoader GUESS_LOADER = new GuessLoader();

    /**
     * render
     *
     * @param classData
     * @param commandArgs
     * @return
     */
    default String render(ClassData classData, CommandArgs commandArgs) {
        Guess guess = GUESS_LOADER.loadGuess(commandArgs);
        TemplateData templateData = ContextBody.getTemplateData();
        templateData.setPkg(guess.guessPackage(classData, commandArgs));
        List<String> importList = guess.guessImport(classData, commandArgs);
        if (null != importList && !importList.isEmpty()) {
            templateData.getImports().addAll(importList);
        }
        templateData.setSignature(guess.guessSignature(commandArgs));
        templateData.setClazzNamespace(guess.guessClazzNamespace(classData, commandArgs));
        if (commandArgs.getInnerMode()) {
            templateData.setInnerClass(guess.guessInnerClass(classData, commandArgs));
        }
        templateData.setField(guess.guessField(classData, commandArgs));
        templateData.setTestMethod(guess.guessTestMethods(classData, commandArgs));
        return templateData.render();
    }

}
