package com.justedlev.storage;

import com.justedlev.storage.client.configuration.JStorageFeignClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.properties.ServiceProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableConfigurationProperties({
		JStorageProperties.class,
		ServiceProperties.class,
		JStorageFeignClientProperties.class
})
public class JStorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(JStorageApplication.class, args);
	}
}
