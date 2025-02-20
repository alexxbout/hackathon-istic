package fr.istic.app.profil.persistence.jpa;

import fr.istic.app.profil.domain.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
}
