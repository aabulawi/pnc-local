package org.jboss.pnc.localspi;

import org.jboss.pnc.spi.builddriver.BuildDriverResult;
import org.jboss.pnc.spi.builddriver.BuildDriverStatus;
import org.jboss.pnc.spi.builddriver.exception.BuildDriverException;
import org.jboss.pnc.spi.environment.RunningEnvironment;

/**
 * Created by aabulawi on 25/06/15.
 */
public class LocalBuildDriverResult implements BuildDriverResult {

    private String buildLog;
    private BuildDriverStatus status;
    private RunningEnvironment runningEnvironment;

    public LocalBuildDriverResult(String buildLog, BuildDriverStatus status, RunningEnvironment runningEnvironment){
        this.buildLog = buildLog;
        this.status = status;
        this.runningEnvironment = runningEnvironment;
    }

    @Override
    public String getBuildLog() throws BuildDriverException {
        return buildLog;
    }

    @Override
    public BuildDriverStatus getBuildDriverStatus() {
        return status;
    }

    @Override
    public RunningEnvironment getRunningEnvironment() {
        return runningEnvironment;
    }
}
