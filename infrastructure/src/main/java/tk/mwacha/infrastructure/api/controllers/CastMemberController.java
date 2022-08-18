package tk.mwacha.infrastructure.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tk.mwacha.application.castmember.create.CreateCastMemberCommand;
import tk.mwacha.application.castmember.create.CreateCastMemberUseCase;
import tk.mwacha.infrastructure.api.CastMemberAPI;
import tk.mwacha.infrastructure.castmember.models.CreateCastMemberRequest;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;


    @Override
    public ResponseEntity<?> create(CreateCastMemberRequest input) {
       final var command = CreateCastMemberCommand.with(
                input.name(),
                input.castMemberType()
        );


       final var output = this.createCastMemberUseCase.execute(command);

       return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

//    @Override
//    public Pagination<CastMemberListResponse> listCategories(
//            final String search,
//            final int page,
//            final int perPage,
//            final String sort,
//            final String direction
//    ) {
//        return listCategoriesUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
//                .map(CastMemberApiPresenter::present);
//    }
//
//    @Override
//    public CastMemberResponse getById(String id) {
//        return CastMemberApiPresenter.present(this.getCastMemberByIdUseCase.execute(id));
//    }
//
//    @Override
//    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberRequest input) {
//        final var aCommand = UpdateCastMemberCommand.with(
//                id,
//                input.name(),
//                input.description(),
//                input.active() != null ? input.active() : true
//        );
//
//        final Function<Notification, ResponseEntity<?>> onError = notification ->
//                ResponseEntity.unprocessableEntity().body(notification);
//
//        final Function<UpdateCastMemberOutput, ResponseEntity<?>> onSuccess =
//                ResponseEntity::ok;
//
//        return this.updateCastMemberUseCase.execute(aCommand)
//                .fold(onError, onSuccess);
//    }
//
//    @Override
//    public void deleteById(final String id) {
//        this.deleteCastMemberUseCase.execute(id);
//    }
}
