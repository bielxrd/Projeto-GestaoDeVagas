package br.com.gabrieldias.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_jobs")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String benefits;
    private String level;

    @ManyToOne()
    @JoinColumn(name = "id_company", insertable = false, updatable = false)
    private CompanyEntity companyEntity;

    @Column(name = "id_company")
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
