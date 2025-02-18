package fr.istic.app.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nom;

    @OneToMany(mappedBy = "competence")
    private List<MatchProfilCompetence> profils;

    @OneToMany(mappedBy = "competence")
    private List<MatchPoolCompetence> pools;
}