package fr.istic.app.matchProfilCompetence.domain.model;

import fr.istic.app.competence.domain.model.Competence;
import fr.istic.app.profil.domain.model.Profil;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
public class MatchProfilCompetence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profil_id")
    private Profil profil;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;
}
