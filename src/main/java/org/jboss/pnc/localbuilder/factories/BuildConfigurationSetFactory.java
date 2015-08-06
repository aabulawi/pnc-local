package org.jboss.pnc.localbuilder.factories;

import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildConfigurationSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by aabulawi on 06/08/15.
 */
public class BuildConfigurationSetFactory {

    private static int idCounter = 0;

    public static BuildConfigurationSet generateBuildConfigurationSet(List<JSONBuildConfiguration> configList, String name){
        BuildConfigurationSet buildConfigurationSet = BuildConfigurationSet.Builder.newBuilder().id(idCounter).name(name).build();

        configList.stream().forEach(config -> buildConfigurationSet.addBuildConfiguration(BuildConfigurationFactory.createBuildConfigFromJSONConfig(config)));
        configList.stream().forEach(config -> addDependenciesToMatchingBuildConfiguration(config, buildConfigurationSet.getBuildConfigurations()));
        idCounter++;
        return buildConfigurationSet;
    }

    private static BuildConfiguration addDependenciesToMatchingBuildConfiguration(JSONBuildConfiguration config, Set<BuildConfiguration> buildConfigurations){
        BuildConfiguration buildConfiguration = buildConfigurations.stream().filter(bc -> bc.getName().equals(config.getName())).collect(Collectors.toList()).get(0);
        buildConfiguration.setDependencies(collectDependencies(config.getDependencies(), buildConfigurations));
        return buildConfiguration;
    }

    private static Set<BuildConfiguration> collectDependencies(List<String> dependencyNames, Set<BuildConfiguration> buildConfigurations){
        return buildConfigurations.stream().filter(b -> dependencyNames.contains(b.getName())).collect(Collectors.toSet());
    }

}
