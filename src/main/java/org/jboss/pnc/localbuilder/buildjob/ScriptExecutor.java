package org.jboss.pnc.localbuilder.buildjob;

import org.jboss.pnc.localbuilder.exception.LocalBuildProcessException;

/**
 * Created by aabulawi on 09/07/15.
 */
public interface ScriptExecutor {

    public void executeScript() throws LocalBuildProcessException;

    public int getResult();

    public String getOutput();

}
