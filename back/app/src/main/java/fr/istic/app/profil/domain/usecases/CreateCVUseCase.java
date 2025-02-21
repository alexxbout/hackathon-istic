package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.profil.domain.model.CV;
import fr.istic.app.profil.persistence.jpa.CVRepository;
import fr.istic.app.profil.persistence.jpa.ProfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CreateCVUseCase {

    private final CVRepository cvRepository;
    private final ProfilRepository profilRepository;

    public CV create(MultipartFile file, Long profilId) throws IOException {

        var profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new NotFoundException("Profil", profilId));

        var cv = cvRepository.findByProfilId(profilId)
                .orElseGet(() -> {
                    try {
                        return CV.builder()
                                .profil(profil)
                                .fileData(file.getBytes())
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        if(cv.getId() != null) {
            cv.setFileData(file.getBytes());
        }

        return cvRepository.save(cv);
    }
}
