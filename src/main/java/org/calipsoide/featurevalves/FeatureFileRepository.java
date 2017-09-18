package org.calipsoide.featurevalves;

import reactor.core.publisher.Flux;

/**
 * Created by epalumbo on 9/18/17.
 */
public interface FeatureFileRepository {

    Flux<FeatureFile> loadAll();

}
