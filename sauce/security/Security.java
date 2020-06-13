package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
/*
Methods relating to security
 */
public class Security {
    public static final int iterations = 137;
    public static final int salt_length = 16;
    private static final int reseed = 24;
    public static final int hash_length = 1024;//in bits, so /8 to get bytes right now 128bytes
    private static int seed_count = 25;
    private static SecureRandom secureRandom;

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        seed_count++;
        if (seed_count >= reseed || secureRandom == null) {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            seed_count = 0;
        }
        byte[] salt = new byte[salt_length];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPasswordWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return hashPasswordWithSalt(password,salt,iterations);
    }

    public static byte[] hashPasswordWithSalt(String password, byte[] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] pwChars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(pwChars, salt, iterations, hash_length);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return secretKeyFactory.generateSecret(spec).getEncoded();
    }

    public static Password hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = getSalt();
        byte[] hash = hashPasswordWithSalt(password,salt);
        return new Password(hash,salt,iterations);
    }

    public static boolean verifyPassword(String password, Password hashed) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] salt = hashed.getSalt();
        byte[] hash = hashPasswordWithSalt(password,salt,hashed.getIterations());
        return Arrays.equals(hash,hashed.getHash());
    }

}