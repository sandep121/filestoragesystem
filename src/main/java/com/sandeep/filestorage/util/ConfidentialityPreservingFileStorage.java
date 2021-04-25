package com.sandeep.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ConfidentialityPreservingFileStorage {
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Bean(name = "dataSource")
//    public DriverManagerDataSource dataSource() {
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//
//        Resource initSchema = new ClassPathResource("shema-data.sql");
//        Resource initData = new ClassPathResource("data.sql");
//        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
//        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
//        System.err.println("OM Namah Shivay");
//
//        return dataSource;
//    }
    public static void main(String[] args) {
        SpringApplication.run(ConfidentialityPreservingFileStorage.class, args);
    }

}
