package org.calipsoide.featurevalves;

import reactor.core.publisher.Mono;

/**
 * Created by epalumbo on 9/17/17.
 */
public interface FeatureService {

    Mono<Feature> findBy(ClientApplicationId applicationId, String name);

}
