package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

import java.nio.CharBuffer;

/**
 * Created by epalumbo on 9/17/17.
 */
public class FeatureFile {

    private FeatureId id;

    private CharBuffer buffer;

    public FeatureFile(FeatureId id, CharBuffer
            buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public FeatureId getId() {
        return id;
    }

    public CharBuffer getBuffer() {
        return buffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureFile that = (FeatureFile) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(buffer, that.buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, buffer);
    }

}
