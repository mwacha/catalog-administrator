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
import tk.mwacha.domain.category.CategoryGateway;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;


    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoryByIdUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
