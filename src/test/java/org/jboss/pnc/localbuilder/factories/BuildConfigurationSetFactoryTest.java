package org.jboss.pnc.localbuilder.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.localbuilder.utils.TestUtils;
import org.jboss.pnc.model.BuildConfiguration;
import org.jboss.pnc.model.BuildConfigurationSet;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by aabulawi on 06/08/15.
 */
public class BuildConfigurationSetFactoryTest {


    //Should this case fail or throw an exception, or just continue with empty list (what it currently does)??
    @Test
    public void testConfigurationDependenciesNotDefined() throws IOException {
        List<JSONBuildConfiguration> configs = TestUtils.generateConfigList("validBuild.json");
        BuildConfigurationSet buildConfigurationSet = BuildConfigurationSetFactory.generateBuildConfigurationSet(configs, "test1");
        assertTrue(buildConfigurationSet.getBuildConfigurations().size() == 1);
        BuildConfiguration bc1 = TestUtils.getSpecitigBuildConfigurations("MyTestProject", buildConfigurationSet.getBuildConfigurations());
        assertTrue(bc1.getDependencies().size() == 0);
    }

    @Test
    public void testConfigurationMultipleDependenciesAllDefined() throws IOException {
        List<JSONBuildConfiguration> configs = TestUtils.generateConfigList("multipleDependencies.json");
        BuildConfigurationSet buildConfigurationSet = BuildConfigurationSetFactory.generateBuildConfigurationSet(configs, "test1");
        assertTrue(buildConfigurationSet.getBuildConfigurations().size() == 3);
        BuildConfiguration bc1 = TestUtils.getSpecitigBuildConfigurations("MyTestProject1", buildConfigurationSet.getBuildConfigurations());
        BuildConfiguration bc2 = TestUtils.getSpecitigBuildConfigurations("MyTestProject2", buildConfigurationSet.getBuildConfigurations());
        BuildConfiguration bc3 = TestUtils.getSpecitigBuildConfigurations("MyTestProject3", buildConfigurationSet.getBuildConfigurations());
        assertTrue(bc1.getDependencies().size() == 2);
        assertTrue(bc2.getDependencies().size() == 1);
        assertTrue(bc3.getDependencies().size() == 0);

        assertEquals(bc2, TestUtils.getSpecitigBuildConfigurations("MyTestProject2", bc1.getDependencies()));
        assertEquals(bc3, TestUtils.getSpecitigBuildConfigurations("MyTestProject3", bc1.getDependencies()));

        assertEquals(bc3, TestUtils.getSpecitigBuildConfigurations("MyTestProject3", bc2.getDependencies()));
    }



}
