package br.com.gabrieldias.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID id;

    @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não deve conter espaços.")
    @Schema(example = "GabrielDias", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo [email] deve conter um email válido.")
    @Schema(example = "gabriel.diasramos2309@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Length(min = 10, max = 100, message = "O campo [password] deve conter entre 10 e 100 caracteres.")
    @Schema(example = "Gabriel@123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    private String website;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
