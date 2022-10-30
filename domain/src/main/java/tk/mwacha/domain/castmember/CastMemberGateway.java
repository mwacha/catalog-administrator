package tk.mwacha.domain.castmember;

import tk.mwacha.domain.pagination.Pagination;
import tk.mwacha.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(CastMember genre);

    void deleteById(CastMemberID id);

    Optional<CastMember> findById(CastMemberID id);

    CastMember update(CastMember genre);

    Pagination<CastMember> findAll(SearchQuery query);

    List<CastMemberID> existsByIds(Iterable<CastMemberID> ids);
}
