package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.ListAllJobsDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsUseCase {
    private final JobRepository jobRepository;

    public ListAllJobsUseCase(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @SneakyThrows
    public ListAllJobsDTO listAllJobs() {
        List<JobEntity> jobs = this.jobRepository.findAll();

        return ListAllJobsDTO.builder().data(jobs).build();
    }

}
