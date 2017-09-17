package org.calipsoide.featurevalves;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by epalumbo on 9/17/17.
 */
@Service
public class GitRepoManager {

    private final File localPath;

    private final String url;

    private final String branch;

    private Git git;

    public GitRepoManager(
            @Value("${features.git.local.path}") String localPath,
            @Value("${features.git.remote.url}") String url,
            @Value("${features.git.remote.branch}") String branch) {
        this.localPath = new File(localPath);
        this.url = url;
        this.branch = branch;
    }

    public void update() {
        try {
            if (localPath.isDirectory()) {
                if (git == null) {
                    git = Git.open(localPath);
                }
                git.pull()
                        .setRemote("origin")
                        .setRemoteBranchName(branch)
                        .call();
            } else {
                git = Git
                        .cloneRepository()
                        .setURI(url)
                        .setRemote("origin")
                        .setBranch(branch)
                        .setDirectory(localPath)
                        .call();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
