package org.calipsoide.featurevalves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Created by epalumbo on 9/17/17.
 */
@Service
public class FeatureLoader implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(FeatureLoader.class);

    private GitRepoManager gitRepoManager;

    private LocalFeatureFileRepository fileRepository;

    private YamlFileFeatureFactory featureFactory;

    private CachingFeatureService cachingService;

    private Duration refresh;

    @Autowired
    public FeatureLoader(
            GitRepoManager gitRepoManager,
            LocalFeatureFileRepository fileRepository,
            YamlFileFeatureFactory featureFactory,
            CachingFeatureService cachingService,
            @Value("${features.refresh.interval}") String refresh) {
        this.gitRepoManager = gitRepoManager;
        this.fileRepository = fileRepository;
        this.featureFactory = featureFactory;
        this.cachingService = cachingService;
        this.refresh = Duration.parse(refresh);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Mono<Integer> now = Mono.just(0);
        final Flux<Long> timer = Flux.interval(refresh);
        Flux.concat(now, timer)
                .flatMap(time -> {
                    logger.debug("Loading features configuration files.");
                    gitRepoManager.update();
                    return fileRepository.loadAll();
                })
                .flatMap(file -> featureFactory.read(file))
                .subscribe(cachingService);
    }

}
