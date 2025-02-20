package fr.istic.app.matchProfilCompetence.persistence.jpa;

import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetenceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchProfilCompetenceRepository extends JpaRepository<MatchProfilCompetence, MatchProfilCompetenceId> {

    void deleteByProfilId(Long profilId);
}
