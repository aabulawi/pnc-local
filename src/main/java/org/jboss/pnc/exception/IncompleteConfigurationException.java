package org.jboss.pnc.exception;

/**
 * Created by aabulawi on 07/07/15.
 */
public class IncompleteConfigurationException extends Exception {

    public IncompleteConfigurationException () {

    }

    public IncompleteConfigurationException (String message) {
        super (message);
    }

    public IncompleteConfigurationException (Throwable cause) {
        super (cause);
    }

    public IncompleteConfigurationException (String message, Throwable cause) {
        super(message, cause);
    }
}
