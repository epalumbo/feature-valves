package org.calipsoide.featurevalves;

import java.util.Optional;

/**
 * Created by epalumbo on 9/16/17.
 */
public interface Evaluator {

    Optional<ExpositionLevel> evaluate(FeatureCheck check);

}
