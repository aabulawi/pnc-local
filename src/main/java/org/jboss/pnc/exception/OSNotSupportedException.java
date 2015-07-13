package org.jboss.pnc.exception;

/**
 * Created by aabulawi on 13/07/15.
 */
public class OSNotSupportedException extends Exception {

    public OSNotSupportedException () {

    }

    public OSNotSupportedException (String message) {
        super (message);
    }

    public OSNotSupportedException (Throwable cause) {
        super (cause);
    }

    public OSNotSupportedException (String message, Throwable cause) {
        super(message, cause);
    }


}
