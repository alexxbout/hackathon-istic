package fr.istic.app.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nom;

    @NotNull
    private String email_user;

    private Integer experience;

    @OneToMany(mappedBy = "pool")
    private List<MatchPoolCompetence> competences;

}
