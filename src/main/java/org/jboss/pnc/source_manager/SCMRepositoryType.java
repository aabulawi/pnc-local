package org.jboss.pnc.source_manager;

/**
 * Created by aabulawi on 03/07/15.
 */
public enum SCMRepositoryType {
    GIT("git");

    String protocol;
    SCMRepositoryType(String protocol){
        this.protocol = protocol;
    }
}
