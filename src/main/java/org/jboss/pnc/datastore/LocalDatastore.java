package org.jboss.pnc.datastore;


import org.jboss.pnc.model.BuildRecord;
import org.jboss.pnc.model.User;
import org.jboss.pnc.spi.datastore.Datastore;
import org.jboss.pnc.spi.datastore.DatastoreException;

/**
 * Created by aabulawi on 24/06/15.
 */
public class LocalDatastore implements Datastore {

    private int buildSetId = -1;
    private int buildId = -1;

    @Override
    public BuildRecord storeCompletedBuild(BuildRecord buildRecord) throws DatastoreException {
        return buildRecord;
    }

    @Override
    public User retrieveUserByUsername(String s) {
        return new User();
    }

    @Override
    public void createNewUser(User user) {

    }

    @Override
    public int getNextBuildRecordId() {
        buildId += 1;
        return buildId;
    }

    @Override
    public int getNextBuildConfigSetRecordId() {
        buildSetId += 1;
        return buildSetId;
    }
}
