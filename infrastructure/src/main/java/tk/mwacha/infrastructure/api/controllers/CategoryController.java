package tk.mwacha.infrastructure.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.mwacha.application.category.create.CreateCategoryUseCase;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.infrastructure.api.CategoryAPI;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, String perPage, String sort, String direction) {
        return null;
    }
}
