package org.calipsoide.featurevalves;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.MINUTES;
import static reactor.core.publisher.Mono.justOrEmpty;

/**
 * Created by epalumbo on 9/17/17.
 */
@Service
public class CachingFeatureService implements FeatureService, Consumer<Feature> {

    private static final Logger logger = LoggerFactory.getLogger(CachingFeatureService.class);

    private Cache<FeatureId, Feature> cache;

    public CachingFeatureService(@Value("${features.cache.ttl}") int ttl) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(ttl, MINUTES).build();
    }

    @Override
    public Mono<Feature> findBy(FeatureId id) {
        final Feature cached = cache.getIfPresent(id);
        return justOrEmpty(cached);
    }

    @Override
    public void accept(Feature feature) {
        cache.put(feature.getId(), feature);
        logger.debug("Reloaded - {}", feature);
    }

}
