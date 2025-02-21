package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.competence.persistence.jpa.CompetenceRepository;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import fr.istic.app.siteAddr.domain.usecases.CreateSiteAddrUseCase;
import fr.istic.app.siteAddr.persistence.jpa.SiteAddrRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UpdateProfilUseCase {

    private final ProfilRepository profilRepository;
    private final CompetenceRepository competenceRepository;
    private final SiteAddrRepository siteAddrRepository;
    private final CreateSiteAddrUseCase createSiteAddrUseCase;

    @Transactional
    public Profil update(Long id, UpdateProfilUseCaseDto dto) {
        var profil = profilRepository.findById(id).orElseThrow(() -> new NotFoundException("Profil", id));

        var siteAddr = this.siteAddrRepository
                .findByVille(dto.ville)
                .orElse(this.createSiteAddrUseCase.create(new CreateSiteAddrUseCase.CreateSiteAddrDto(dto.ville)));

        profil.setNom(dto.nom);
        profil.setPrenom(dto.prenom);
        profil.setExperience(dto.experience);
        profil.setSiteAddr(siteAddr);

        var profilCompetenceIds = profil.getCompetences()
                .stream()
                .map(competence -> competence.getCompetence().getId())
                .toList();

        // Get all competences that are in the profil but not in the new competences list
        var competencesToRemove = profilCompetenceIds
                .stream()
                .filter(competenceId -> !dto.competenceIds.contains(competenceId))
                .map(competenceId -> competenceRepository.findById(competenceId).orElseThrow(() -> new NotFoundException("Competence", competenceId)))
                .toList();

        // Get all competences that are in the new competences list but not in the profil
        var competencesToAdd = dto.competenceIds
                .stream()
                .filter(competenceId -> !profilCompetenceIds.contains(competenceId))
                .map(competenceId -> competenceRepository.findById(competenceId).orElseThrow(() -> new NotFoundException("Competence", competenceId)))
                .toList();

        // Remove competences that are in the profil but not in the new competences list
        var newCompetenceProfilRelationList = profil.getCompetences()
                .stream()
                .filter(matchProfilCompetence -> !competencesToRemove.contains(matchProfilCompetence.getCompetence()))
                .collect(Collectors.toList());

        // Add competences that are in the new competences list but not in the profil
        newCompetenceProfilRelationList.addAll(
                competencesToAdd
                        .stream()
                        .map(competence ->
                                MatchProfilCompetence
                                        .builder()
                                        .profil(profil)
                                        .competence(competence)
                                        .build()
                        )
                        .toList()
        );

        profil.setCompetences(newCompetenceProfilRelationList);

        return profilRepository.save(profil);
    }

    public record UpdateProfilUseCaseDto(
            String nom,
            String prenom,
            Integer experience,
            List<Long> competenceIds,
            String ville
    ) {
    }
}