package org.calipsoide.featurevalves;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by epalumbo on 9/16/17.
 */
public class FeatureValve {

    private String name;

    private ExpositionLevel exposition;

    private List<Tag> tags;

    public FeatureValve(String name, ExpositionLevel exposition, List<Tag> tags) {
        this.name = name;
        this.exposition = exposition;
        this.tags = tags;
    }

    int getCardinality() {
        return tags.size();
    }

    boolean matches(FeatureCheck check) {
        final List<Tag> tags = check.getTags();
        return !tags.isEmpty() && this.tags.stream().allMatch(tags::contains);
    }

    boolean allows(ExpositionLevel level) {
        return exposition.compareTo(level) > 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("tags", tags)
                .add("exposition", exposition)
                .toString();
    }

}
