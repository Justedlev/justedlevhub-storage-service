package com.justedlev.service.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.justedlev.service.storage.configuration.properties.JStorageProperties;
import com.justedlev.service.storage.configuration.properties.ServiceProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		JStorageProperties.class,
		ServiceProperties.class
})
public class JStorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(JStorageApplication.class, args);
	}
}
