package tk.mwacha.application;

import tk.mwacha.domain.category.Category;

public class UseCase {

    public Category execute() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        return actualCategory;
    }
}
