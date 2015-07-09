package org.jboss.pnc.buildjob;

import java.io.File;
import java.io.IOException;

/**
 * Created by aabulawi on 09/07/15.
 */
public interface ScriptGenerator {

    public File generateExecutableScript(String  pathToScript, String contents) throws IOException;
}
