package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by epalumbo on 9/16/17.
 */
public class ExpositionLevel implements Comparable<ExpositionLevel> {

    public static final ExpositionLevel ZERO = ofPercentage(0);

    private Integer percentage;

    private ExpositionLevel(int percentage) {
        this.percentage = percentage;
    }

    static ExpositionLevel ofPercentage(int percentage) {
        checkArgument(percentage >= 0 && percentage <= 100, "percentage must be between 0 and 100");
        return new ExpositionLevel(percentage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpositionLevel that = (ExpositionLevel) o;
        return percentage == that.percentage;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(percentage);
    }

    @Override
    public String toString() {
        return Integer.toString(percentage);
    }

    @Override
    public int compareTo(ExpositionLevel other) {
        return percentage.compareTo(other.percentage);
    }

}
