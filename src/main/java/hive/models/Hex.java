package hive.models;

import java.util.Objects;

public class Hex {
    private Integer q;
    private Integer r;

    Hex(Integer q, Integer r) {
        this.q = q;
        this.r = r;
    }

    public Integer getKey() {
        return q;
    }

    public Integer getValue() {
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return Objects.equals(q, hex.q) &&
                Objects.equals(r, hex.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }

    @Override
    public String toString() {
        return '(' + q + ", " + r + ')';
    }
}
