package bookmarks.lumen

import groovy.util.logging.Slf4j
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
		log.info("hello");

		SpringApplication app = new SpringApplication(MyBookmarksApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8080"));
		app.run(MyBookmarksApplication.class, args);
	}
}
