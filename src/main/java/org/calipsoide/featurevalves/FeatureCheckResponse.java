package org.calipsoide.featurevalves;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;

/**
 * Created by epalumbo on 9/16/17.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FeatureCheckResponse {

    @JsonInclude
    private boolean result;

    @JsonCreator
    public FeatureCheckResponse(boolean result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureCheckResponse that = (FeatureCheckResponse) o;
        return result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(result);
    }

}
