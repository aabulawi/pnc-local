package org.jboss.pnc.localbuilder;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.pnc.localbuilder.factories.BuildConfigurationSetFactory;
import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.localbuilder.parser.PncLocalCliOptions;
import org.jboss.pnc.core.builder.BuildCoordinator;
import org.jboss.pnc.core.exception.CoreException;
import org.jboss.pnc.model.*;
import org.jboss.pnc.spi.datastore.DatastoreException;
import org.jboss.pnc.spi.events.BuildSetStatusChangedEvent;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.*;
import java.util.*;

public class LocalBuildRunner {

    @Inject
    BuildCoordinator buildCoordinator;

    public void run(List<JSONBuildConfiguration> configList){


        if (configList.stream().allMatch(config -> config.isValid())) {
            BuildConfigurationSet buildConfigurationSet = BuildConfigurationSetFactory.generateBuildConfigurationSet(configList, "Local Build Set");
            try {
                buildCoordinator.build(buildConfigurationSet, null);
            } catch (CoreException e) {
                e.printStackTrace();
            } catch (DatastoreException e) {
                e.printStackTrace();
            }
        }
    }

    public void collectBuildSetStatusChangedEvent(@Observes BuildSetStatusChangedEvent buildSetStatusChangedEvent) {
        if (buildSetStatusChangedEvent.getNewStatus().isCompleted()){
            buildCoordinator.shutdownCoordinator();
        }
    }

    public static void main(String[] args) {

        final PncLocalCliOptions options = new PncLocalCliOptions();
        final CmdLineParser parser = new CmdLineParser(options);

        try {
            parser.parseArgument(args);

            Weld weld = new Weld();
            Runtime.getRuntime().addShutdownHook(new ShutdownHook(weld));
            WeldContainer weldContainer = weld.initialize();

            File jsonSource = options.getBuildJSON();
            ObjectMapper mapper = new ObjectMapper();
            List<JSONBuildConfiguration> jsonConfig = mapper.readValue(jsonSource, new TypeReference<List<JSONBuildConfiguration>>() {});

            weldContainer.instance().select(LocalBuildRunner.class).get().run(jsonConfig);

        } catch (CmdLineException e) {
            parser.printUsage(System.err);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
