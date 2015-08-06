package org.jboss.pnc.localbuilder.LocalBuild;

import org.apache.commons.io.FileUtils;
import org.jboss.pnc.localbuilder.buildjob.BashScriptGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by aabulawi on 09/07/15.
 */
public class BashScriptGeneratorTest {

    private String scriptsDir = System.getProperty("user.dir") + "/src/test/resources/test_scripts/";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void createValidScript() throws IOException {
        BashScriptGenerator bashScriptGenerator = new BashScriptGenerator();
        String pathToFile = temporaryFolder.getRoot().getAbsolutePath() + File.separator + "test.sh";
        File testFile = bashScriptGenerator.generateExecutableScript(pathToFile, "mvn clean install -DskipTests");
        assertTrue(FileUtils.contentEquals(testFile, new File(scriptsDir +File.separator+"validBash.sh")));
    }

    @Test
    public void writeToExistingFile() throws IOException {
        BashScriptGenerator bashScriptGenerator = new BashScriptGenerator();
        String pathToFile = temporaryFolder.getRoot().getAbsolutePath() + File.separator + "test.sh";
        File existingFile = new File(pathToFile);
        existingFile.createNewFile();
        File testFile = bashScriptGenerator.generateExecutableScript(pathToFile, "mvn clean install -DskipTests");
        assertTrue(FileUtils.contentEquals(testFile, new File(scriptsDir +File.separator+"validBash.sh")));
    }

    @Test
    public void isFileExecutable() throws IOException {
        BashScriptGenerator bashScriptGenerator = new BashScriptGenerator();
        String pathToFile = temporaryFolder.getRoot().getAbsolutePath() + File.separator + "test.sh";
        File testFile = bashScriptGenerator.generateExecutableScript(pathToFile, "mvn clean install -DskipTests");
        assertTrue(testFile.canExecute());
    }

}
