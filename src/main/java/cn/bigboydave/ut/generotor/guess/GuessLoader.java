package cn.bigboydave.ut.generotor.guess;

import cn.bigboydave.ut.generotor.data.CommandArgs;
import cn.bigboydave.ut.generotor.interfaces.Guess;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/19/19 12:12 AM
 * @srcFile GuessLoader.java
 */
public class GuessLoader {

    ObjectMapper objectMapper = new ObjectMapper();

    public Guess loadGuess(CommandArgs commandArgs) {
        return this.objectMapper.convertValue(commandArgs, Guess.class);
    }
}
