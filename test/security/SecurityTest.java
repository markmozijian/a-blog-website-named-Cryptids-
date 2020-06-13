package security;

import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class SecurityTest {
    String pw0,pw1,pw2,pw3,pw4;
    Password hashed0,hashed1,hashed2,hashed3,hashed4;
    @Before
    public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
        pw1 = "bob";
        pw2 = "chicken";
        pw3 = "Ambigu0us!";
        pw4 = "$%^+_{}{.,.<><";
        pw0 = "";

        hashed1 = Security.hashPassword(pw1);
        hashed2 = Security.hashPassword(pw2);
        hashed3 = Security.hashPassword(pw3);
        hashed4 = Security.hashPassword(pw4);
        hashed0 = Security.hashPassword(pw0);
    }
    @Test
    public void testLength(){
        int expectedLength = 1024/8;//128
        assertEquals(expectedLength,hashed0.getHash().length);
        assertEquals(expectedLength,hashed1.getHash().length);
        assertEquals(expectedLength,hashed2.getHash().length);
        assertEquals(expectedLength,hashed3.getHash().length);
        assertEquals(expectedLength,hashed4.getHash().length);
    }
    @Test
    public void testVerification() throws NoSuchAlgorithmException, InvalidKeySpecException {
        assertTrue(Security.verifyPassword(pw0,hashed0));
        assertTrue(Security.verifyPassword(pw1,hashed1));
        assertTrue(Security.verifyPassword(pw2,hashed2));
        assertTrue(Security.verifyPassword(pw3,hashed3));
        assertTrue(Security.verifyPassword(pw4,hashed4));

        assertFalse(Security.verifyPassword(pw1,hashed3));
        assertFalse(Security.verifyPassword(pw3,hashed2));
    }
    @Test
    public void testSalt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[]{10,20};
        byte[] hashSalt = Security.hashPasswordWithSalt(pw1,salt);
        Password passwordSalt = new Password(hashSalt,new byte[]{10,50},Security.iterations);
        assertFalse(Security.verifyPassword(pw1,passwordSalt));
        passwordSalt = new Password(hashSalt,salt,Security.iterations+5);
        assertFalse(Security.verifyPassword(pw1,passwordSalt));
        passwordSalt = new Password(hashSalt,salt,Security.iterations);
        assertTrue(Security.verifyPassword(pw1,passwordSalt));
    }
}
