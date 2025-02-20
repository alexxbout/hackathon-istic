package fr.istic.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.directory.Attributes;
import java.util.List;

@SpringBootApplication
public class AuthApplication {

	@Bean
	public CommandLineRunner testLdapConnection(LdapTemplate ldapTemplate) {
		return args -> {
			// Perform a search for all entries under dc=example,dc=com
			List<String> results = ldapTemplate.search(
					"",
					"(objectClass=*)",
					(AttributesMapper<String>) (Attributes attrs) -> {
						// Return the DN or any attribute you like (here we try to get 'cn')
						return attrs.get("objectClass") != null ? (String) attrs.get("objectClass").get() : "no cn";
					}
			);
			System.out.println("LDAP search result: " + results);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
