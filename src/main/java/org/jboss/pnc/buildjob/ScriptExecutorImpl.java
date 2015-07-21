package org.jboss.pnc.buildjob;

import org.jboss.pnc.exception.LocalBuildProcessException;

import java.io.*;

/**
 * Created by aabulawi on 09/07/15.
 */
public class ScriptExecutorImpl implements ScriptExecutor {


    private File executableFile;
    private File logFile;
    private String output = "";
    private int result;

    public ScriptExecutorImpl(File executableFile, File logFile){
        this.executableFile = executableFile;
        this.logFile = logFile;
    }

    @Override
    public void executeScript() throws LocalBuildProcessException {
        String[] buildcommands = {executableFile.getAbsolutePath()};
        ProcessBuilder processBuilder = new ProcessBuilder(buildcommands);
        processBuilder.directory(executableFile.getParentFile());
        processBuilder.redirectErrorStream(true);
        Process process;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile))) {
            process = processBuilder.start();
            boolean running = true;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            Thread b = new Thread(new Runnable() {
                public void run() {
                    try {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            bw.write(line + "\n");
                            updateOutput(line);
                        }
                        bw.flush();
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
                    setResult(process.exitValue());
                } catch (InterruptedException e) {
                    process.destroy();
                }
            }
        } catch (InterruptedException e) {
            throw new LocalBuildProcessException(e);
        } catch (IOException e) {
            throw new LocalBuildProcessException(e);
        }
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public String getOutput() {
        return output.trim();
    }

    private void setResult(int result){
        this.result = result;
    }

    private void updateOutput(String line){
        this.output += line + "\n";
    }

}
