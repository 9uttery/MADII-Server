package com.guttery.madii.common.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NcpObjectStorageClientConfig {
  private static final String END_POINT = "https://kr.object.ncloudstorage.com";
  private static final String REGION_NAME = "kr-standard";

  @Value("${ncp.config.access-key}")
  private String accessKey;

  @Value("${ncp.config.secret-key}")
  private String secretKey;

  @Bean
  public AmazonS3 ncpObjectStorageClient() {
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(END_POINT, REGION_NAME))
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
        .build();
  }

}