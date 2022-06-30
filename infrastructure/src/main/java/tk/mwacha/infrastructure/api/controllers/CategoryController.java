package tk.mwacha.infrastructure.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.mwacha.application.category.create.CreateCategoryCommand;
import tk.mwacha.application.category.create.CreateCategoryOutput;
import tk.mwacha.application.category.create.CreateCategoryUseCase;
import tk.mwacha.application.category.retrieve.get.GetCategoryByIdUseCase;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.validation.handler.Notification;
import tk.mwacha.infrastructure.api.CategoryAPI;
import tk.mwacha.infrastructure.category.models.CategoryApiOutput;
import tk.mwacha.infrastructure.category.models.CreateCategoryApiInput;
import tk.mwacha.infrastructure.category.presenters.CategoryApiPresenter;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
       final var command =  CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

       final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;

       final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
               ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(command).fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, String perPage, String sort, String direction) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }
}
