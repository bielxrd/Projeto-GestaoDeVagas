package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.ListAllJobsDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListAllJobsUseCaseTest {

    @InjectMocks
    private ListAllJobsUseCase listAllJobsUseCase;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should return a list of jobs")
    public void must_return_an_list_of_jobs() {

        List<JobEntity> jobs = new ArrayList<>();

        jobs.add(new JobEntity(UUID.randomUUID(), "Fullstack Senior DevOps", "VR, VA, VT", "SENIOR", new CompanyEntity(), UUID.randomUUID(), LocalDateTime.now()));
        jobs.add(new JobEntity(UUID.randomUUID(), "Fullstack Senior DevOps", "VR, VA, VT", "SENIOR", new CompanyEntity(), UUID.randomUUID(), LocalDateTime.now()));
        jobs.add(new JobEntity(UUID.randomUUID(), "Fullstack Senior DevOps", "VR, VA, VT", "SENIOR", new CompanyEntity(), UUID.randomUUID(), LocalDateTime.now()));



        when(this.jobRepository.findAll()).thenReturn(jobs);

        ListAllJobsDTO result = listAllJobsUseCase.listAllJobs();

        assertNotNull(result);
        assertEquals(result.data, jobs);
        assertEquals(result.data.get(0), jobs.get(0));
    }

}
