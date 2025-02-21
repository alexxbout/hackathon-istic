package fr.istic.app.profil.api.dtos;

import fr.istic.app.competence.api.dtos.CompetenceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilDto {

    private Long id;
    private String nom;
    private String prenom;
    private Integer experience;
    private List<CompetenceDto> competences;
    private Long cv_id;
    private String ville;


}
