package org.jboss.pnc;

import org.jboss.pnc.model.*;
import org.jboss.pnc.source_manager.SourceManagerUtils;

import java.util.Set;

/**
 * Created by aabulawi on 08/07/15.
 */
public class BuildConfigurationFactory {

    public static BuildConfiguration createBuildConfigFromJSONConfig(int id, JSONBuildConfiguration config, Set<BuildConfiguration> dependencies){
        return BuildConfiguration.Builder.newBuilder().id(id).name(config.getName()).scmRevision(SourceManagerUtils.extractRevision(config.getScm()))
                .scmRepoURL(SourceManagerUtils.extractUrl(config.getScm())).dependencies(dependencies).buildScript(config.getCommand())
                .environment(Environment.Builder.defaultEnvironment().build()).build();
    }


}
