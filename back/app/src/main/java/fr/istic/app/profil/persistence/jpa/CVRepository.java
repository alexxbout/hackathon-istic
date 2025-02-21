package fr.istic.app.profil.persistence.jpa;

import fr.istic.app.profil.domain.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {

    Optional<CV> findByProfilId(Long profilId);
}
