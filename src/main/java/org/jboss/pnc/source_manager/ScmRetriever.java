package org.jboss.pnc.source_manager;

import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmTag;
import org.apache.maven.scm.command.checkout.CheckOutScmResult;
import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.manager.NoSuchScmProviderException;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.provider.git.jgit.JGitScmProvider;
import org.apache.maven.scm.repository.ScmRepository;
import org.apache.maven.scm.repository.ScmRepositoryException;

import java.io.File;


public class ScmRetriever {

    private ScmManager scmManager;
    private SCMRepositoryType repositoryType;

    public ScmRetriever(SCMRepositoryType repoType){
        this.scmManager = new BasicScmManager();
        this.repositoryType = repoType;
        setProvider(repositoryType);
    }

    public CheckOutScmResult cloneRepository(String scmUrl, String revision, String cloneTo) throws ScmException {

        File buildDir = new File(cloneTo);
        if (!buildDir.exists())
            buildDir.mkdir();

        ScmRepository repo = getScmRepository(String.format("scm:%s:%s", repositoryType.name(), scmUrl), scmManager);
        return scmManager.checkOut(repo, new ScmFileSet(buildDir), new ScmTag(revision));
    }

    private void setProvider(SCMRepositoryType type){
        switch (type) {
            case GIT:
                scmManager.setScmProvider(type.name(), new JGitScmProvider());
        }
    }

    private ScmRepository getScmRepository( String scmUrl, ScmManager scmManager ) throws ScmException {
        try
        {
            return scmManager.makeScmRepository( scmUrl );
        }
        catch ( NoSuchScmProviderException ex )
        {
            throw new ScmException( "Could not find a provider.", ex );
        }
        catch ( ScmRepositoryException ex )
        {
            throw new ScmException( "Error while connecting to the repository", ex );
        }
    }
}
