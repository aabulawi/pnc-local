package org.jboss.pnc.model;

import org.jboss.pnc.exception.IncompleteConfigurationException;
import org.jboss.pnc.source_manager.SourceManagerUtils;

import java.util.List;
import java.util.Set;

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

    public void validate() throws IncompleteConfigurationException {
        String errormsg = "Build.json is missing the following parameter: %s";
        if (name == null)
            throw new IncompleteConfigurationException(String.format(errormsg, "name"));
        if (scm == null)
            throw new IncompleteConfigurationException(String.format(errormsg, "scm"));
        if (command == null)
            throw new IncompleteConfigurationException(String.format(errormsg, "command"));
        if (dependencies == null)
            throw new IncompleteConfigurationException(String.format(errormsg, "dependencies"));
    }

}
