package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.JobNotFoundException;
import br.com.gabrieldias.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.ApplyJobRepostory;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock // Quando colocamos @Mock estamos falando que essas classes sao dependencias dessa minha classe Inject Mocks
    private CandidateRepository candidateRepository;

    @Mock // Quando colocamos @Mock estamos falando que essas classes sao dependencias dessa minha classe Inject Mocks
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepostory applyJobRepostory;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found.")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.applyJob(null, null);
        } catch (Exception e) {
           assertThat(e).isInstanceOf(UserNotFoundException.class); // Verificar que a exception que deu é do tipo UserNotFound.
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        // Gera um UUID aleatório para o candidato
        UUID idCandidate = UUID.randomUUID();

        // Cria e configura a entidade CandidateEntity
        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        // Configura o comportamento do mock: sempre retornar o candidate quando findById é chamado com idCandidate
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        // Imprime os valores do candidate para verificação
        System.out.println("Candidate ID: " + candidate.getId());

        try {
            // Tenta aplicar para um job com id nulo
            applyJobCandidateUseCase.applyJob(idCandidate, null);
        } catch (Exception e) {
            // Verifica se a exceção lançada é do tipo JobNotFoundException
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new Apply Job")
    public void shouldBeAbleToCreateANewApplyJob() {

        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());

        JobEntity jobEntity = JobEntity.builder().id(UUID.randomUUID()).build();

        ApplyJobEntity applyJob = new ApplyJobEntity();


        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(jobEntity.getId())).thenReturn(Optional.of(jobEntity));

        applyJob.setIdCandidate(candidate.getId());
        applyJob.setIdJob(jobEntity.getId());

        when(applyJobRepostory.save(applyJob)).thenReturn(applyJob);

        try {
            ApplyJobEntity applyJobTest = this.applyJobCandidateUseCase.applyJob(candidate.getId(), jobEntity.getId());

            assertEquals(applyJobTest.getIdCandidate(), applyJob.getIdCandidate());
            assertEquals(applyJobTest.getIdJob(), applyJob.getIdJob());
            assertThat(applyJobTest).hasFieldOrProperty("id");

        } catch (Exception e) {
            fail("Error: "+e.getMessage());
        }
    }


}
