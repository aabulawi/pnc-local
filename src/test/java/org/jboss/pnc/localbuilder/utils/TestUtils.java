package org.jboss.pnc.localbuilder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.pnc.localbuilder.model.JSONBuildConfiguration;
import org.jboss.pnc.model.BuildConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by aabulawi on 06/08/15.
 */
public class TestUtils {

    public static final String CONFIGS_DIR = System.getProperty("user.dir") + "/src/test/resources/json_build_configs/";
    public static final String SCRIPTS_DIR = System.getProperty("user.dir") + "/src/test/resources/test_scripts/";


    public static List<JSONBuildConfiguration> generateConfigList(String jsonFileName) throws IOException {
        File jsonSource = new File(CONFIGS_DIR, jsonFileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonSource, new TypeReference<List<JSONBuildConfiguration>>() {});
    }

    public static BuildConfiguration getSpecitigBuildConfigurations(String name, Set<BuildConfiguration> buildConfigurations){
        return buildConfigurations.stream().filter(bc -> name.equals(bc.getName())).collect(Collectors.toList()).get(0);
    }


}
