package fr.istic.app.competence.domain.model;

import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder(toBuilder = true)
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