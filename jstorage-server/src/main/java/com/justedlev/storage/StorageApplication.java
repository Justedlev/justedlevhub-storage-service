package com.justedlev.storage;

import com.justedlev.storage.client.configuration.JstorageFeignClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.justedlev.storage.properties.StorageProperties;
import com.justedlev.storage.properties.ServiceProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableConfigurationProperties({
		StorageProperties.class,
		ServiceProperties.class,
		JstorageFeignClientProperties.class
})
public class StorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}
}
