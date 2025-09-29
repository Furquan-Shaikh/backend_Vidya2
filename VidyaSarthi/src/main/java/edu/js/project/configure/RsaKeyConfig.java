/*
 * RsaKeyConfig.java
 *
 * Drops a ready-to-use @Configuration that exposes RSAPrivateKey and RSAPublicKey beans
 * by loading PEM files (recommended for production) or by generating a new keypair at
 * runtime if PEM files are not available.
 *
 * How this fixes your startup error:
 * - Your OAuthSecurityConfig constructor asks for RSAPrivateKey/RSAPublicKey beans.
 * - Spring couldn't find such beans and failed to start. This configuration creates
 *   those beans so Spring can inject them.
 *
 * Notes on your current RsaKeyGenerator component:
 * - Writing into src/main/resources at runtime is not reliable when running from a
 *   packaged jar, because the classpath is read from the jar not the source tree.
 * - Better options:
 *   1) Load keys from an external keystore / PEM files (production recommended).
 *   2) Generate a KeyPair at startup and expose KeyPair/RSAPrivateKey/RSAPublicKey beans
 *      directly (suitable for development/testing).
 */

package edu.js.project.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RsaKeyConfig {

    // Accepts values like:
    // classpath:keys/private_key.pem
    // file:/etc/myapp/keys/private_key.pem
    @Value("${jwt.private-key.location:classpath:private_key.pem}")
    private Resource privateKeyResource;

    @Value("${jwt.public-key.location:classpath:public_key.pem}")
    private Resource publicKeyResource;

    @Value("${app.security.rsa.key-size:2048}")
    private int keySize;

    /**
     * Try to load a private key from the configured resource. If not found, generate a new KeyPair.
     */
    @Bean
    public KeyPair keyPair() throws Exception {
        try {
            RSAPrivateKey priv = privateKey();
            RSAPublicKey pub = publicKey();
            return new KeyPair(pub, priv);
        } catch (Exception ex) {
            // Fallback: generate an ephemeral keypair (useful for dev/tests)
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(keySize);
            return gen.generateKeyPair();
        }
    }

    @Bean
    public RSAPrivateKey privateKey() throws Exception {
        if (resourceExists(privateKeyResource)) {
            String pem = readPem(privateKeyResource.getInputStream());
            byte[] decoded = parsePEM(pem, "PRIVATE KEY", "RSA PRIVATE KEY");
            // Try PKCS#8 first
            try {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                return (RSAPrivateKey) kf.generatePrivate(keySpec);
            } catch (Exception e) {
                // If it fails, rethrow with context
                throw new IllegalStateException("Failed to parse private key PEM: " + e.getMessage(), e);
            }
        }
        throw new IllegalStateException("Private key resource not found: " + privateKeyResource);
    }

    @Bean
    public RSAPublicKey publicKey() throws Exception {
        if (resourceExists(publicKeyResource)) {
            String pem = readPem(publicKeyResource.getInputStream());
            byte[] decoded = parsePEM(pem, "PUBLIC KEY", "RSA PUBLIC KEY");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(keySpec);
        }
        throw new IllegalStateException("Public key resource not found: " + publicKeyResource);
    }

    // ---------------------- helpers ----------------------

    private boolean resourceExists(Resource r) {
        try {
            return r != null && r.exists() && r.getInputStream() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private String readPem(InputStream in) throws Exception {
        try (in) {
            byte[] bytes = in.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    /**
     * Strips PEM header/footer and base64-decodes the content.
     * Accepts headers like "-----BEGIN PUBLIC KEY-----" or "-----BEGIN RSA PRIVATE KEY-----".
     */
    private byte[] parsePEM(String pem, String... possibleHeaders) {
        String normalized = pem.replace("\r", "").replace("\n", "\n");
        for (String header : possibleHeaders) {
            String begin = "-----BEGIN " + header + "-----";
            String end = "-----END " + header + "-----";
            if (normalized.contains(begin)) {
                int start = normalized.indexOf(begin) + begin.length();
                int finish = normalized.indexOf(end);
                String base64 = normalized.substring(start, finish).replace("\n", "").trim();
                return Base64.getDecoder().decode(base64);
            }
        }
        // If no known headers matched try to strip all header/footer lines and decode whatever left
        String[] lines = normalized.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith("-----")) continue;
            sb.append(line.trim());
        }
        return Base64.getDecoder().decode(sb.toString());
    }
}
