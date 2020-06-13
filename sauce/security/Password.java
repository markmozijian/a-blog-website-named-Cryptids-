package security;
/*
Password + salt encapsulation
 */
public class Password {
    private byte[] hash,salt;
    private int iterations;
    public Password(byte[] hash, byte[] salt, int iterations){
        this.hash = hash;
        this.salt = salt;
        this.iterations = iterations;
    }
    public Password(byte[] hash, byte[] salt){
        this(hash,salt,Security.iterations);
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getIterations() {
        return iterations;
    }
}
