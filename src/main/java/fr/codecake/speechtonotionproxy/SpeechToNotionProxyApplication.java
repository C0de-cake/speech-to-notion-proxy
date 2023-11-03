package fr.codecake.speechtonotionproxy;

import fr.codecake.speechtonotionproxy.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class SpeechToNotionProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeechToNotionProxyApplication.class, args);
	}

}
