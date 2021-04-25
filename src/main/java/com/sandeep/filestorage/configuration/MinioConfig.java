package com.sandeep.filestorage.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${spring.minio.access-key}")
    String accessKey;
    @Value("${spring.minio.secret-key}")
    String secretKey;
    @Value("${spring.minio.url}")
    String minioUrl;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient client = new MinioClient.Builder()
                    .endpoint(minioUrl)
                    .credentials(accessKey,secretKey)
                    .build();
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
