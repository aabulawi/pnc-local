package org.jboss.pnc;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.pnc.core.builder.BuildCoordinator;
import org.jboss.pnc.core.exception.CoreException;
import org.jboss.pnc.exception.IncompleteConfigurationException;
import org.jboss.pnc.model.*;
import org.jboss.pnc.spi.events.BuildSetStatusChangedEvent;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LocalBuildRunner {

    @Inject
    BuildCoordinator buildCoordinator;

    public void run(List<JSONBuildConfiguration> configList){

        BuildConfigurationSet buildConfigurationSet = BuildConfigurationSet.Builder.newBuilder().id(0).name("Local Build").build();
        try {
            int id = 0;
            for (JSONBuildConfiguration config : configList) {
                config.validate();
                Set<BuildConfiguration> dependencies = collectDependencies(config.getDependencies(), buildConfigurationSet.getBuildConfigurations());
                BuildConfiguration buildConfig = BuildConfigurationFactory.createBuildConfigFromJSONConfig(id, config, dependencies);
                buildConfigurationSet.addBuildConfiguration(buildConfig);
                id++;
            }
            User user = User.Builder.newBuilder().id(0).build();
            buildCoordinator.build(buildConfigurationSet, user);
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (IncompleteConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    private Set<BuildConfiguration> collectDependencies(List<String> dependencyNames, Set<BuildConfiguration> buildConfigurations){
        return buildConfigurations.stream().filter(b -> dependencyNames.contains(b.getName())).collect(Collectors.toSet());
    }

    public void collectBuildSetStatusChangedEvent(@Observes BuildSetStatusChangedEvent buildSetStatusChangedEvent) {
        if (buildSetStatusChangedEvent.getNewStatus().isCompleted()){
            buildCoordinator.shutdownCoordinator();
        }
    }

    public static void main(String[] args){

        if (args.length == 0){
            System.out.println("Please provide the path to a build.json");
            return;
        }

        Weld weld = new Weld();
        WeldContainer weldContainer = weld.initialize();

        File jsonSource = new File(args[0]);

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<JSONBuildConfiguration> jsonConfig = mapper.readValue(jsonSource, new TypeReference<List<JSONBuildConfiguration>>() {});
            weldContainer.instance().select(LocalBuildRunner.class).get().run(jsonConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
