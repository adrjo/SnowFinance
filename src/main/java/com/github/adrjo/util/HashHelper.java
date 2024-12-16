package com.github.adrjo.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashHelper {


    public static boolean verifyHash(String password, String encoded) {
        try {
            String decoded = new String(Base64.getDecoder().decode(encoded.getBytes()));

            String[] split = decoded.split(":");
            String passHash = split[0];
            byte[] salt = Base64.getDecoder().decode(split[1]);

            String reHashed = HashHelper.hashPassword(password, salt);

            return reHashed.equals(passHash);
        } catch (Exception e) {
            System.err.println("Failed to verify hash");
            return false;
        }
    }

    /**
     * generates a salt and hashes the password using the salt. salt is appended to the returned hash for verification
     * @param pass password to be hashed
     * @return hashed password
     */
    public static String hashAndSaltPassword(String pass) {
        byte[] salt = generateSalt();
        String hashed = HashHelper.hashPassword(pass, salt);

        hashed += ":";
        hashed += Base64.getEncoder().encodeToString(salt);

        return new String(Base64.getEncoder().encode(hashed.getBytes()));
    }

    public static String hashPassword(String pass, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Critical error: couldn't hash password " + e.getMessage());
        }
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
