package fr.istic.app.pool.api.dtos;

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
public class PoolDto {
    private Long id;
    private String nom;
    private Integer experience;
    private List<CompetenceDto> competences;
}
