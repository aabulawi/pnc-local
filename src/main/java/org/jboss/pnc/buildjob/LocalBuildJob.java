package org.jboss.pnc.buildjob;

import org.apache.maven.scm.command.checkout.CheckOutScmResult;
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
    private String buildScriptContents;
    private String revision;
    private String buildLog = "";

    public LocalBuildJob(String scmUrl, String revision, String outputDir, String installScript) {
        this.scmUrl = scmUrl;
        this.revision = revision;
        this.outputDir = outputDir;
        this.buildScriptContents = installScript;
    }

    public void start(Consumer<BuildDriverStatus> onMonitorComplete,
                      Consumer<Exception> onMonitorError) {
        BuildDriverStatus status = BuildDriverStatus.SUCCESS;
        ScmRetriever scmRetriever = new ScmRetriever(SCMRepositoryType.GIT);

        try {
            CheckOutScmResult result = scmRetriever.cloneRepository(scmUrl, revision, outputDir);
            if (!result.isSuccess()){
                status = BuildDriverStatus.FAILED;
            }
        } catch (Exception e) {
            status = BuildDriverStatus.FAILED;
        }

        if (status.equals(BuildDriverStatus.SUCCESS)) {
            String pathToBuildScript = String.format("%s/%s/%s", System.getProperty("user.dir"), outputDir, buildScriptName);
            File buildScriptFile = new File(pathToBuildScript);
            createBuildScript(buildScriptFile, buildScriptContents);

            String[] buildcommands = {buildScriptFile.getAbsolutePath()};
            ProcessBuilder buildProcessBuilder = new ProcessBuilder(buildcommands);
            buildProcessBuilder.directory(buildScriptFile.getParentFile());

            Process process;
            try {
                process = buildProcessBuilder.start();
                boolean running = true;

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                writer.newLine();
                writer.flush();
                Thread b = new Thread(new Runnable() {
                    public void run() {
                        try {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                                buildLog += line + "\n";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                b.start();
                b.join();

                while (running) {
                    try {

                        process.waitFor();
                        running = false;
                    } catch (InterruptedException e) {
                        process.destroy();
                        status = BuildDriverStatus.ABORTED;
                    }
                }
            } catch (IOException e) {
                status = BuildDriverStatus.FAILED;
            } catch (InterruptedException e) {
                status = BuildDriverStatus.ABORTED;            }
        }

        onMonitorComplete.accept(status);
    }

    private void createBuildScript(File buildScriptFile, String buildScript){

        if (!buildScriptFile.exists()){
            try {
                buildScriptFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileWriter fw = new FileWriter(buildScriptFile)) {
            fw.write("#!/bin/bash\n");
            for (String line : buildScript.split("\n")){
                fw.write(line+"\n");
            }
            fw.flush();
            buildScriptFile.setExecutable(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBuildLog(){
        return buildLog;
    }
}
