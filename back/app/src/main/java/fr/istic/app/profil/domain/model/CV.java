package fr.istic.app.profil.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cv")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_data", nullable = false)
    @Lob // Objet volumineux
    private byte[] fileData;

    @OneToOne(mappedBy = "cv", fetch = FetchType.LAZY)
    private Profil profil;
}
