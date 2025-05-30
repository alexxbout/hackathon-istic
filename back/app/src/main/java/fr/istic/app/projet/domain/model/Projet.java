package fr.istic.app.projet.domain.model;

import fr.istic.app.reservation.domain.model.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String nom;

    @Column(name = "user_mail")
    @NotBlank
    private String userMail;

    @Column(name = "date_debut")
    @NotNull
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    @NotNull
    private LocalDate dateFin;

    @OneToMany(mappedBy = "projet", fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
