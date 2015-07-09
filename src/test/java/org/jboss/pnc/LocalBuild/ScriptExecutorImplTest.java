package org.jboss.pnc.LocalBuild;

import org.apache.commons.io.FileUtils;
import org.jboss.pnc.buildjob.ScriptExecutorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by aabulawi on 09/07/15.
 */
public class ScriptExecutorImplTest {

    //TODO: Think of way to make this platform independent. Probably need to make batch script generator and use factory or something

    private String scriptsDir = System.getProperty("user.dir") + "/src/test/resources/test_scripts/";
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void runValidScript() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"helloWorld.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
    }

    @Test
    public void verifyCorrectLogsForSuccess() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"helloWorld.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
        assertEquals("Hello World", scriptExecutor.getOutput());
    }

    @Test
    public void verifyCorrectResultForSuccess() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"helloWorld.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
        assertEquals(0, scriptExecutor.getResult());
    }

    @Test
    public void verifyCorrectLogFileForSuccess() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"helloWorld.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
        assertTrue(FileUtils.contentEquals(testlog, new File(scriptsDir+"helloWorld.txt")));
    }

    @Test
    public void runInvalidScript() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"errorScript.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
    }

    @Test
    public void verifyCorrectLogFileForInvalid() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"errorScript.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
        assertEquals(testExecutable.getPath() + ": line 2: qwewqe123!sds: command not found", scriptExecutor.getOutput());
    }

    @Test
    public void verifyCorrectResultForInvalid() throws IOException, InterruptedException {
        File testlog = temporaryFolder.newFile();
        File testExecutable = new File(scriptsDir+"errorScript.sh");
        testExecutable.setExecutable(true);
        ScriptExecutorImpl scriptExecutor = new ScriptExecutorImpl(testExecutable, testlog);
        scriptExecutor.executeScript();
        assertTrue(scriptExecutor.getResult() != 0);
    }

}
