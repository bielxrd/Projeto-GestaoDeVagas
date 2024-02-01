package br.com.gabrieldias.gestao_vagas.modules.candidate.repositories;

import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

}
