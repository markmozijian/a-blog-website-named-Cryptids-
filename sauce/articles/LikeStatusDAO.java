package articles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeStatusDAO {

    public static int setLikeIcon(Connection connection, int articleID, int userID, int status) throws SQLException {
        if(getLikeIcon(connection,userID,articleID) != 2){
            try (PreparedStatement ps = connection.prepareStatement("UPDATE like_status SET status = ?  WHERE articleID = ? and userID = ?;")) {
                ps.setInt(1, status);
                ps.setInt(2, articleID);
                ps.setInt(3, userID);
                ps.executeUpdate();
            }
        }else{//2
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO like_status (status,articleID,userID) values ( ? ,? ,? );")) {
                ps.setInt(1, status);
                ps.setInt(2, articleID);
                ps.setInt(3, userID);
                ps.executeUpdate();
            }
        }
        return getLikeIcon(connection,userID,articleID);
    }

    /**
     *   作用 ：根据数据库userID和articleID来确定status的数值
     * @param connection 连接
     * @param userID  用户ID
     * @param articleID 文章ID
     * @throws SQLException 数组异常
     */
    public static int getLikeIcon(Connection connection,int userID, int articleID) throws SQLException {


        try (PreparedStatement ps = connection.prepareStatement("SELECT status FROM like_status  WHERE userID = ? and articleID = ?;")) {
            ps.setInt(1, userID);
            ps.setInt(2,articleID);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? rs.getInt("status") : 2;
            }


        }
    }

    public static int getNumberOfLikes(Connection connection, int articleID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT COUNT(id) FROM like_status WHERE articleID = ? AND status = 1;")){
            ps.setInt(1,articleID);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}
