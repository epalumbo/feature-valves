package org.calipsoide.featurevalves;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by epalumbo on 9/17/17.
 */
@Service
public class CachingFeatureServiceProxy implements FeatureService {

    private Cache<String, Feature> cache;

    private FeatureService target;

    @Autowired
    public CachingFeatureServiceProxy(YamlFileFeatureService target) {
        this.target = target;
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(30, SECONDS).build();
    }

    private static String keyOf(ClientApplicationId applicationId, String featureName) {
        return applicationId + ":" + featureName;
    }

    @Override
    public Mono<Feature> findBy(ClientApplicationId applicationId, String featureName) {
        final String key = keyOf(applicationId, featureName);
        final Feature cached = cache.getIfPresent(key);
        return Optional
                .ofNullable(cached)
                .map(Mono::just)
                .orElseGet(() -> target.findBy(applicationId, featureName)
                        .doOnNext(feature -> cache.put(key, feature)));
    }

}
