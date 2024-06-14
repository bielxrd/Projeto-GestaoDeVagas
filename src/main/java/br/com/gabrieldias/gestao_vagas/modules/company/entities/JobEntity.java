package br.com.gabrieldias.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Esse campo é obrigatório.")
    @Schema(example = "Vaga para fullstack")
    private String description;
    @Schema(example = "GYM PASS, VR, VA, VT, Plano de saúde")
    private String benefits;

    @NotBlank(message = "Esse campo é obrigatório.")
    @Schema(example = "SENIOR")
    private String level;

    @ManyToOne()
    @JoinColumn(name = "id_company", insertable = false, updatable = false)
    private CompanyEntity companyEntity;


    @Column(name = "id_company", insertable = true, updatable = true, nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
