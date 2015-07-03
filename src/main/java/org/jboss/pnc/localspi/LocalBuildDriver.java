package org.jboss.pnc.localspi;


import org.jboss.pnc.buildjob.LocalBuildJob;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildType;
import org.jboss.pnc.spi.builddriver.*;
import org.jboss.pnc.spi.builddriver.exception.BuildDriverException;
import org.jboss.pnc.spi.environment.RunningEnvironment;

/**
 * Created by aabulawi on 24/06/15.
 */
public class LocalBuildDriver implements BuildDriver {
    @Override
    public String getDriverId() {
        return null;
    }

    @Override
    public boolean canBuild(BuildType buildType) {
        return true;
    }

    @Override
    public RunningBuild startProjectBuild(BuildConfiguration buildConfiguration, final RunningEnvironment runningEnvironment) throws BuildDriverException {
        String scmURL = buildConfiguration.getScmRepoURL();
        String buildScript = buildConfiguration.getBuildScript();
        LocalBuildJob localBuildJob = new LocalBuildJob("https://github.com/release-engineering/pom-manipulation-ext.git", "22855c9404319a30195b8ba7e637ddfb3f5dcaaf", "builddir", buildScript);
        return new LocalRunningBuild(runningEnvironment, localBuildJob);
    }

    private BuildDriverResult getBuildResultMock(final RunningEnvironment runningEnvironment) {
        return new BuildDriverResult() {
            @Override
            public String getBuildLog() throws BuildDriverException {
                return "Building in workspace ... Finished: SUCCESS";
            }

            @Override
            public BuildDriverStatus getBuildDriverStatus() throws BuildDriverException {
                return BuildDriverStatus.SUCCESS;
            }

            @Override
            public RunningEnvironment getRunningEnvironment() {
                return runningEnvironment;
            }
        };
    }

}