package br.com.gabrieldias.gestao_vagas.modules.candidate.controllers;


import br.com.gabrieldias.gestao_vagas.exceptions.UserFoundException;
import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gabrieldias.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/")
    public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.candidateRepository.save(candidateEntity);
    }


}
