package tk.mwacha.domain.category;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tk.mwacha.domain.Identifier;

import java.util.Objects;
import java.util.UUID;


@EqualsAndHashCode
@Getter
public class CategoryID extends Identifier {

    @NonNull
    private final UUID value;

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
}
