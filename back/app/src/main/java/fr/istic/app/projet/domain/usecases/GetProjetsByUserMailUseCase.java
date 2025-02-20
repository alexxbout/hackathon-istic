package fr.istic.app.projet.domain.usecases;

import fr.istic.app.projet.domain.model.Projet;
import fr.istic.app.projet.persistence.jpa.ProjetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class GetProjetsByUserMailUseCase {

    private final ProjetRepository projetRepository;

    @Transactional
    public List<Projet> getProjetsByUserMail(String user_mail) {
        return this.projetRepository.findProjetsByUserMail(user_mail);
    }
}
