package br.com.gabrieldias.gestao_vagas.modules.candidate.useCases;

import br.com.gabrieldias.gestao_vagas.exceptions.CandidateNotFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;


    public ProfileCandidateResponseDTO getCandidateProfile(UUID idCandidate) {

        CandidateEntity candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() ->  new CandidateNotFoundException("Cannot find candidate with id: " + idCandidate));


        return new ProfileCandidateResponseDTO().builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .build();


    }

}
