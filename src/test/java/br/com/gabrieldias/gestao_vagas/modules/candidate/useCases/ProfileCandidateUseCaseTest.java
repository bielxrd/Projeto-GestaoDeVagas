package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.CandidateNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {

    @InjectMocks
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should not be able to get an profile candidate that not exists on our repository")
    public void should_not_be_able_to_get_an_profile_candidate_that_not_exists() {

        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());

        when(this.candidateRepository.findById(candidate.getId())).thenReturn(Optional.empty());

        try {
            this.profileCandidateUseCase.getCandidateProfile(candidate.getId());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CandidateNotFoundException.class);
        }

    }
    @Test
    @DisplayName("Should be able to get an profile candidate.")
    public void should_be_able_to_gen_an_profile_candidate() {

        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());

        when(this.candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));

        ProfileCandidateResponseDTO candidateProfile = this.profileCandidateUseCase.getCandidateProfile(candidate.getId());

        assertNotNull(candidateProfile);
        assertEquals(candidateProfile.getId(), candidate.getId());
    }


}
