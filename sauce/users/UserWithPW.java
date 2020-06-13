package users;

import security.Password;
/*
User + PW encapsulation
 */
public class UserWithPW {
    private User user;
    private Password password;
    public UserWithPW(User user, Password password){
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public Password getPassword() {
        return password;
    }
}
