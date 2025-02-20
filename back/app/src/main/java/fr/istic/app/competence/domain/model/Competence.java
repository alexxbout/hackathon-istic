package fr.istic.app.competence.domain.model;

import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nom;

    @OneToMany(mappedBy = "competence")
    private List<MatchProfilCompetence> profils;

    @OneToMany(mappedBy = "competence")
    private List<MatchPoolCompetence> pools;
}