package com.guttery.madii.common.config.mongo;

import com.guttery.madii.common.converter.StringToYearMonthConverter;
import com.guttery.madii.common.converter.YearMonthToStringConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfiguration {
    private static final String MONGO_DB_NAME = "madii";

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUrl;

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new StringToYearMonthConverter(),
                new YearMonthToStringConverter()
        ));
    }
}
