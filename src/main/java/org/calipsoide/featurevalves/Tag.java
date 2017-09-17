package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

/**
 * Created by epalumbo on 9/16/17.
 */
public class Tag {

    private String code;

    private String value;

    public Tag(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equal(code, tag.code) &&
                Objects.equal(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code, value);
    }

}
