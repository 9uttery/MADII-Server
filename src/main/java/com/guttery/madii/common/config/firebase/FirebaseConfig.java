package com.guttery.madii.common.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.config}")
    private String firebaseKeyFilePath;
    @Value("${firebase.project-id}")
    private String firebaseProjectId;

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @Bean
    public FirebaseApp firebaseApp(GoogleCredentials credentials) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    GoogleCredentials googleCredentials() {
        try (final InputStream serviceAccount = new ClassPathResource(firebaseKeyFilePath).getInputStream();) {
            return GoogleCredentials.fromStream(serviceAccount);
        } catch (final Exception e) {
            throw CustomException.of(ErrorDetails.FIREBASE_INTEGRATION_FAILED);
        }
    }
}
