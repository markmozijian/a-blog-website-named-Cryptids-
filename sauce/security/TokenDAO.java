package security;

import Tools.SQLtools;
import Tools.TimeTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDAO {
    public static boolean addToken(Connection connection, int userID, int token) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO tokens (user, code) VALUE " +
                "(?, ?);")){
            ps.setInt(1,userID);
            ps.setInt(2,token);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }
    public static boolean verifyToken(Connection connection, int token) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM tokens WHERE " +
                "code = ?;")){
            ps.setInt(1,token);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    long timeNow = SQLtools.timestampNow(connection).getTime();
                    long tokenTime = rs.getTimestamp("created").getTime();
                    return (tokenTime >= timeNow - TimeTools.tenmins);//token is 10mins fresh
                } else
                    return false;
            }
        }
    }

    public static boolean deleteToken(Connection connection, int token) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM tokens WHERE " +
                "code = ?;")){
            ps.setInt(1,token);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static int getUserID(Connection connection, int token) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT user FROM tokens WHERE code = ?;")){
            ps.setInt(1,token);
            try (ResultSet rs = ps.executeQuery()){
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public static boolean tokenExists(Connection connection, int token) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT code FROM tokens WHERE code = ?;")){
            ps.setInt(1,token);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }

    public static boolean userTokenExists(Connection connection, int userID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT user FROM tokens WHERE user = ?;")){
            ps.setInt(1,userID);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }

    public static boolean deleteUserToken(Connection connection, int userID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM tokens WHERE " +
                "user = ?;")){
            ps.setInt(1,userID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }
}
