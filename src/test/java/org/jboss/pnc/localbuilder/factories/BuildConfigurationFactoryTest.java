package org.jboss.pnc.localbuilder.factories;

import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.localbuilder.utils.TestUtils;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildConfigurationSet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by aabulawi on 06/08/15.
 */
public class BuildConfigurationFactoryTest {

    @Test
    public void testConfigurationDependenciesNotDefined() throws IOException {
        List<JSONBuildConfiguration> configs = TestUtils.generateConfigList("validBuild.json");
        BuildConfiguration buildConfiguration = BuildConfigurationFactory.createBuildConfigFromJSONConfig(configs.get(0));
        assertEquals("MyTestProject", buildConfiguration.getName());
        assertEquals("mvn clean install", buildConfiguration.getBuildScript());
        assertEquals("97c3e1f137282ca8a69cb67cef8627da45797a94", buildConfiguration.getScmRevision());
        assertEquals("http://github.com/repo.git", buildConfiguration.getScmRepoURL());
        assertTrue(buildConfiguration.getDependencies().isEmpty());

    }

}
