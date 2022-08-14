package tk.mwacha.infrastructure.configuration.usecases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mwacha.application.castmember.create.CreateCastMemberUseCase;
import tk.mwacha.application.castmember.create.DefaultCreateCastMemberUseCase;
import tk.mwacha.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import tk.mwacha.application.castmember.delete.DeleteCastMemberUseCase;
import tk.mwacha.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import tk.mwacha.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import tk.mwacha.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import tk.mwacha.application.castmember.retrieve.list.ListCastMembersUseCase;
import tk.mwacha.application.castmember.update.DefaultUpdateCastMemberUseCase;
import tk.mwacha.application.castmember.update.UpdateCastMemberUseCase;
import tk.mwacha.domain.castmember.CastMemberGateway;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }
}