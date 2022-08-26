package tk.mwacha.application.castmember.retrieve.list;

import tk.mwacha.application.UseCase;
import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;
public sealed abstract class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {
}