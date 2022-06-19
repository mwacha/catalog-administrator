package tk.mwacha.domain.category;

import lombok.NonNull;
import tk.mwacha.domain.Identifier;

import java.util.Objects;
import java.util.UUID;


public class CategoryID extends Identifier {

    @NonNull
    private final UUID value;

    public UUID getValue() {
        return value;
    }

    private CategoryID(final UUID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique() {
        return new CategoryID(UUID.randomUUID());
    }

    public static CategoryID from(final UUID id) {
        return new CategoryID(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
