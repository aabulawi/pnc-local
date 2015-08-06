package org.jboss.pnc.localbuilder.parser;

import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by aabulawi on 08/07/15.
 */
public class PncLocalCliOptionsTest {

    @Test
    public void testValidArgs() throws CmdLineException {
        String[] args = {"-f", "build.json"};
        PncLocalCliOptions pncLocalCliOptions = new PncLocalCliOptions();
        CmdLineParser cmdLineParser = new CmdLineParser(pncLocalCliOptions);
        cmdLineParser.parseArgument(args);
        assertEquals(new File("build.json"), pncLocalCliOptions.getBuildJSON());

    }

    @Test(expected = CmdLineException.class)
    public void testInvalidArgs() throws CmdLineException {
        String[] args = {"-t", "build.json"};
        PncLocalCliOptions pncLocalCliOptions = new PncLocalCliOptions();
        CmdLineParser cmdLineParser = new CmdLineParser(pncLocalCliOptions);
        cmdLineParser.parseArgument(args);
    }

}
