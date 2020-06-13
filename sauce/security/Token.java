package security;

import java.security.SecureRandom;
/*
Methods relating to PW reset tokens
 */
public class Token {
    private static SecureRandom secureRandom = new SecureRandom();
    public static int getToken(){
        return secureRandom.nextInt();
    }
    public static String resetPWmessage(String name,int token){
        return String.format("Token to reset password of user:%s%n%n " +
                "<a href='https://sporadic.nz/group0_2019_cryptid/reset-password?token=%d'>https://sporadic.nz/group0_2019_cryptid/reset-password?token=%d</a>",name,token,token);
    }
}
