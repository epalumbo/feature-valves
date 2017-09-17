package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

import java.util.List;

/**
 * Created by epalumbo on 9/16/17.
 */
public class FeatureCheck {

    private List<Tag> tags;

    public FeatureCheck(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureCheck that = (FeatureCheck) o;
        return Objects.equal(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tags);
    }

}
