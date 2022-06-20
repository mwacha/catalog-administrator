package tk.mwacha.domain;


import lombok.Getter;
import tk.mwacha.domain.validation.ValidationHandler;

import java.util.Objects;

@Getter
public abstract class Entity <ID extends Identifier> {

    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "id should not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
