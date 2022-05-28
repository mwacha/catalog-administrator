package tk.mwacha.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;


@Getter
@EqualsAndHashCode
public abstract class Entity <ID extends Identifier> {

    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "id should not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

}
