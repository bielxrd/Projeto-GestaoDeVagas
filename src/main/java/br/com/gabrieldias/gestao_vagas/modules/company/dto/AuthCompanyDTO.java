package br.com.gabrieldias.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyDTO {

    @Schema(example = "santander", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(example = "1243xzdgdfsfdsf3daw", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
