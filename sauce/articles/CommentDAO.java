package articles;

import Tools.SQLtools;
import Tools.TimeTools;
import users.User;
import users.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public static List<Comment> getComments(Connection connection,
                                            PreparedStatement ps) throws SQLException{
        try(ResultSet rs = ps.executeQuery()){
            List<Comment> list = new ArrayList<>();
            while(rs.next()){
                list.add(makeComment(connection,rs));
            }
            return list;
        }
    }

    public static List<Comment> getCommentsForAdmin(Connection connection,
                                            PreparedStatement ps) throws SQLException{
        try(ResultSet rs = ps.executeQuery()){
            List<Comment> list = new ArrayList<>();
            while(rs.next()){
                list.add(makeCommentForAdmin(connection,rs));
            }
            return list;
        }
    }

    public static List<Comment> getCommentsOfArticle(Connection connection,
                                                     int articleID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE article = ? AND parent IS NULL ORDER BY updated DESC;")){
            ps.setInt(1,articleID);
            return getComments(connection,ps);
        }
    }

    public static List<Comment> getCommentsOfComment(Connection connection, int parentID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE parent = ? ORDER BY updated DESC;")){
            ps.setInt(1,parentID);
            return getComments(connection,ps);
        }
    }

    public static List<Comment> getCommentsOfCommentForAdmin(Connection connection, int parentID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE parent = ? ORDER BY updated DESC;")){
            ps.setInt(1,parentID);
            return getCommentsForAdmin(connection,ps);
        }
    }

    public static Comment getComment(Connection connection, int commentID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments WHERE id = ?;")){
            ps.setInt(1,commentID);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? makeComment(connection,rs) : null;
            }
        }
    }

    private static Comment makeComment(Connection connection, ResultSet rs) throws SQLException{
        int ID = rs.getInt(1);
        int articleID = rs.getInt("article");
        if (rs.getBoolean("redacted")){
            return new Comment(ID,articleID);
        }
        User author = UserDAO.getUser(connection, rs.getInt("author"));
        String content = rs.getNString("content");
        Timestamp time = rs.getTimestamp("updated");
        Comment comment = new Comment(ID,author,content,time,articleID);
        comment.setParent(rs.getInt("parent"));
        comment.setComments(getCommentsOfComment(connection,ID));
        return comment;
    }

    public static boolean addComment(Connection connection, Comment comment) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO comments (content, article, parent, author, updated) " +
                "VALUE (?, ?, ?, ?, ?);")){
            ps.setNString(1,comment.getContent());
            ps.setInt(2,comment.getArticleID());
            Integer parent = comment.getParent();
            if (parent == null){
                ps.setNull(3,Types.INTEGER);
            } else {
                ps.setInt(3, parent);
            }
            ps.setInt(4,comment.getAuthor().getID());
            ps.setTimestamp(5,comment.getLastUpdate());
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean editComment(Connection connection, int commentID, String content) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("UPDATE comments SET " +
                "content = ? WHERE id = ?;")){
            ps.setNString(1,content);
            ps.setInt(2,commentID);
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean softDelete(Connection connection, int commentID, String deleter) throws SQLException{
        String replacer = String.format("[Comment deleted by %s on %s]",deleter, TimeTools.getLocalDateStr());
        return editComment(connection,commentID,replacer);
    }

    public static boolean purgeComment(Connection connection, int commentID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("DELETE FROM comments " +
                "WHERE id = ?;")){
            ps.setInt(1,commentID);
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static User getCommenter(Connection connection, int commentID) throws SQLException{
        Comment comment = getComment(connection, commentID);
        return comment.getAuthor();
    }

    public static Comment makeCommentForAdmin(Connection connection, ResultSet rs) throws SQLException{
        int ID = rs.getInt(1);
        int articleID = rs.getInt("article");
        String content = rs.getNString("content");
        if (rs.getBoolean("redacted")){
            String redactedComment = "*REDACTED*";
            content = String.format("%s%n%s%n%s",redactedComment,content,redactedComment);
        }
        User author = UserDAO.getUser(connection, rs.getInt("author"));
        Timestamp time = rs.getTimestamp("updated");
        Comment comment = new Comment(ID,author,content,time,articleID);
        comment.setParent(rs.getInt("parent"));
        comment.setComments(getCommentsOfCommentForAdmin(connection,ID));
        return comment;
    }

    public static Comment getCommentForAdmin(Connection connection, int commentID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM comments WHERE id = ?;")){
            ps.setInt(1,commentID);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next() ? makeCommentForAdmin(connection,rs) : null;
            }
        }
    }
}
