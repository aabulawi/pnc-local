package org.jboss.pnc;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.pnc.core.builder.BuildCoordinator;
import org.jboss.pnc.core.exception.CoreException;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;
import org.jboss.pnc.model.User;
import org.jboss.pnc.spi.events.BuildSetStatusChangedEvent;
import org.jboss.pnc.spi.events.BuildStatusChangedEvent;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class LocalBuildRunner {

    @Inject
    BuildCoordinator buildCoordinator;
    public void run(){

        Environment env = Environment.Builder.defaultEnvironment().build();
        BuildConfiguration buildConfiguration = BuildConfiguration.Builder.newBuilder().id(0).scmRepoURL("https://github.com/project-ncl/pnc")
                .buildScript("mvn install").name("MYBUILDCONFIG").environment(env).scmRevision("80f8fcf4e334daeef15667ccf1d4517f1050545b").build();
        User user = User.Builder.newBuilder().id(1).build();


        try {
            buildCoordinator.build(buildConfiguration, user);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public void collectBuildStatusChangedEvent(@Observes BuildStatusChangedEvent buildStatusChangedEvent) {
        if (buildCoordinator.getBuildTasks().isEmpty() && buildStatusChangedEvent.getNewStatus().isCompleted()){
            System.exit(0);
        }
    }

    public void collectBuildSetStatusChangedEvent(@Observes BuildSetStatusChangedEvent buildSetStatusChangedEvent) {
        if (buildCoordinator.getBuildTasks().isEmpty() && buildSetStatusChangedEvent.getNewStatus().isCompleted()){
            System.exit(0);
        }
    }

    public static void main(String[] args){

        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();
        weldContainer.instance().select(LocalBuildRunner.class).get().run();

        Runtime.getRuntime().addShutdownHook(new ShutdownHook(weld));
    }

    static class ShutdownHook extends Thread {
        private final Weld weld;


        ShutdownHook(final Weld weld) {
            this.weld = weld;
        }


        public void run() {
            weld.shutdown();
        }
    }
}
