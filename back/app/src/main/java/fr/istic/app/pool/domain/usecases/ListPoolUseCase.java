package fr.istic.app.pool.domain.usecases;

import fr.istic.app.pool.domain.model.Pool;
import fr.istic.app.pool.persistence.jpa.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListPoolUseCase {

    private final PoolRepository poolRepository;

    public List<Pool> list() {
        return poolRepository.findAll();
    }

}
