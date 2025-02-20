package fr.istic.app.pool.domain.model;

import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nom;

    @Column(name = "user_mail")
    @NotBlank
    private String userMail;

    @Column
    private Integer experience;

    @OneToMany(mappedBy = "pool")
    private List<MatchPoolCompetence> competences;

}
