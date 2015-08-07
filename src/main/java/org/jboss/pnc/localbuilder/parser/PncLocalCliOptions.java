package org.jboss.pnc.localbuilder.parser;

import org.kohsuke.args4j.Option;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.io.File;

/**
 * Created by aabulawi on 08/07/15.
 */

@Singleton
public class PncLocalCliOptions {
    @Option(name = "-f", aliases = { "--file" }, metaVar = "<file>",
            usage = "Provide the BUILD.json configuration (required)", required = true)
    private File buildJSON;

    @Option(name = "-h", aliases = "--help", usage = "Print help message")
    private boolean help = false;

    public File getBuildJSON(){
        return buildJSON;
    }
}
