package com.guttery.madii.common.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig implements InitializingBean {
    @Value("${firebase.config}")
    private String firebaseKeyFilePath;
    @Value("${firebase.project-id}")
    private String firebaseProjectId;

    @Override
    public void afterPropertiesSet() {
        initialize();
    }

    private void initialize() {
        try (
                final InputStream serviceAccount = new ClassPathResource(firebaseKeyFilePath).getInputStream();
        ) {
            final FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(firebaseProjectId)
                    .build();


            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            throw CustomException.of(ErrorDetails.FIREBASE_INTEGRATION_FAILED);
        }
    }
}
