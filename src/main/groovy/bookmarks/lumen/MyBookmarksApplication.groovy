package bookmarks.lumen

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Slf4j
@EnableScheduling
@SpringBootApplication
class MyBookmarksApplication {
	static void main(String[] args) {
		Flyway flyway = Flyway
				.configure()
				.dataSource("jdbc:sqlite:lumen.sqlite", "admin", "admin").load()
		flyway.migrate()


		log.trace("Entering Log4j Example.");

		SpringApplication app = new SpringApplication(MyBookmarksApplication.class);
		app.setDefaultProperties(Collections
		  .singletonMap("server.port", "8080"));
		app.run(MyBookmarksApplication.class, args);
	}




}
@Configuration
public class DevConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200");
			}
		};
	}

}
