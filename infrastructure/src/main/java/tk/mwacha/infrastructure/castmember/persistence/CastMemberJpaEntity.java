package tk.mwacha.infrastructure.castmember.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mwacha.domain.castmember.CastMember;
import tk.mwacha.domain.castmember.CastMemberID;
import tk.mwacha.domain.castmember.CastMemberType;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "CastMember")
@Table(name = "cast_members")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CastMemberJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CastMemberType type;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;


    public static CastMemberJpaEntity from(final CastMember aMember) {
        return new CastMemberJpaEntity(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt(),
                aMember.getUpdatedAt()
        );
    }

    public CastMember toAggregate() {
        return CastMember.with(
                CastMemberID.from(getId()),
                getName(),
                getType(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }
}