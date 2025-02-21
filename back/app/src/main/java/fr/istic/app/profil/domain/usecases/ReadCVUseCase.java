package fr.istic.app.profil.domain.usecases;

import fr.istic.app.common.domain.exception.BasicException;
import fr.istic.app.common.domain.exception.enums.ExceptionMessages;
import fr.istic.app.profil.domain.model.CV;
import fr.istic.app.profil.persistence.jpa.CVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadCVUseCase {

    private final CVRepository cvRepository;

    public CV read(Long profilId) {
        return cvRepository.findByProfilId(profilId)
                .orElseThrow(() -> new BasicException(String.format(ExceptionMessages.CV_NOT_FOUND.getMessage(), profilId)));
    }
}
