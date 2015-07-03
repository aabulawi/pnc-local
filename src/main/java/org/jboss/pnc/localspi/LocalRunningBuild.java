package org.jboss.pnc.localspi;

import org.jboss.pnc.buildjob.LocalBuildJob;
import org.jboss.pnc.spi.builddriver.BuildDriverStatus;
import org.jboss.pnc.spi.builddriver.CompletedBuild;
import org.jboss.pnc.spi.builddriver.RunningBuild;
import org.jboss.pnc.spi.environment.RunningEnvironment;

import java.util.function.Consumer;

/**
 * Created by aabulawi on 25/06/15.
 */
public class LocalRunningBuild implements RunningBuild {

    private RunningEnvironment runningEnvironment;
    private LocalBuildJob localBuildJob;

    public LocalRunningBuild(RunningEnvironment runningEnvironment, LocalBuildJob localBuildJob){
        this.runningEnvironment = runningEnvironment;
        this.localBuildJob = localBuildJob;
    }

    @Override
    public void monitor(Consumer<CompletedBuild> onComplete, Consumer<Exception> onError) {
        Consumer<BuildDriverStatus> onBuildComplete = (buildDriverStatus) -> {
            onComplete.accept(new LocalCompletedBuild(localBuildJob.getBuildLog(), buildDriverStatus, runningEnvironment));
        };
        Consumer<Exception> onBuildError = (e) -> {
            onError.accept(e);
        };

        localBuildJob.start(onBuildComplete, onBuildError);


    }

    @Override
    public RunningEnvironment getRunningEnvironment() {
        return runningEnvironment;
    }
}
