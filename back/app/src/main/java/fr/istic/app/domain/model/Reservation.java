package fr.istic.app.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @ManyToOne
   @JoinColumn(name = "profil_id")
   private Profil profil;

   @NotNull
   private Date date_debut;

   @NotNull
   private Date date_fin;

   @ManyToOne
   @JoinColumn(name = "id_projet")
   private Projet projet;
}
