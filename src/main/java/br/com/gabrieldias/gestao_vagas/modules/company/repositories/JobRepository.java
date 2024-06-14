package br.com.gabrieldias.gestao_vagas.modules.company.repositories;

import br.com.gabrieldias.gestao_vagas.modules.company.dto.JobDTO;
import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

//    @Query("SELECT new br.com.gabrieldias.gestao_vagas.modules.company.dto.JobDTO(j.id, j.description, j.benefits, j.level, c.id, c.name) " +
//            "FROM tb_jobs j " +
//            "JOIN tb_company c ON j.companyId = c.id " +
//            "WHERE j.description LIKE CONCAT('%', :filter, '%')")
//    List<JobDTO> findByDescriptionContaining(@Param("filter") String filter);

    List<JobEntity> findByDescriptionContaining(String filter);
}
