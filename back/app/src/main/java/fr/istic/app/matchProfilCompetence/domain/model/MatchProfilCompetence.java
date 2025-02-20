package fr.istic.app.matchProfilCompetence.domain.model;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.profil.domain.model.Profil;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    // 🔹 Ajout d'un constructeur qui initialise l'ID composite
    public MatchProfilCompetence(Profil profil, Competence competence) {
        this.id = new MatchProfilCompetenceId(profil.getId(), competence.getId()); // Initialisation correcte
        this.profil = profil;
        this.competence = competence;
    }
}
