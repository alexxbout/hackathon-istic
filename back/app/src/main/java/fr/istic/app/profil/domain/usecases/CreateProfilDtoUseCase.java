package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.matchProfilCompetence.persistence.jpa.MatchProfilCompetenceRepository;
import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import fr.istic.app.siteAddr.domain.usecases.CreateSiteAddrUseCase;
import fr.istic.app.siteAddr.persistence.jpa.SiteAddrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateProfilDtoUseCase {

    private final ProfilRepository profilRepository;
    private final SiteAddrRepository siteAddrRepository;
    private final CompetenceRepository competenceRepository;
    private final MatchProfilCompetenceRepository matchProfilCompetenceRepository;

    private final CreateSiteAddrUseCase createSiteAddrUseCase;

    @Transactional
    public Profil create(CreateProfilDto dto) {
        //TODO add validation

        var siteAddr = this.siteAddrRepository
                .findByVille(dto.ville)
                .orElseGet(() -> this.createSiteAddrUseCase.create(new CreateSiteAddrUseCase.CreateSiteAddrDto(dto.ville)));

        Profil profil = Profil.builder()
                .nom(dto.nom)
                .prenom(dto.prenom)
                .experience(dto.experience)
                .cvUrl(dto.cvUrl)
                .siteAddr(siteAddr)
                .build();

        var competences = dto.competenceIds
                .stream()
                .map(id -> this.competenceRepository.findById(id).orElseThrow(() -> new NotFoundException("Competence", id)))
                .toList();

        var competenceProfilRelations = competences
                .stream()
                .map(competence ->
                        MatchProfilCompetence.builder()
                                .profil(profil)
                                .competence(competence)
                                .build()
                )
                .toList();

        var saved = this.profilRepository.save(profil);
        saved.setCompetences(competenceProfilRelations);

        this.matchProfilCompetenceRepository.saveAll(competenceProfilRelations);

        return saved;
    }

    public record CreateProfilDto(
            String nom,
            String prenom,
            Integer experience,
            String cvUrl,
            List<Long> competenceIds,
            String ville
    ) {
    }
}
