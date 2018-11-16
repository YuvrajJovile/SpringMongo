package com.mongo.mongo.config;

import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDbConfig {

    @Bean
    CommandLineRunner lCommandLineRunner(UserRepository pUserRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                pUserRepository.save(new UsersDao(1, "yuvi", "java", 1000.00));
                pUserRepository.save(new UsersDao(2, "ishu", "java", 1000.00));
            }
        };
    }
}
