package org.jboss.pnc.buildjob;

import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.source_manager.SCMRepositoryType;
import org.jboss.pnc.source_manager.ScmRetriever;
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

    public LocalBuildJob(BuildConfiguration configuration, String outputDir) {
        this.scmUrl = configuration.getScmRepoURL();
        this.revision = configuration.getScmRevision();
        this.outputDir = outputDir + File.separator + configuration.getName();
        this.buildScriptContents = configuration.getBuildScript();
    }

    public void build(Consumer<BuildDriverStatus> onMonitorComplete,
                      Consumer<Exception> onMonitorError) {

        ScriptExecutor executor = null;
        try {
            ScmRetriever scmRetriever = new ScmRetriever(SCMRepositoryType.GIT);
            scmRetriever.cloneRepository(scmUrl, revision, outputDir);

            ScriptGenerator bashScriptGenerator = new BashScriptGenerator();
            File buildScriptFile = bashScriptGenerator.generateExecutableScript(outputDir + File.separator + buildScriptName, buildScriptContents);

            File mvnlog = new File(outputDir + File.separator + buildLogName);
            mvnlog.createNewFile();

            executor = new ScriptExecutorImpl(buildScriptFile, mvnlog);
            executor.executeScript();
            buildLog = executor.getOutput();

            int result = executor.getResult();

            onMonitorComplete.accept(result == 0 ? BuildDriverStatus.SUCCESS : BuildDriverStatus.FAILED);
        } catch (Exception e) {
            if (executor != null)
                buildLog = executor.getOutput();
            onMonitorError.accept(e);
        }
    }

    public String getBuildLog(){
        return buildLog;
    }
}
