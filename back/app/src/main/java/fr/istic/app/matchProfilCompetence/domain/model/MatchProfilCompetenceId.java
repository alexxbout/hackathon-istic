package fr.istic.app.matchProfilCompetence.domain.model;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchProfilCompetenceId implements Serializable {
    private Long profilId;
    private Long competenceId;
}