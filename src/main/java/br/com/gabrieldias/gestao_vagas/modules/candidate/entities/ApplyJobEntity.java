package br.com.gabrieldias.gestao_vagas.modules.candidate.entities;

import br.com.gabrieldias.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_jobs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_candidate", insertable = false, updatable = false)
    private CandidateEntity candidateEntity;

    @ManyToOne
    @JoinColumn(name = "id_job", insertable = false, updatable = false)
    private JobEntity jobEntity;

    @Column(name = "id_candidate")
    private UUID idCandidate;

    @Column(name = "id_job")
    private UUID idJob;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
