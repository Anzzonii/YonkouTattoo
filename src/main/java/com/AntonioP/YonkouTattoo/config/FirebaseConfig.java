package com.AntonioP.YonkouTattoo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CLASE DE CONFIGURACIÓN DE FIREBASE QUE INICIA EL SERVICIO DE LA CUENTA A TRAVÉS DE LAS PROPIEDADES MARCADAS EN EL ARCHIVO serviceAccountKey.json
 */
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = getClass().getResourceAsStream("/firebase/serviceAccountKey.json");

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo inicializar Firebase", e);
        }
    }
}
