package fr.istic.app.competence.persistence.jpa;

import fr.istic.app.competence.domain.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
}
