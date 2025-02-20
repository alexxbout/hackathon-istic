package fr.istic.app.reservation.api.dtos;

import fr.istic.app.profil.api.dtos.ProfilDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long id;
    private Long projetId;
    private ProfilDto profil;
    private String dateDebut;
    private String dateFin;

}
