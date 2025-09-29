package edu.js.project.configure;
/*
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Component("rsaKeyGenerator")
public class RsaKeyGenerator {

    @PostConstruct
    public void init() throws Exception {
        try {
            // Path to the resource directory (where application.properties is)
            Path resourcePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources");

            Path privateKeyPath = resourcePath.resolve("private_key.pem");
            Path publicKeyPath = resourcePath.resolve("public_key.pem");

            // Check if key files already exist in src/main/resources
            if (Files.exists(privateKeyPath) && Files.exists(publicKeyPath)) {
                System.out.println("PEM key files already exist in src/main/resources. Skipping generation.");
                return;
            }

            System.out.println("Generating new RSA key pair and saving to src/main/resources...");

            // Create the resources directory if it doesn't exist
            Files.createDirectories(resourcePath);

            // Generate the RSA key pair
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();

            // Write the private key to private_key.pem
            writePemFile(keyPair, privateKeyPath, true);

            // Write the public key to public_key.pem
            writePemFile(keyPair, publicKeyPath, false);

            System.out.println("Successfully generated and saved key files:");
            System.out.println("  - " + privateKeyPath.toAbsolutePath());
            System.out.println("  - " + publicKeyPath.toAbsolutePath());

        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Error during RSA key generation: " + e.getMessage());
            // Optionally re-throw as a runtime exception to halt startup on failure
            throw new RuntimeException("Failed to generate and save RSA keys", e);
        }
    }

    /**
     * Writes a KeyPair to a PEM file.
     *
     * @param pair The KeyPair to write.
     * @param path The path to the output file.
     * @param isPrivate True to write the private key, false for the public key.
     * @throws IOException if an I/O error occurs.
     *//*
    private void writePemFile(KeyPair pair, Path path, boolean isPrivate) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
             PemWriter pemWriter = new PemWriter(writer)) {

            String type = isPrivate ? "PRIVATE KEY" : "PUBLIC KEY";
            byte[] content = isPrivate ? pair.getPrivate().getEncoded() : pair.getPublic().getEncoded();

            pemWriter.writeObject(new PemObject(type, content));
        }
    }
}*/





import jakarta.annotation.PostConstruct;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Generates and writes a new RSA key pair on every application startup.
 * This component is designed to overwrite existing keys, ensuring a fresh set
 * for each run. Configuration for key size and location is externalized
 * to application.properties for better flexibility and management.
 */
//@Component("rsaKeyGenerator")
public class RsaKeyGenerator {

    private static final Logger log = LoggerFactory.getLogger(RsaKeyGenerator.class);

    // Injects the key storage location from application.properties, with a default value.
    @Value("${app.security.rsa.keys.location:src/main/resources/keys}")
    private String keyLocation;

    // Injects the desired key size. 2048 is a secure minimum.
    @Value("${app.security.rsa.key-size:2048}")
    private int keySize;

    /**
     * This method is executed by Spring after the bean is constructed.
     * It orchestrates the generation and writing of the RSA key pair.
     */
    @PostConstruct
    public void generateKeys() {
        try {
            log.info("Starting RSA key pair generation. Keys will be overwritten if they exist.");
            log.debug("Key size: {}, Location: '{}'", keySize, keyLocation);

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            Path keyPath = Paths.get(keyLocation);
            Files.createDirectories(keyPath); // Ensure the directory exists

            writePemFile(keyPath, "private_key.pem", "RSA PRIVATE KEY", keyPair.getPrivate().getEncoded());
            writePemFile(keyPath, "public_key.pem", "PUBLIC KEY", keyPair.getPublic().getEncoded());

            log.info("✅ New RSA key pair successfully generated and saved to {}", keyPath.toAbsolutePath());

        } catch (NoSuchAlgorithmException | IOException e) {
            log.error("❌ Failed to generate or save RSA key pair.", e);
            // This exception will prevent the application from starting if key generation fails.
            throw new RuntimeException("Fatal error during RSA key generation.", e);
        }
    }

    /**
     * A helper method to write key data to a file in PEM format.
     *
     * @param keyPath The directory where the file will be saved.
     * @param filename The name of the file.
     * @param header The PEM header (e.g., "RSA PRIVATE KEY").
     * @param content The byte content of the key.
     * @throws IOException if an I/O error occurs during writing.
     */
    private void writePemFile(Path keyPath, String filename, String header, byte[] content) throws IOException {
        File keyFile = keyPath.resolve(filename).toFile();
        try (PemWriter writer = new PemWriter(new FileWriter(keyFile))) {
            PemObject pemObject = new PemObject(header, content);
            writer.writeObject(pemObject);
            log.debug("Successfully wrote PEM file: {}", keyFile.getAbsolutePath());
        }
    }
}

