# Tomasing Blog (backend)
The blog application with Spring Boot used by tomasing.com for the backend.

## Configuration
The application persists to Firestore in Google Cloud Platform,
thus requires it's credentials to be configured.

Since the application relies on Spring Boot, it uses the
Spring Cloud GCP starter to ease configuration.
Please see the [official documentation](https://cloud.spring.io/spring-cloud-static/spring-cloud-gcp/1.1.0.RELEASE/single/spring-cloud-gcp.html#_credentials)
for more information on how to configure the credentials. 