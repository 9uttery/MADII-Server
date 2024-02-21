package com.guttery.madii.common.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.config}")
    private String firebaseKeyFilePath;
    @Value("${firebase.project-id}")
    private String firebaseProjectId;

    @PostConstruct
    public void initialize() {
        try (
                final InputStream serviceAccount = new FileInputStream(firebaseKeyFilePath)
        ) {
            final FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(firebaseProjectId)
                    .build();


            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (final Exception e) {
            throw CustomException.of(ErrorDetails.FIREBASE_INTEGRATION_FAILED);
        }
    }
}
