package org.calipsoide.featurevalves;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by epalumbo on 9/16/17.
 */
public class Feature {

    private FeatureId id;

    private List<FeatureValve> valves;

    private Evaluator evaluator;

    private boolean active;

    public Feature(FeatureId id, List<FeatureValve> valves, Evaluator evaluator, boolean active) {
        this.id = id;
        this.valves = ImmutableList.copyOf(valves);
        this.evaluator = evaluator;
        this.active = active;
    }

    public FeatureId getId() {
        return id;
    }

    public boolean execute(FeatureCheck check) {
        if (active) {
            return valves.stream()
                    .filter(valve -> valve.matches(check))
                    .max((one, two) -> {
                        final int first = one.getCardinality();
                        final int second = two.getCardinality();
                        return Integer.valueOf(first).compareTo(second);
                    })
                    .flatMap(valve -> evaluator.evaluate(check).map(valve::allows))
                    .orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return Objects.equal(id, feature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("active", active)
                .add("evaluator", evaluator)
                .add("valves", valves)
                .toString();
    }
}
