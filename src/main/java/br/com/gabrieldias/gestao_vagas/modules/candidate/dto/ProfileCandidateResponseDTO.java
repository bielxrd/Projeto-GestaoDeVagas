package br.com.gabrieldias.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Desenvolvedor Java JUNIOR")
    private String description;
    @Schema(example = "bielxrd")
    private String username;
    @Schema(example = "gabriel.dias@gmail.com")
    private String email;
    private UUID id;
    @Schema(example = "Gabriel Dias")
    private String name;


}
