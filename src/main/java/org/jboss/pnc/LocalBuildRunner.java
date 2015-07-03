package org.jboss.pnc;

import javax.inject.Inject;

import org.jboss.pnc.core.builder.BuildCoordinator;
import org.jboss.pnc.core.exception.CoreException;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;
import org.jboss.pnc.model.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class LocalBuildRunner {

    @Inject
    BuildCoordinator buildCoordinator;
    public void run(){

        int result = 0;
        Environment env = Environment.Builder.defaultEnvironment().build();
        BuildConfiguration buildConfiguration = BuildConfiguration.Builder.newBuilder().id(0).scmRepoURL("git@github.com:release-engineering/pom-manipulation-ext.git")
                .buildScript("mvn install").name("MYBUILDCONFIG").environment(env).scmRevision("22855c9404319a30195b8ba7e637ddfb3f5dcaaf").build();
        User user = User.Builder.newBuilder().id(1).build();


        try {
            buildCoordinator.build(buildConfiguration, user);
        } catch (CoreException e) {
            result = 1;
        }

        while(!buildCoordinator.getBuildTasks().isEmpty()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                result = 1;
            }
        }
        System.exit(result);

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
