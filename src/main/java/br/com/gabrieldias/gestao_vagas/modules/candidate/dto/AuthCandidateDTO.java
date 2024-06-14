package br.com.gabrieldias.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateDTO {

    @Schema(example = "bielxrd1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(example = "Bielgui10@", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
