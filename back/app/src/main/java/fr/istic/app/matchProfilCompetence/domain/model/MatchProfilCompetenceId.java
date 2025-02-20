package fr.istic.app.matchProfilCompetence.domain.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchProfilCompetenceId implements Serializable {
    private Long profilId;
    private Long competenceId;
}