package org.jboss.pnc.localbuilder.model;

import org.jboss.pnc.localbuilder.exception.IncompleteConfigurationException;

import java.util.List;

/**
 * Created by aabulawi on 07/07/15.
 */
public class JSONBuildConfiguration {

    private String name;
    private String scm;
    private String command;
    private List<String> dependencies;

    public String getName(){
        return name;
    }

    public String getScm(){
        return scm;
    }

    public String getCommand(){
        return command;
    }

    public List<String> getDependencies(){
        return dependencies;
    }

    public void setName(String name){
         this.name = name;
    }

    public void getScm(String scm){
        this.scm = scm;
    }

    public void getCommand(String command){
        this.command = command;
    }

    public void getDependencies(List<String> dependencies){
        this.dependencies = dependencies;
    }

    public boolean isValid() {
        if (name == null || scm == null || command == null || dependencies == null)
            return false;
        return true;
    }

}
