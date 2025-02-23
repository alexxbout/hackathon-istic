package fr.istic.app.matchProfilCompetence.persistence.jpa;

import fr.istic.app.matchProfilCompetence.domain.model.MatchProfilCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchProfilCompetenceRepository extends JpaRepository<MatchProfilCompetence, Long> {

    void deleteAllByProfilId(Long profilId);
}
