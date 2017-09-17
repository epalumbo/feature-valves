package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

/**
 * Created by epalumbo on 9/17/17.
 */
public class FeatureId {

    private ClientApplicationId applicationId;
    private String featureCode;

    public FeatureId(ClientApplicationId applicationId, String featureCode) {
        this.applicationId = applicationId;
        this.featureCode = featureCode.toLowerCase();
    }

    public ClientApplicationId getApplicationId() {
        return applicationId;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureId featureId = (FeatureId) o;
        return Objects.equal(applicationId, featureId.applicationId) &&
                Objects.equal(featureCode, featureId.featureCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(applicationId, featureCode);
    }

    @Override
    public String toString() {
        return applicationId + ":" + featureCode;
    }

}
