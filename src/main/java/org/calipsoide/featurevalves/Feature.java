package org.calipsoide.featurevalves;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by epalumbo on 9/16/17.
 */
public class Feature {

    private String name;

    private List<FeatureValve> valves;

    private Evaluator evaluator;

    private boolean active;

    public Feature(String name, List<FeatureValve> valves, Evaluator evaluator, boolean active) {
        this.name = name;
        this.valves = ImmutableList.copyOf(valves);
        this.evaluator = evaluator;
        this.active = active;
    }

    public String getName() {
        return name;
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
        return Objects.equal(name, feature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
