package org.jboss.pnc.localbuilder.buildjob;

import org.apache.maven.scm.ScmException;
import org.jboss.pnc.localbuilder.exception.LocalBuildProcessException;
import org.jboss.pnc.localbuilder.exception.OSNotSupportedException;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;
import org.jboss.pnc.localbuilder.sourcemanager.SCMRepositoryType;
import org.jboss.pnc.localbuilder.sourcemanager.ScmRetriever;
import org.jboss.pnc.spi.builddriver.BuildDriverStatus;

import java.io.*;
import java.util.function.Consumer;

/**
 * Created by aabulawi on 24/06/15.
 */
public class LocalBuildJob {

    private String scmUrl;
    private String outputDir;
    private final String buildScriptName = "pnc-build-script.sh";
    private final String buildLogName = "mvn.log";
    private String buildScriptContents;
    private String revision;
    private String buildLog = "";
    private Environment environment;

    public LocalBuildJob(BuildConfiguration configuration, String outputDir) {
        this.scmUrl = configuration.getScmRepoURL();
        this.revision = configuration.getScmRevision();
        this.outputDir = outputDir + File.separator + configuration.getName();
        this.buildScriptContents = configuration.getBuildScript();
        this.environment = configuration.getEnvironment();
    }

    public void build(Consumer<BuildDriverStatus> onMonitorComplete,
                      Consumer<Exception> onMonitorError) {

        ScriptExecutor executor = null;
        try {
            ScmRetriever scmRetriever = new ScmRetriever(SCMRepositoryType.GIT);
            scmRetriever.cloneRepository(scmUrl, revision, outputDir);

            ScriptGenerator scriptGenerator = ScriptGeneratorFactory.createScriptGenerator(environment.getOperationalSystem());
            File buildScriptFile = scriptGenerator.generateExecutableScript(outputDir + File.separator + buildScriptName, buildScriptContents);

            File mvnlog = new File(outputDir + File.separator + buildLogName);
            mvnlog.createNewFile();

            executor = new ScriptExecutorImpl(buildScriptFile, mvnlog);
            executor.executeScript();
            buildLog = executor.getOutput();

            onMonitorComplete.accept(executor.getResult() == 0 ? BuildDriverStatus.SUCCESS : BuildDriverStatus.FAILED);

        } catch (LocalBuildProcessException e) {
            buildLog = executor.getOutput();
            e.printStackTrace();
            onMonitorError.accept(e);
        } catch (ScmException|IOException|OSNotSupportedException e) {
            e.printStackTrace();
            onMonitorError.accept(e);
        }
    }

    public String getBuildLog(){
        return buildLog;
    }
}
