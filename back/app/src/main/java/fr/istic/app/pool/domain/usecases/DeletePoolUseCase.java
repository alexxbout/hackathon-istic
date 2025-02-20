package fr.istic.app.pool.domain.usecases;

import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePoolUseCase {

    private final PoolRepository poolRepository;

    public void delete(Long id) {
        this.poolRepository.deleteById(id);
    }
}
