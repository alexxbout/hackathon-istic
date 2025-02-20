package fr.istic.app.reservation.domain.model;

import fr.istic.app.profil.domain.model.Profil;
import fr.istic.app.projet.domain.model.Projet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "profil_id")
   private Profil profil;

   @Column(name = "date_debut")
   @NotNull
   private LocalDate dateDebut;

   @Column(name = "date_fin")
   @NotNull
   private LocalDate dateFin;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_projet")
   private Projet projet;
}
