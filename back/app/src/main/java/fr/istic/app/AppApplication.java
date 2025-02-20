package fr.istic.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages =
		{
				"fr.istic.app.competence.persistence.jpa",
				"fr.istic.app.profil.persistence.jpa",
				"fr.istic.app.siteAddr.persistence.jpa",
				"fr.istic.app.matchProfilCompetence.persistence.jpa",
				"fr.istic.app.matchPoolCompetence.persistence.jpa",
				"fr.istic.app.projet.persistence.jpa",
				"fr.istic.app.pool.persistence.jpa",
				"fr.istic.app.reservation.persistence.jpa"
		})
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
