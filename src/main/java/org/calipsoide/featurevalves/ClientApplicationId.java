package org.calipsoide.featurevalves;

import com.google.common.base.Objects;

/**
 * Created by epalumbo on 9/16/17.
 */
public class ClientApplicationId {

    private String name;

    private ClientApplicationId(String name) {
        this.name = name;
    }

    public static ClientApplicationId of(String name) {
        return new ClientApplicationId(name.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientApplicationId that = (ClientApplicationId) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
