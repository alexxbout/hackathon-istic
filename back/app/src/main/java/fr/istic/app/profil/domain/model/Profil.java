package fr.istic.app.profil.domain.model;

import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.siteAddr.domain.model.SiteAddr;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nom;

    @Column
    @NotNull
    private String prenom;

    @Column
    private Integer experience;

    @Column(name = "cvUrl")
    @NotNull
    private String cvUrl;

    @ManyToOne
    @JoinColumn(name = "site_id")
    @NotNull
    private SiteAddr siteAddr;

    @OneToMany(mappedBy = "profil")
    private List<MatchProfilCompetence> competences;

}
