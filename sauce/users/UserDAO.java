package users;

import Tools.SQLtools;
import Tools.TimeTools;
import security.Password;
import security.Security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean addNewUser(Connection connection, User user, String passwordStr) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        try(PreparedStatement ps = connection.prepareStatement("INSERT INTO users " +
                "(username, joined, dateOfBirth, realName, country, bio, avatar, email, password ,salt) VALUE " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")){
            ps.setNString(1,user.getUsername());
            ps.setDate(2, user.getJoined());
            ps.setDate(3, user.getDateOfBirth());
            ps.setNString(4,user.getRealName());
            ps.setNString(5,user.getCountry());
            ps.setNString(6,user.getBio());
            ps.setNString(7,user.getAvatar());
            ps.setNString(8,user.getEmail());
            Password password = Security.hashPassword(passwordStr);
            ps.setBytes(9,password.getHash());
            ps.setBytes(10,password.getSalt());
            int statusCode = ps.executeUpdate();
            SQLtools.checkTooManyRowsAffected(statusCode,1);
            return statusCode == 1;//if 1 row modified => success
        }
    }

    public static boolean addNewAdmin(Connection connection, User admin, String passwordStr) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        try(PreparedStatement ps = connection.prepareStatement("INSERT INTO users " +
                "(id, username, joined, password ,salt, realName, country, bio, avatar) VALUE " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?);")){
            ps.setInt(1,admin.getID());
            ps.setNString(2,admin.getUsername());
            ps.setDate(3,admin.getJoined());
            Password password = Security.hashPassword(passwordStr);
            ps.setBytes(4,password.getHash());
            ps.setBytes(5,password.getSalt());
            ps.setNString(6,"");
            ps.setNString(7,"");
            ps.setNString(8,"");
            ps.setNString(9,admin.getAvatar());
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static boolean updateUser(Connection connection, User user) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("UPDATE users " +
                "SET username = ?, dateOfBirth = ?, realName = ?, country = ?, bio = ?, avatar = ?, email = ? " +
                "WHERE id = ?;")){
            ps.setNString(1,user.getUsername());
            ps.setDate(2, user.getDateOfBirth());
            ps.setNString(3,user.getRealName());
            ps.setNString(4,user.getCountry());
            ps.setNString(5,user.getBio());
            ps.setNString(6,user.getAvatar());
            ps.setNString(7,user.getEmail());
            ps.setInt(8,user.getID());
            int statusCode = ps.executeUpdate();
            SQLtools.checkTooManyRowsAffected(statusCode,1);
            return statusCode == 1;//if 1 row modified => success
        }
    }

    public static boolean updatePassword(Connection connection, int userID, String newPassword) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        Password password = Security.hashPassword(newPassword);
        try(PreparedStatement ps = connection.prepareStatement("UPDATE users " +
                "SET password = ?, salt = ? WHERE id = ?;")){
            ps.setBytes(1,password.getHash());
            ps.setBytes(2,password.getSalt());
            ps.setInt(3,userID);
            int statusCode = ps.executeUpdate();
            SQLtools.checkTooManyRowsAffected(statusCode,1);
            return statusCode == 1;//if 1 row modified => success
        }
    }

    public static boolean checkUsername(Connection connection, String username) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT username " +
                "FROM users WHERE username = ?;")){
            preparedStatement.setNString(1,username);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
                    if (rs.getNString(1).equalsIgnoreCase(username)){
                        return false;//username not ok
                    }
                }
                return true;//username ok
            }
        }
    }

    private static UserWithPW getUserWithPW(PreparedStatement ps) throws SQLException{
        try(ResultSet rs = ps.executeQuery()){
            if (rs.next()){
                User user = makeUser(rs);
                Password password = new Password(rs.getBytes("password"),
                        rs.getBytes("salt"));
                return new UserWithPW(user,password);
            } else {
                return null;
            }
        }
    }

    public static UserWithPW getUserWithPW(Connection connection, String username) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ?;")){
            ps.setNString(1,username);
            return getUserWithPW(ps);
        }
    }

    public static UserWithPW getUserWithPW(Connection connection, int ID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?;")){
            ps.setInt(1,ID);
            return getUserWithPW(ps);
        }
    }


    private static User makeUser(ResultSet rs) throws SQLException{
        User user = new User(rs.getInt(1),
                rs.getNString(2),rs.getDate(3));
        user.setDateOfBirth(rs.getDate("dateOfBirth"));
        user.setRealName(rs.getNString("realName"));
        user.setCountry(rs.getNString("country"));
        user.setBio(rs.getNString("bio"));
        user.setAvatar(rs.getNString("avatar"));
        user.setEmail(rs.getNString("email"));
        return user;
    }

    public static User getUser(Connection connection, String username) throws SQLException{
        UserWithPW userWithPW = getUserWithPW(connection, username);
        if (userWithPW != null)
            return userWithPW.getUser();
        else
            return null;
    }

    public static User getUser(Connection connection, int ID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?")){
            ps.setInt(1,ID);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next())
                    return makeUser(rs);
                else
                    return null;
            }
        }
    }

    public static boolean verifyUser(Connection connection, UserWithPW user, String pwInput) throws NoSuchAlgorithmException,InvalidKeySpecException{
        Password password = user.getPassword();
        return Security.verifyPassword(pwInput,password);
    }

    public static boolean murder(Connection connection, int userID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?;")){
            ps.setInt(1,userID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static boolean usernameExists(Connection connection, String username) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT username FROM users WHERE username = ?;")){
            ps.setNString(1,username);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }
    public static int articlesWrittenAmount(Connection connection, int authorID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT COUNT(author) FROM articles WHERE author = ?;")){
            ps.setInt(1,authorID);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return rs.getInt(1);
                } else throw new SQLException("Couldn't count number of articles authored");
            }
        }
    }
}
