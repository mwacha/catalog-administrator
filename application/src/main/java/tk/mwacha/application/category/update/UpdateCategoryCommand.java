package tk.mwacha.application.category.update;

import java.util.UUID;

public record UpdateCategoryCommand(UUID id, String name, String description, boolean isActive) {

    public static UpdateCategoryCommand with(final UUID id, final String name, final String description, final boolean isActive) {
        return new UpdateCategoryCommand(id, name, description, isActive);
    }
}
