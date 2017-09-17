package org.calipsoide.featurevalves;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by epalumbo on 9/16/17.
 */
public class HashingEvaluator implements Evaluator {

    private List<String> tagNames;

    public HashingEvaluator(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public Optional<ExpositionLevel> evaluate(FeatureCheck check) {
        final List<String> values =
                check.getTags().stream()
                        .filter(tag -> tagNames.contains(tag.getCode()))
                        .map(Tag::getValue)
                        .collect(toList());
        if (values.isEmpty()) {
            return Optional.empty();
        } else {
            final String source = Joiner.on(":").join(values);
            final int hash = Math.abs(source.hashCode());
            return Optional.of(ExpositionLevel.ofPercentage(hash % 100));
        }
    }

}
