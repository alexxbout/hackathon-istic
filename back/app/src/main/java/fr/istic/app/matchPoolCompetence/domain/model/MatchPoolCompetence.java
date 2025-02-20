package fr.istic.app.matchPoolCompetence.domain.model;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.pool.domain.model.Pool;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
public class MatchPoolCompetence {
    @EmbeddedId
    private MatchPoolCompetenceId id;

    @ManyToOne
    @MapsId("poolId")
    @JoinColumn(name = "pool_id")
    private Pool pool;

    @ManyToOne
    @MapsId("competenceId")
    @JoinColumn(name = "competence_id")
    private Competence competence;
}
