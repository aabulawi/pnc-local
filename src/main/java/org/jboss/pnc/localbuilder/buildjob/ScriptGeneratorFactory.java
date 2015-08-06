package org.jboss.pnc.localbuilder.buildjob;

import org.jboss.pnc.localbuilder.exception.OSNotSupportedException;
import org.jboss.pnc.model.OperationalSystem;

/**
 * Created by aabulawi on 13/07/15.
 */
public class ScriptGeneratorFactory {

    public static ScriptGenerator createScriptGenerator(OperationalSystem operationalSystem) throws OSNotSupportedException {
        switch (operationalSystem) {
            case LINUX:
                return new BashScriptGenerator();
        }
        throw new OSNotSupportedException(String.format("OS %s is currently supported", operationalSystem.name()));
    }

}
