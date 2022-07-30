package tk.mwacha.infrastructure.configuration.usecases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mwacha.application.category.create.CreateCategoryUseCase;
import tk.mwacha.application.category.create.DefaultCreateCategoryUseCase;
import tk.mwacha.application.category.delete.DefaultDeleteCategoryUseCase;
import tk.mwacha.application.category.delete.DeleteCategoryUseCase;
import tk.mwacha.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import tk.mwacha.application.category.retrieve.get.GetCategoryByIdUseCase;
import tk.mwacha.application.category.retrieve.list.DefaultListCategoriesUseCase;
import tk.mwacha.application.category.retrieve.list.ListCategoriesUseCase;
import tk.mwacha.application.category.update.DefaultUpdateCategoryUseCase;
import tk.mwacha.application.category.update.UpdateCategoryUseCase;
import tk.mwacha.application.genre.create.CreateGenreUseCase;
import tk.mwacha.application.genre.create.DefaultCreateGenreUseCase;
import tk.mwacha.application.genre.delete.DefaultDeleteGenreUseCase;
import tk.mwacha.application.genre.delete.DeleteGenreUseCase;
import tk.mwacha.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import tk.mwacha.application.genre.retrieve.get.GetGenreByIdUseCase;
import tk.mwacha.application.genre.retrieve.list.DefaultListGenreUseCase;
import tk.mwacha.application.genre.retrieve.list.ListGenreUseCase;
import tk.mwacha.application.genre.update.DefaultUpdateGenreUseCase;
import tk.mwacha.application.genre.update.UpdateGenreUseCase;
import tk.mwacha.domain.category.CategoryGateway;
import tk.mwacha.domain.genre.GenreGateway;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public GenreUseCaseConfig(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway);
    }
}