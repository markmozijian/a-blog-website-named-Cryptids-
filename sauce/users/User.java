package users;

import Tools.ParseTools;
import Tools.PathFinder;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.sql.Date;
/*
User information encapsulation
 */
public class User {
    private Integer ID;//Should never be changed
    private Date dateOfBirth,joined;
    private String username,realName,country,bio,avatar,email;
    private final static String na = "N/A";
    private final static String default_pic = "default.jpg";
    public User(String username){
        this.ID = 0;
        this.username = Jsoup.clean(username,Whitelist.simpleText());
        long time = System.currentTimeMillis();
        this.joined = new Date(time);
    }
    public User(int id, String username){
        this(username);
        this.ID = id;
    }
    public User(int ID, String username, Date joined) {
        this.ID = ID;
        this.joined = joined;
        this.username = Jsoup.clean(username,Whitelist.simpleText());
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setUsername(String username) {
        this.username = Jsoup.clean(username,Whitelist.simpleText());
    }

    public void setRealName(String realName) {
        this.realName = Jsoup.clean(realName,Whitelist.simpleText());
    }

    public void setCountry(String country) {
        this.country = Jsoup.clean(country,Whitelist.simpleText());
    }

    public void setBio(String bio) {
        this.bio = Jsoup.clean(bio, ParseTools.filter());
    }

    public int getID() {
        return ID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getJoined() {
        return joined;
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return (realName == null) ? na : realName;
    }

    public String getCountry() {
        return (country == null) ? na : country;
    }

    public String getBio() {
        return (bio == null) ? na : bio;
    }

    public String getName(){// returns realname if any. if none then returns username
        return (realName == null || realName.length() == 0) ? getUsername() : getRealName();
    }

    public String getAvatar() {
        if(avatar == null || avatar.length() <= 0){
            return default_pic;
        }
        return PathFinder.existsElse(PathFinder.avatarPath,avatar,default_pic);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin(){
        return ID < 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return ID == ((User) obj).ID;
        return false;
    }
}

/*
A user is an admin if the ID is negative
 */
