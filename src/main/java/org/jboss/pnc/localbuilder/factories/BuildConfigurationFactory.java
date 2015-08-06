package org.jboss.pnc.localbuilder.factories;

import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.localbuilder.sourcemanager.SourceManagerUtils;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;

import java.util.Set;

/**
 * Created by aabulawi on 08/07/15.
 */
public class BuildConfigurationFactory {

    private static int idCounter = 0;

    public static BuildConfiguration createBuildConfigFromJSONConfig(JSONBuildConfiguration config){
        BuildConfiguration buildConfiguration =  BuildConfiguration.Builder.newBuilder().id(idCounter).name(config.getName())
                .scmRevision(SourceManagerUtils.extractRevision(config.getScm())).scmRepoURL(SourceManagerUtils.extractUrl(config.getScm()))
                .environment(Environment.Builder.defaultEnvironment().build()).buildScript(config.getCommand()).build();
        idCounter++;
        return buildConfiguration;
    }

}
