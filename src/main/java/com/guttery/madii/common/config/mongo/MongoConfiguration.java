package com.guttery.madii.common.config.mongo;

import com.guttery.madii.common.converter.StringToYearMonthConverter;
import com.guttery.madii.common.converter.YearMonthToStringConverter;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = "com.guttery.madii")
public class MongoConfiguration {
    private final String mongoDbUri;
    private final String mongoDbName;

    public MongoConfiguration(
            @Value("${spring.data.mongodb.uri}") String mongoDbUri,
            @Value("${spring.data.mongodb.database}") String mongoDbName // 이 부분을 설정 파일에서 주입받도록 변경
    ) {
        this.mongoDbUri = mongoDbUri;
        this.mongoDbName = mongoDbName;
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new StringToYearMonthConverter(),
                new YearMonthToStringConverter()
        ));
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            final MongoDatabaseFactory mongoDatabaseFactory,
            final MongoMappingContext context,
            final MongoCustomConversions mongoCustomConversions
    ) {
        final DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        final MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(mongoCustomConversions);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); // to remove _class

        return mappingConverter;
    }

    @Bean
    public MongoClient mongo() {
        final ConnectionString connectionString = new ConnectionString(mongoDbUri);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(final MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoDbName);
    }

    @Bean
    public MongoTemplate mongoTemplate(final MongoDatabaseFactory mongoDatabaseFactory, final MappingMongoConverter mappingMongoConverter) {
        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }
}
