package tk.mwacha.infrastructure.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.mwacha.application.castmember.create.CreateCastMemberCommand;
import tk.mwacha.application.castmember.create.CreateCastMemberUseCase;
import tk.mwacha.application.castmember.delete.DeleteCastMemberUseCase;
import tk.mwacha.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import tk.mwacha.application.castmember.retrieve.list.ListCastMembersUseCase;
import tk.mwacha.application.castmember.update.UpdateCastMemberCommand;
import tk.mwacha.application.castmember.update.UpdateCastMemberUseCase;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;
import tk.mwacha.infrastructure.api.CastMemberAPI;
import tk.mwacha.infrastructure.castmember.models.CastMemberListResponse;
import tk.mwacha.infrastructure.castmember.models.CastMemberResponse;
import tk.mwacha.infrastructure.castmember.models.CreateCastMemberRequest;
import tk.mwacha.infrastructure.castmember.models.UpdateCastMemberRequest;
import tk.mwacha.infrastructure.castmember.presenter.CastMemberPresenter;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CastMemberController implements CastMemberAPI {


    private final CreateCastMemberUseCase createCastMemberUseCase;
    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;
    private final UpdateCastMemberUseCase updateCastMemberUseCase;
    private final DeleteCastMemberUseCase deleteCastMemberUseCase;
    private final ListCastMembersUseCase listCastMembersUseCase;


    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand =
                CreateCastMemberCommand.with(input.name(), input.type());

        final var output = this.createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public Pagination<CastMemberListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listCastMembersUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(CastMemberPresenter::present);
    }

    @Override
    public CastMemberResponse getById(final String id) {
        return CastMemberPresenter.present(this.getCastMemberByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberRequest aBody) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, aBody.name(), aBody.type());

        final var output = this.updateCastMemberUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteCastMemberUseCase.execute(id);
    }
}