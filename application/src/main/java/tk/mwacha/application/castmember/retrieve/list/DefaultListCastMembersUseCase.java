package tk.mwacha.application.castmember.retrieve.list;

import tk.mwacha.domain.castmember.CastMemberGateway;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;

import java.util.Objects;

public final class DefaultListCastMembersUseCase extends ListCastMembersUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMembersUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery aQuery) {
        return this.castMemberGateway.findAll(aQuery)
                .map(CastMemberListOutput::from);
    }
}