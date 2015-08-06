package org.jboss.pnc.localbuilder.sourcemanager;

/**
 * Created by aabulawi on 07/07/15.
 */
public class SourceManagerUtils {

    public static String extractRevision(String urlWithRevision){
        return urlWithRevision.substring( urlWithRevision.lastIndexOf('#') + 1);
    }

    public static String extractUrl(String urlWithRevision){
        return urlWithRevision.substring(0, urlWithRevision.lastIndexOf('#'));
    }
}
