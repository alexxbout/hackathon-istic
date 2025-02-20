package fr.istic.app.pool.domain.usecases;

import fr.istic.app.common.domain.exception.NotFoundException;
import fr.istic.app.pool.domain.model.Pool;
import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadPoolUseCase {

    private final PoolRepository poolRepository;

    public Pool read(Long id) {
        return poolRepository.findById(id).orElseThrow(() -> new NotFoundException("Pool", id));
    }
}
