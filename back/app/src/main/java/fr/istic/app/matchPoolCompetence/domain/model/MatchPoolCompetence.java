package fr.istic.app.matchPoolCompetence.domain.model;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.pool.domain.model.Pool;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
public class MatchPoolCompetence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool pool;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;
}
