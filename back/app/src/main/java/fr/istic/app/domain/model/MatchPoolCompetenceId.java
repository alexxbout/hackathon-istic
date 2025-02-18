package fr.istic.app.domain.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchPoolCompetenceId implements Serializable {
    private Integer poolId;
    private Integer competenceId;
}