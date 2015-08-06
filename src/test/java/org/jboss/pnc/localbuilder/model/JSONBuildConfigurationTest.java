package org.jboss.pnc.localbuilder.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by aabulawi on 07/07/15.
 */
public class JSONBuildConfigurationTest {

    private String configsDir = System.getProperty("user.dir") + "/src/test/resources/json_build_configs/";

    @Test
    public void testValidJSONConfiguration() throws IOException {
        File json = new File(configsDir+"validBuild.json");
        ObjectMapper mapper = new ObjectMapper();
        List<JSONBuildConfiguration> jsonConfig = mapper.readValue(json, new TypeReference<List<JSONBuildConfiguration>>() {});
        assertEquals("http://github.com/repo.git#97c3e1f137282ca8a69cb67cef8627da45797a94", jsonConfig.get(0).getScm());
    }

    @Test
    public void testCorrectNumberDependencies() throws IOException {
        File json = new File(configsDir+"validBuild.json");
        ObjectMapper mapper = new ObjectMapper();
        List<JSONBuildConfiguration> jsonConfig = mapper.readValue(json, new TypeReference<List<JSONBuildConfiguration>>() {});
        assertEquals(2, jsonConfig.get(0).getDependencies().size());
    }


    @Test(expected = JsonMappingException.class)
    public void testInvalidJSONConfiguration() throws IOException {
        File json = new File(configsDir+"invalidBuild.json");
        ObjectMapper mapper = new ObjectMapper();
        List<JSONBuildConfiguration> jsonConfig = mapper.readValue(json, new TypeReference<List<JSONBuildConfiguration>>() {});
    }

    @Test
    public void createInvalidBuildConfiguration() throws Exception {
        File json = new File(configsDir+"missingNameBuild.json");
        ObjectMapper mapper = new ObjectMapper();
        List<JSONBuildConfiguration> jsonConfig = mapper.readValue(json, new TypeReference<List<JSONBuildConfiguration>>() {});
        assertTrue(jsonConfig.stream().anyMatch(config -> !config.isValid()));
    }

}
