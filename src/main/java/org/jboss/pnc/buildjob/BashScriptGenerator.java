package org.jboss.pnc.buildjob;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by aabulawi on 09/07/15.
 */
public class BashScriptGenerator implements ScriptGenerator {

    @Override
    public File generateExecutableScript(String pathToScript, String contents) throws IOException {
        File script = new File(pathToScript);
        if (!script.exists()){
            script.createNewFile();
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(script))) {
            bw.write("#!/bin/bash\n");
            for (String line : contents.split("\n")){
                bw.write(line + "\n");
            }
            bw.flush();
            script.setExecutable(true);
        }
        return script;
    }
}
