package fr.istic.app.siteAddr.domain.usecases;

import fr.istic.app.siteAddr.domain.model.SiteAddr;
import fr.istic.app.siteAddr.persistence.jpa.SiteAddrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateSiteAddrUseCase {

    private final SiteAddrRepository siteAddrRepository;

    @Transactional
    public SiteAddr create(CreateSiteAddrDto dto) {
        //TODO add validation

        SiteAddr siteAddr = SiteAddr.builder()
            .ville(dto.ville)
            .build();

        return this.siteAddrRepository.save(siteAddr);
    }

    public record CreateSiteAddrDto(
        String ville
    ) { }
}
