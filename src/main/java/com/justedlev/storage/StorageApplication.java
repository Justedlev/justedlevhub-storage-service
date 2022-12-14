package com.justedlev.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.justedlev.storage.properties.StorageProperties;
import com.justedlev.storage.properties.ServiceProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
		StorageProperties.class,
		ServiceProperties.class
})
public class StorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}
}
