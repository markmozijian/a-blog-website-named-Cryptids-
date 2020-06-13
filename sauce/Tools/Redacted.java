package Tools;

import articles.Article;
import articles.Comment;
import users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
Censorship tools
 */
public class Redacted {
    public final static String content = "<h2>REDACTED</h2>";
    public final static String title = "REDACTED";
    public final static String comment = "<h6>REDACTED</h6>";
    public final static String author = "<b><i>REDACTED</i></b>";
    public final static String A = "articles";
    public final static String C = "comments";
    public static User getUser(){

        return new User(author);
    }
    public static Comment getComment(int articleID){
        return new Comment(getUser(),comment,articleID);
    }
    public static List<Comment> getComments(int articleID){
        Comment censored = getComment(articleID);
        List<Comment> array = new ArrayList<>();
        array.add(censored);
        return array;
    }
    public static Article getArticle(){
        return new Article(getUser(),title,content);
    }

    private static PreparedStatement censor(Connection connection, String table, boolean boo) throws SQLException{
        String bool;
        bool = (boo) ? "1" : "0";
        return connection.prepareStatement("UPDATE " + table + " SET updated = updated , redacted = " + bool + " WHERE id = ?;");
    }

    public static boolean redactArticle(Connection connection, int articleID) throws SQLException{
        try(PreparedStatement ps = censor(connection,A,true)){
            ps.setInt(1,articleID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static boolean emancipateArticle(Connection connection, int articleID) throws SQLException{
        try(PreparedStatement ps = censor(connection,A,false)){
            ps.setInt(1,articleID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static boolean redactComment(Connection connection, int commentID) throws SQLException{
        try(PreparedStatement ps = censor(connection,C,true)){
            ps.setInt(1,commentID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }

    public static boolean emancipateComment(Connection connection, int commentID) throws SQLException{
        try(PreparedStatement ps = censor(connection,C,false)){
            ps.setInt(1,commentID);
            return SQLtools.checkOnly1RowChanged(ps.executeUpdate());
        }
    }
}
