package org.jboss.pnc.localspi;

import org.jboss.pnc.model.BuildStatus;
import org.jboss.pnc.spi.builddriver.BuildDriverResult;
import org.jboss.pnc.spi.builddriver.BuildDriverStatus;
import org.jboss.pnc.spi.builddriver.CompletedBuild;
import org.jboss.pnc.spi.builddriver.exception.BuildDriverException;
import org.jboss.pnc.spi.environment.RunningEnvironment;

/**
 * Created by aabulawi on 25/06/15.
 */
public class LocalCompletedBuild implements CompletedBuild {

    private RunningEnvironment runningEnvironment;
    private BuildDriverStatus status;
    private String buildLog;

    public LocalCompletedBuild(String buildLog, BuildDriverStatus status, RunningEnvironment runningEnvironment){
        this.status = status;
        this.runningEnvironment = runningEnvironment;
        this.buildLog = buildLog;
    }

    @Override
    public BuildDriverStatus getCompleteStatus() {
        return status;
    }

    @Override
    public BuildDriverResult getBuildResult() throws BuildDriverException {
        return new LocalBuildDriverResult(buildLog, status, runningEnvironment);
    }

    @Override
    public RunningEnvironment getRunningEnvironment() {
        return runningEnvironment;
    }
}
