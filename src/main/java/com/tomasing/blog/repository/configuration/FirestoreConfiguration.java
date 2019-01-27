package com.tomasing.blog.repository.configuration;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirestoreConfiguration {

    @Bean
    public Firestore firestore(CredentialsProvider googleCredentials) throws IOException {
        ServiceAccountCredentials credentials = (ServiceAccountCredentials) googleCredentials.getCredentials();
        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setTimestampsInSnapshotsEnabled(true)
                .setCredentialsProvider(googleCredentials)
                .setProjectId(credentials.getProjectId())
                .build();

        return options.getService();
    }
}
