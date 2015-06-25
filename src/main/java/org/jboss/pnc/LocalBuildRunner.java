package org.jboss.pnc;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.pnc.core.builder.BuildCoordinator;
import org.jboss.pnc.core.exception.CoreException;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;
import org.jboss.pnc.model.User;
import org.jboss.weld.environment.se.events.ContainerInitialized;

public class LocalBuildRunner {

    @Inject
    private BuildCoordinator buildCoordinator;

    public void main(@Observes ContainerInitialized event){
        Environment env = Environment.Builder.defaultEnvironment().build();
        BuildConfiguration buildConfiguration = BuildConfiguration.Builder.newBuilder().id(0).scmRepoURL("git@github.com:release-engineering/pom-manipulation-ext.git")
                .buildScript("mvn install").name("MYBUILDCONFIG").environment(env).build();
        User user = User.Builder.newBuilder().id(1).build();
        try {
            buildCoordinator.build(buildConfiguration, user);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}