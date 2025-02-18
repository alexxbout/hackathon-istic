package fr.istic.app.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
