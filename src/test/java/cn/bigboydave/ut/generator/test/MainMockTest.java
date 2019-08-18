package cn.bigboydave.ut.generator.test;

import cn.bigboydave.ut.generotor.Main;
import cn.bigboydave.ut.generotor.api.JavaApi;
import cn.bigboydave.ut.generotor.data.CommandArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/19/19 12:35 AM
 * @srcFile MainMockTest.java
 */
@RunWith(MockitoJUnitRunner.class)
public class MainMockTest {

    @Test
    public void testMain() {
        String[] args = new String[]{
                "--template=dao", "--clazzName=cn.bigboydave.ut.generator.test.BigBoyDaveRepository", "--write=false", "--basePackages=cn.bigboydave.ut.generator.test"
        };
        Main.main(args);
    }

    @Test
    public void testGenerateDao() {

        CommandArgs commandArgs = new CommandArgs();
        commandArgs.setClazzName(BigBoyDaveRepository.class.getName());
        commandArgs.setTemplate("dao");
        commandArgs.setConfigClazz("DaoPureApplication");
        commandArgs.setConfigClazzImport("cn.bigboydave.DaoPureApplication");
        commandArgs.setWrite(false);

        JavaApi.generate(commandArgs);

    }

    @Test
    public void testGenerateService() {

        CommandArgs commandArgs = new CommandArgs();
        commandArgs.setClazzName(BigBoyDaveService.class.getName());
        commandArgs.setTemplate("service");
        commandArgs.setWrite(false);

        JavaApi.generate(commandArgs);
    }
}
