package fr.istic.app.matchPoolCompetence.persistence.jpa;

import fr.istic.app.matchPoolCompetence.domain.model.MatchPoolCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchPoolCompetenceRepository extends JpaRepository<MatchPoolCompetence, Long> {

    void deleteAllByProfilId(Long profilId);
}
