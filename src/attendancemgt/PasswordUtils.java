package attendancemgt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password encryption using SHA-256 hashing with salt.
 * Uses only Java built-in APIs - no external libraries required.
 */
public class PasswordUtils {

    /**
     * Hashes a password with a randomly generated salt using SHA-256.
     * Returns a Base64-encoded string in the format "salt:hash"
     */
    public static String hashPassword(String password) {
        try {
            // Generate a 16-byte random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Hash password with salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedBytes = md.digest(password.getBytes());

            // Encode salt and hash as Base64 and combine
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedBytes);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verifies a password against a stored hash produced by hashPassword().
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split stored hash into salt and hash parts
            String[] parts = storedHash.split(":");
            if (parts.length != 2) return false;

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[1]);

            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] actualHash = md.digest(password.getBytes());

            // Constant-time comparison to prevent timing attacks
            return MessageDigest.isEqual(actualHash, expectedHash);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Convenience method to hash a password for demo/seed data.
     */
    public static String[] generateDemoCredentials(String password) {
        String hashed = hashPassword(password);
        return new String[] {password, hashed};
    }

    public static void main(String[] args) {
        // Demo: show how hashing works
        String password = "12345";
        String hashed = hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("Hashed:   " + hashed);
        System.out.println("Verify:   " + verifyPassword(password, hashed));
        System.out.println("Wrong:    " + verifyPassword("wrong", hashed));
    }
}
