package com.guttery.madii.common.config.mongo;

import com.guttery.madii.common.converter.StringToYearMonthConverter;
import com.guttery.madii.common.converter.YearMonthToStringConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfiguration {
    private static final String MONGO_DB_NAME = "madii";

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUrl;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(mongoDbUrl);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, MONGO_DB_NAME);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new StringToYearMonthConverter(),
                new YearMonthToStringConverter()
        ));
    }
}
