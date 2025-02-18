package fr.istic.app.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
