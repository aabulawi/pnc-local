package org.jboss.pnc.source_manager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aabulawi on 07/07/15.
 */
public class SourceManagerUtilsTest {

    @Test
    public void retrieveRevision(){
        String urlAndRevision = "https://github.com/project-ncl/pnc.git#80f8fcf4e334daeef15667ccf1d4517f1050545b";
        String revision = SourceManagerUtils.extractRevision(urlAndRevision);
        assertEquals("80f8fcf4e334daeef15667ccf1d4517f1050545b", revision);
    }

    @Test
    public void retrieveURL(){
        String urlAndRevision = "https://github.com/project-ncl/pnc.git#80f8fcf4e334daeef15667ccf1d4517f1050545b";
        String url = SourceManagerUtils.extractUrl(urlAndRevision);
        assertEquals("https://github.com/project-ncl/pnc.git", url);
    }

    @Test
    public void retrieveURLMultiplePounds(){
        String urlAndRevision = "file:///home/testuser/repos/myr#epos#80f8fcf4e334daeef15667ccf1d4517f1050545b";
        String url = SourceManagerUtils.extractUrl(urlAndRevision);
        assertEquals("file:///home/testuser/repos/myr#epos", url);
    }

    @Test
    public void retrieveRevisionMultiplePounds(){
        String urlAndRevision = "file:///home/testuser/repos/myr#epos#80f8fcf4e334daeef15667ccf1d4517f1050545b";
        String revision = SourceManagerUtils.extractRevision(urlAndRevision);
        assertEquals("80f8fcf4e334daeef15667ccf1d4517f1050545b", revision);
    }

    @Test
    public void retrieveRevisionNoPounds(){
        String urlAndRevision = "file:///home/testuser/repos/myr#epos#";
        String revision = SourceManagerUtils.extractRevision(urlAndRevision);
        assertEquals("", revision);
    }

    @Test
    public void retrieveURLNoPounds(){
        String urlAndRevision = "file:///home/testuser/repos/myr#epos#";
        String url = SourceManagerUtils.extractUrl(urlAndRevision);
        assertEquals("file:///home/testuser/repos/myr#epos", url);
    }

}
