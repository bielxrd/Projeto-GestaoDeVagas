package br.com.gabrieldias.gestao_vagas.modules.candidate.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

    private UUID id;
    private String name;

    @Pattern(regexp = "^[^\\s]+$", message = "O campo [username] não deve conter espaços.")
    private String username;

    @Email(message = "O campo [email] deve conter um email válido.")
    private String email;

    @Length(min = 10, max = 100, message = "O campo [password] deve conter entre 10 e 100 caracteres.")
    private String password;
    private String description;
    private String curriculum;


}
