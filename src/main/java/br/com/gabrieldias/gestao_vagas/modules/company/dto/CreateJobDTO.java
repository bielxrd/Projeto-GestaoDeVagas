package br.com.gabrieldias.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora FULLSTACK PLENO", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "R$5000, VR, VT, VA, Dayoff.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "PLENO", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
