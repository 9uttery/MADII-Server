package com.guttery.madii.common.config.mongo;

import com.guttery.madii.common.converter.StringToYearMonthConverter;
import com.guttery.madii.common.converter.YearMonthToStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new StringToYearMonthConverter(),
                new YearMonthToStringConverter()
        ));
    }
}
