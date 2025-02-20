package fr.istic.app.siteAddr.persistence.jpa;

import fr.istic.app.siteAddr.domain.model.SiteAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteAddrRepository extends JpaRepository<SiteAddr, Long> {

    Optional<SiteAddr> findByVille(String ville);

}
