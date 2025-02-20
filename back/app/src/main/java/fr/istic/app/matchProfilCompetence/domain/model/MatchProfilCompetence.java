package fr.istic.app.matchProfilCompetence.domain.model;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.profil.domain.model.Profil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
public class MatchProfilCompetence {
    @EmbeddedId
    private MatchProfilCompetenceId id;

    @ManyToOne
    @MapsId("profilId")
    @JoinColumn(name = "profil_id")
    private Profil profil;

    @ManyToOne
    @MapsId("competenceId")
    @JoinColumn(name = "competence_id")
    private Competence competence;
}
