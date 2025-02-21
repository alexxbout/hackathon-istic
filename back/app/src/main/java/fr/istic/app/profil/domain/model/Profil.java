package fr.istic.app.profil.domain.model;

import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.siteAddr.domain.model.SiteAddr;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    private CV cv;

    @ManyToOne
    @JoinColumn(name = "site_id")
    @NotNull
    private SiteAddr siteAddr;

    @OneToMany(mappedBy = "profil")
    private List<MatchProfilCompetence> competences;

}
