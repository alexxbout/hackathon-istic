package fr.istic.app.projet.persistence.jpa;

import fr.istic.app.projet.domain.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    @Query("SELECT p FROM Projet p WHERE p.userMail = :mail")
    List<Projet> findProjetsByUserMail(@Param("mail") String mail);

}
