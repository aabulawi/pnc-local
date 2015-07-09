package org.jboss.pnc.buildjob;

import java.io.IOException;

/**
 * Created by aabulawi on 09/07/15.
 */
public interface ScriptExecutor {

    public void executeScript() throws InterruptedException, IOException;

    public int getResult();

    public String getOutput();

}
