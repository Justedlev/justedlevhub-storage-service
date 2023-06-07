package com.justedlev.storage.configuration;

import com.justedlev.storage.properties.JStorageFeignClientProperties;
import com.justedlev.storage.properties.JStorageProperties;
import com.justedlev.storage.properties.ServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JStorageProperties.class,
        ServiceProperties.class,
        JStorageFeignClientProperties.class
})
public class PropertiesConfiguration {
}
