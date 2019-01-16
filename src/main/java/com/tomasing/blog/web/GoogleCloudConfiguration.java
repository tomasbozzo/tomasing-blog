package com.tomasing.blog.web;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConfigurationProperties("google.cloud.credentials")
public class GoogleCloudConfiguration {

    private String clientId;
    private String clientEmail;
    private String privateKeyId;
    private String privateKey;

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        return ServiceAccountCredentials.fromPkcs8(
                clientId,
                clientEmail,
                privateKey,
                privateKeyId,
                null);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setPrivateKeyId(String privateKeyId) {
        this.privateKeyId = privateKeyId;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey.replace("\\n", "\n");
    }
}
