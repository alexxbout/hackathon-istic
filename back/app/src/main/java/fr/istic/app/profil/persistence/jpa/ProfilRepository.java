package fr.istic.app.profil.persistence.jpa;

import fr.istic.app.profil.domain.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            JOIN (
                SELECT mpc.profil.id
                FROM MatchProfilCompetence mpc
                WHERE mpc.competence.id IN :competenceIds
                GROUP BY mpc.profil.id
                HAVING COUNT(DISTINCT mpc.competence.id) >= :#{#competenceIds.size()}
            ) matched_profiles ON p.id = matched_profiles.id
            WHERE p.experience >= :experience
            AND NOT EXISTS (
                SELECT 1 FROM Reservation r
                WHERE r.profil.id = p.id
                AND (
                    r.dateDebut <= :dateFin
                    AND r.dateFin >= :dateDebut
                )
            )
            """)
    List<Profil> search(List<Long> competenceIds, Integer experience, LocalDate dateDebut, LocalDate dateFin);

    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            JOIN (
                SELECT mpc.profil.id
                FROM MatchProfilCompetence mpc
                WHERE mpc.competence.id IN :competenceIds
                GROUP BY mpc.profil.id
                HAVING COUNT(DISTINCT mpc.competence.id) >= :#{#competenceIds.size()}
            ) matched_profiles ON p.id = matched_profiles.id
            WHERE p.experience >= :experience
            """)
    List<Profil> search(List<Long> competenceIds, Integer experience);

    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            JOIN (
                SELECT mpc.profil.id
                FROM MatchProfilCompetence mpc
                WHERE mpc.competence.id IN :competenceIds
                GROUP BY mpc.profil.id
                HAVING COUNT(DISTINCT mpc.competence.id) >= :#{#competenceIds.size()}
            ) matched_profiles ON p.id = matched_profiles.id
            AND NOT EXISTS (
                SELECT 1 FROM Reservation r
                WHERE r.profil.id = p.id
                AND (
                    r.dateDebut <= :dateFin
                    AND r.dateFin >= :dateDebut
                )
            )
            """)
    List<Profil> search(List<Long> competenceIds, LocalDate dateDebut, LocalDate dateFin);

    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            WHERE p.experience >= :experience
            AND NOT EXISTS (
                SELECT 1 FROM Reservation r
                WHERE r.profil.id = p.id
                AND (
                    r.dateDebut <= :dateFin
                    AND r.dateFin >= :dateDebut
                )
            )
            """)
    List<Profil> search(Integer experience, LocalDate dateDebut, LocalDate dateFin);

    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            JOIN (
                SELECT mpc.profil.id
                FROM MatchProfilCompetence mpc
                WHERE mpc.competence.id IN :competenceIds
                GROUP BY mpc.profil.id
                HAVING COUNT(DISTINCT mpc.competence.id) >= :#{#competenceIds.size()}
            ) matched_profiles ON p.id = matched_profiles.id
            """)
    List<Profil> search(List<Long> competenceIds);

    @Query("""
            SELECT p FROM Profil p
            WHERE p.experience >= :experience
            """)
    List<Profil> search(Integer experience);

    @Query("""
            SELECT DISTINCT p
            FROM Profil p
            WHERE NOT EXISTS (
                SELECT 1 FROM Reservation r
                WHERE r.profil.id = p.id
                AND (
                    r.dateDebut <= :dateFin
                    AND r.dateFin >= :dateDebut
                )
            )
            """)
    List<Profil> search(LocalDate dateDebut, LocalDate dateFin);
}
