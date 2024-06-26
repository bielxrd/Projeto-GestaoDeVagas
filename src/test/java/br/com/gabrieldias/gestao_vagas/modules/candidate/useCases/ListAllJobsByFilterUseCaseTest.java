package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListAllJobsByFilterUseCaseTest {

    @InjectMocks
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Must return an list of jobs by filter")
    public void should_return_an_list_of_jobs_by_filter() {

        List<JobEntity> jobs = new ArrayList<>();


        JobEntity job1 = JobEntity.builder()
                .id(UUID.randomUUID())
                .description("Desenvolvedor Kubernetes")
                .benefits("VR, VA, VT")
                .level("SENIOR")
                .companyId(UUID.randomUUID())
                .build();

                JobEntity job2 = JobEntity.builder()
                .id(UUID.randomUUID())
                .description("Desenvolvedor Kubernetes")
                .benefits("VR, VA, VT")
                .level("SENIOR")
                .companyId(UUID.randomUUID())
                .build();

                jobs.add(job1);
                jobs.add(job2);

        when(this.jobRepository.findByDescriptionContainingIgnoreCase("KUBERNETES")).thenReturn(jobs);

        List<JobEntity> result = this.listAllJobsByFilterUseCase.execute("KUBERNETES");

        assertEquals(jobs, result);

    }

}
