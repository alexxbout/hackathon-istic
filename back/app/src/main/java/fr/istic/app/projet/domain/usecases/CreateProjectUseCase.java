package fr.istic.app.projet.domain.usecases;

import fr.istic.app.projet.domain.model.Projet;
import fr.istic.app.projet.persistence.jpa.ProjetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class CreateProjectUseCase {

    private final ProjetRepository projetRepository;

    @Transactional
    public Projet create(CreateProjetDto createProjetDto) {
        Projet projet = new Projet();
        projet.setNom(createProjetDto.nom);
        projet.setUserMail(createProjetDto.userMail);
        projet.setDateDebut(createProjetDto.dateDebut);
        projet.setDateFin(createProjetDto.dateFin);
        return this.projetRepository.save(projet);
    }

    public record CreateProjetDto(String nom,
                                  String userMail,
                                  LocalDate dateDebut,
                                  LocalDate dateFin) {
    }
}
