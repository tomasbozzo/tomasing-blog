package com.tomasing.blog.repository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirestoreConfiguration {

    @Value("${google.cloud.projectId}")
    private String projectId;

    @Bean
    public Firestore firestore(GoogleCredentials googleCredentials) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(googleCredentials)
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);

        return FirestoreClient.getFirestore();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
