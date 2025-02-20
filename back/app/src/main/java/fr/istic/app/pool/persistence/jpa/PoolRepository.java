package fr.istic.app.pool.persistence.jpa;

import fr.istic.app.pool.domain.model.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {
    List<Pool> findByUserMail(String userMail);
}
