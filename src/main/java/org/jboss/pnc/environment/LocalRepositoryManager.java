package org.jboss.pnc.environment;

import org.jboss.pnc.model.BuildRecord;
import org.jboss.pnc.model.BuildRecordSet;
import org.jboss.pnc.model.RepositoryType;
import org.jboss.pnc.spi.BuildExecution;
import org.jboss.pnc.spi.repositorymanager.RepositoryManager;
import org.jboss.pnc.spi.repositorymanager.RepositoryManagerException;
import org.jboss.pnc.spi.repositorymanager.model.*;

import java.util.function.Consumer;

/**
 * Created by aabulawi on 24/06/15.
 */
public class LocalRepositoryManager implements RepositoryManager {
    private Boolean promotionSuccess;

    private Exception promotionError;

    private Boolean deletionSuccess;

    private Exception deletionError;

    public LocalRepositoryManager expectPromotionSuccess(boolean promotionSuccess) {
        this.promotionSuccess = promotionSuccess;
        return this;
    }

    public LocalRepositoryManager expectPromotionError(Exception promotionError) {
        this.promotionError = promotionError;
        return this;
    }

    public LocalRepositoryManager expectDeletionSuccess(boolean deletionSuccess) {
        this.deletionSuccess = deletionSuccess;
        return this;
    }

    public LocalRepositoryManager expectDeletionError(Exception deletionError) {
        this.deletionError = deletionError;
        return this;
    }

    @Override
    public RepositorySession createBuildRepository(BuildExecution buildExecution) throws RepositoryManagerException {

        RepositorySession repositoryConfiguration = new LocalRepositorySession();
        return repositoryConfiguration;
    }

    @Override
    public boolean canManage(RepositoryType managerType) {
        return true;
    }

    @Override
    public RunningRepositoryPromotion promoteBuild(BuildRecord buildRecord, String toGroup)
            throws RepositoryManagerException {
        return new RunningRepositoryPromotionMock(promotionSuccess, promotionError);
    }

    @Override
    public RunningRepositoryPromotion promoteBuildSet(BuildRecordSet buildRecordSet, String toGroup)
            throws RepositoryManagerException {
        return new RunningRepositoryPromotionMock(promotionSuccess, promotionError);
    }

    @Override
    public RunningRepositoryDeletion deleteBuild(BuildRecord buildRecord) throws RepositoryManagerException {
        return new RunningRepositoryDeletionMock(deletionSuccess, deletionError);
    }

    public static final class RunningRepositoryPromotionMock implements RunningRepositoryPromotion {

        private Boolean status;
        private Exception error;

        public RunningRepositoryPromotionMock(Boolean status, Exception error) {
            this.status = status;
            this.error = error;
        }

        @Override
        public void monitor(Consumer<CompletedRepositoryPromotion> onComplete, Consumer<Exception> onError) {
            if (status != null) {
                onComplete.accept(new CompletedRepositoryPromotion() {
                    @Override
                    public boolean isSuccessful() {
                        return status;
                    }
                });
            } else {
                onError.accept(error);
            }
        }

    }

    public static final class RunningRepositoryDeletionMock implements RunningRepositoryDeletion {

        private Boolean status;
        private Exception error;

        public RunningRepositoryDeletionMock(Boolean status, Exception error) {
            this.status = status;
            this.error = error;
        }

        @Override
        public void monitor(Consumer<CompletedRepositoryDeletion> onComplete, Consumer<Exception> onError) {
            if (status != null) {
                onComplete.accept(new CompletedRepositoryDeletion() {
                    @Override
                    public boolean isSuccessful() {
                        return status;
                    }
                });
            } else {
                onError.accept(error);
            }
        }

    }
}

