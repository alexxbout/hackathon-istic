package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.ProfilExistsInReservationException;
import fr.istic.app.matchProfilCompetence.persistence.jpa.MatchProfilCompetenceRepository;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import fr.istic.app.reservation.persistence.jpa.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class DeleteProfilUseCase {

    private final ProfilRepository profilRepository;
    private final ReservationRepository reservationRepository;
    private final MatchProfilCompetenceRepository matchProfilCompetenceRepository;

    @Transactional
    public void delete(long id) {
        if (reservationRepository.existsByProfilId(id)) {
            var profile = profilRepository.findById(id).orElseThrow();
            throw new ProfilExistsInReservationException(profile.getNom(), profile.getPrenom());
        }
        matchProfilCompetenceRepository.deleteAllByProfilId(id);
        profilRepository.deleteById(id);
    }
}
