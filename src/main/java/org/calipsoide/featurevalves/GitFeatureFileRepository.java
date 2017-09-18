package org.calipsoide.featurevalves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Created by epalumbo on 9/18/17.
 */
@Repository
public class GitFeatureFileRepository implements FeatureFileRepository {

    private GitRepoManager gitRepoManager;

    private FeatureFileRepository fileRepository;

    @Autowired
    public GitFeatureFileRepository(
            GitRepoManager gitRepoManager,
            LocalFeatureFileRepository fileRepository) {
        this.gitRepoManager = gitRepoManager;
        this.fileRepository = fileRepository;
    }

    @Override
    public Flux<FeatureFile> loadAll() {
        gitRepoManager.update();
        return fileRepository.loadAll();
    }

}
