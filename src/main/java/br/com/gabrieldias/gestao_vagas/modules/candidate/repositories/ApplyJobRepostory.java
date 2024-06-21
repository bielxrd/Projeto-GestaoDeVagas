package br.com.gabrieldias.gestao_vagas.modules.candidate.repositories;

import br.com.gabrieldias.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepostory extends JpaRepository<ApplyJobEntity, UUID> {
}
