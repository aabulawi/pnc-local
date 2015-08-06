package org.jboss.pnc.localbuilder;

import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.localbuilder.sourcemanager.SourceManagerUtils;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.Environment;

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
