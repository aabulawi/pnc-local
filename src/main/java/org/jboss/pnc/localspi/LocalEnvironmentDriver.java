package org.jboss.pnc.localspi;

import org.jboss.pnc.model.Environment;
import org.jboss.pnc.spi.environment.EnvironmentDriver;
import org.jboss.pnc.spi.environment.RunningEnvironment;
import org.jboss.pnc.spi.environment.StartedEnvironment;
import org.jboss.pnc.spi.environment.exception.EnvironmentDriverException;
import org.jboss.pnc.spi.repositorymanager.model.RepositorySession;

import javax.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
import java.util.function.Consumer;

@ApplicationScoped
public class LocalEnvironmentDriver implements EnvironmentDriver {

    @Override
    public StartedEnvironment buildEnvironment(Environment buildEnvironment,
                                               final RepositorySession repositoryConfiguration) throws EnvironmentDriverException {
        return new StartedEnvironment() {

            @Override
            public void destroyEnvironment() throws EnvironmentDriverException {

            }

            @Override
            public void monitorInitialization(Consumer<RunningEnvironment> onComplete,
                                              Consumer<Exception> onError) {
                onComplete.accept(
                        new RunningEnvironment() {

                            @Override
                            public RepositorySession getRepositorySession() {
                                return repositoryConfiguration;
                            }

                            @Override
                            public Path getWorkingDirectory() {
                                return null;
                            }

                            @Override
                            public String getJenkinsUrl() {
                                return "http://10.10.10.10:8080";
                            }

                            @Override
                            public int getJenkinsPort() {
                                return 0;
                            }

                            @Override
                            public String getId() {
                                return null;
                            }

                            @Override
                            public void destroyEnvironment() throws EnvironmentDriverException {
                            }
                        });
            }

            @Override
            public String getId() {
                return null;
            }
        };

    }

    @Override
    public boolean canBuildEnvironment(Environment environment) {
        return true;
    }

}
