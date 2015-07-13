package org.jboss.pnc.exception;

/**
 * Created by aabulawi on 13/07/15.
 */
public class LocalBuildProcessException extends Exception {

    public LocalBuildProcessException () {

    }

    public LocalBuildProcessException (String message) {
        super (message);
    }

    public LocalBuildProcessException (Throwable cause) {
        super (cause);
    }

    public LocalBuildProcessException (String message, Throwable cause) {
        super(message, cause);
    }

}
