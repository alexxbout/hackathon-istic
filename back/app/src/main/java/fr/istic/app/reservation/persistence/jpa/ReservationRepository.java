package fr.istic.app.reservation.persistence.jpa;

import fr.istic.app.reservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.profil.id = :profilId
            AND (
               (:newStartDate BETWEEN r.dateDebut AND r.dateFin)
               OR (:newEndDate BETWEEN r.dateDebut AND r.dateFin)
                OR (r.dateDebut BETWEEN :newStartDate AND :newEndDate)
                OR (r.dateFin BETWEEN :newStartDate AND :newEndDate)
            )
            """)
    List<Reservation> findOverlappingReservationsByProfilId(
            @Param("profilId") Long profilId,
            @Param("newStartDate") LocalDate newStartDate,
            @Param("newEndDate") LocalDate newEndDate);

    List<Reservation> findAllByProfilId(Long profilId);

    boolean existsByProfilId(Long profilId);

}
