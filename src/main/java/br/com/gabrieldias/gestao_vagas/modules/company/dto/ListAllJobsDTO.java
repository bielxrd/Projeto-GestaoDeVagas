package br.com.gabrieldias.gestao_vagas.modules.company.dto;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAllJobsDTO {

    public List<JobEntity> data;

}
