package br.com.gabrieldias.gestao_vagas.modules.company.repositories;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    @Query("SELECT j.id AS id, j.description AS description, j.benefits AS benefits, j.level AS level FROM tb_jobs j WHERE j.description LIKE CONCAT('%', :filter, '%')")
    List<JobEntity> findByDescriptionContaining(@Param("filter") String filter);

}
