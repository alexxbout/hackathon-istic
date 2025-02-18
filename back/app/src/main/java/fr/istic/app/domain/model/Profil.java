package fr.istic.app.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private Integer experience;

    @NotNull
    private String cv_url;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site_addr siteAddr;

    @OneToMany(mappedBy = "profil")
    private List<MatchProfilCompetence> competences;

}
