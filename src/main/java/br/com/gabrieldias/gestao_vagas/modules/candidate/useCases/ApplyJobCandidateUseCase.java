package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.JobNotFoundException;
import br.com.gabrieldias.gestao_vagas.exceptions.UserNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.ApplyJobRepostory;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.gabrieldias.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepostory applyJobRepostory;

    public ApplyJobEntity applyJob(UUID idCandidate, UUID idJob) { // Necessita do ID da vaga e do CANDIDATO
        // Validar se o candidato existe.
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        // Validar se a vaga existe.
        this.jobRepository.findById(idJob).orElseThrow(() -> new JobNotFoundException("Job not found"));

        // Candidato se candidata na vaga
        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .idCandidate(idCandidate)
                .idJob(idJob).build();

       return applyJobRepostory.save(applyJob); // Salva no banco de dados

    }

}
