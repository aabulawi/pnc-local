package org.jboss.pnc.localbuilder.buildjob;


import org.jboss.pnc.localbuilder.parser.PncLocalCliOptions;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildType;
import org.jboss.pnc.spi.BuildExecution;
import org.jboss.pnc.spi.builddriver.*;
import org.jboss.pnc.spi.builddriver.exception.BuildDriverException;
import org.jboss.pnc.spi.environment.RunningEnvironment;

import javax.inject.Inject;

/**
 * Created by aabulawi on 24/06/15.
 */
public class LocalBuildDriver implements BuildDriver {

    @Inject
    PncLocalCliOptions options;

    @Override
    public String getDriverId() {
        return "local-build-driver";
    }

    @Override
    public boolean canBuild(BuildType buildType) {
        return true;
    }

    @Override
    public RunningBuild startProjectBuild(BuildExecution buildExecution, BuildConfiguration buildConfiguration, RunningEnvironment runningEnvironment) throws BuildDriverException {
        LocalBuildJob localBuildJob = new LocalBuildJob(buildConfiguration, "workspace");
        return new LocalRunningBuild(runningEnvironment, localBuildJob);
    }

}