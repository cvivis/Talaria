package com.hermes.talaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.hermes.talaria.global.property.CorsProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
	CorsProperties.class
})
public class TalariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalariaApplication.class, args);
	}

}
