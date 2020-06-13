package articles;

import Tools.Redacted;
import Tools.SQLtools;
import Tools.TimeTools;
import users.User;
import users.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    public static List<Article> getAllArticles(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            try (ResultSet rs = statement.executeQuery("SELECT * FROM articles;")){

                return makeArticles(connection,rs);
            }
        }
    }

    public static List<List<Article>> getArticleSets(List<Article> allArticles, int amount) throws SQLException{
        List<List<Article>> articleSets = new ArrayList<>();
        while (!allArticles.isEmpty()){
            List<Article> tempList = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                tempList.add(allArticles.remove(0));
            }
            articleSets.add(tempList);
        }
        return articleSets;
    }

    public static Article getArticle(Connection connection, int articleID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM articles " +
                "WHERE id = ?;")){
            ps.setInt(1,articleID);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next())
                    return makeArticle(connection,rs);
                else
                    return null;
            }

        }
    }
    public static Article getArticleForAdmin(Connection connection, int articleID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM articles " +
                "WHERE id = ?;")){
            ps.setInt(1,articleID);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next())
                    return makeArticleForAdmin(connection,rs);
                else
                    return null;
            }

        }
    }

    public static List<Article> getArticlesByAuthor(Connection connection, int authorID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM articles WHERE author = ?;")){
            List<Article> articleList = new ArrayList<>();
            ps.setInt(1,authorID);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    articleList.add(makeArticle(connection,rs));
                }
                return articleList;
            }
        }
    }

    /*private static Article constructArticle(Connection connection, ResultSet rs, int articleID, User author, String title) throws SQLException{
        String content = rs.getNString(3);
        Timestamp updated = rs.getTimestamp("updated");
        String mainpic = rs.getNString("mainPicture");
        String mainMedia = rs.getNString("mainMedia");
        Article article = new Article(articleID,author,title,content,updated);
        article.setComments(CommentDAO.getCommentsOfArticle(connection,articleID));
        article.setMainPicture(mainpic);
        if (mainMedia != null)
            article.setMainMedia(mainMedia);
        return article;
    }*/
    private static Article constructArticle(Connection connection, ResultSet rs) throws SQLException{
        int articleID = rs.getInt(1);
        User author = UserDAO.getUser(connection,rs.getInt("author"));
        String title = rs.getNString(2);
        String content = rs.getNString(3);
        Timestamp updated = rs.getTimestamp("updated");
        String mainpic = rs.getNString("mainPicture");
        String mainMedia = rs.getNString("mainMedia");
        Article article = new Article(articleID,author,title,content,updated);
        article.setComments(CommentDAO.getCommentsOfArticle(connection,articleID));
        article.setMainPicture(mainpic);
        if (mainMedia != null)
            article.setMainMedia(mainMedia);
        return article;
    }

    private static Article makeArticle(Connection connection, ResultSet rs) throws SQLException{
        Article article = constructArticle(connection,rs);
        if (rs.getBoolean("redacted")){
            int articleID = article.getID();
            return new Article(articleID);
        }
        return article;
    }

    private static Article makeArticleForAdmin(Connection connection, ResultSet rs) throws SQLException{
        Article article = constructArticle(connection,rs);
        if (rs.getBoolean("redacted")){
            article.setRedacted(true);
        }
        return article;
    }

    public static boolean addNewArticle(Connection connection, Article article) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("INSERT INTO articles (title, content, author, updated, mainPicture, mainMedia) " +
                "VALUE (?, ?, ?, ?, ?, ?);")){
            ps.setNString(1,article.getTitle());
            ps.setNString(2,article.getContent());
            ps.setInt(3,article.getAuthor().getID());
            ps.setTimestamp(4,article.getLastUpdate());
            ps.setNString(5,article.getMainPicture());
            ps.setNString(6,article.getMainMedia());
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }
   public static boolean editArticle(Connection connection, int articleID, String content) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("UPDATE articles SET " +
                "content = ? WHERE id = ?;")){
            ps.setNString(1,content);
            ps.setInt(2,articleID);
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean editTitle(Connection connection, int articleID, String title) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("UPDATE articles SET " +
                "title = ? WHERE id = ?;")){
            ps.setNString(1,title);
            ps.setInt(2,articleID);
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean updateArticle(Connection connection, Article article) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("UPDATE articles SET " +
                "title = ?, content = ?, mainPicture = ?, mainMedia = ? WHERE id = ?;")){
            ps.setNString(1,article.getTitle());
            ps.setNString(2,article.getContent());
            ps.setNString(3,article.getMainPicture());
            ps.setNString(4,article.getMainMedia());
            ps.setInt(5,article.getID());
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean purgeArticle(Connection connection, int articleID) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM articles WHERE " +
                "id = ?;")){
            ps.setInt(1,articleID);
            int statusCode = ps.executeUpdate();
            return SQLtools.checkOnly1RowChanged(statusCode);
        }
    }

    public static boolean softDelete(Connection connection, int articleID, String deleter) throws SQLException{
        String replacer = String.format("[Article deleted by %s on %s]",deleter,TimeTools.getLocalDateStr());
        return editArticle(connection,articleID,replacer);
    }

    public static User getAuthor(Connection connection, int articleID) throws SQLException{
        Article article = getArticle(connection,articleID);
        return article.getAuthor();
    }

    public static User getAuthorIfCensored(Connection connection, int articleID) throws SQLException{
        Article article = getArticleForAdmin(connection,articleID);
        return article.getAuthor();
    }

    public static String getAllContent(Connection connection) throws SQLException{
        StringBuilder builder = new StringBuilder();
        try(Statement statement = connection.createStatement()){
            try(ResultSet rs = statement.executeQuery("SELECT content FROM articles WHERE redacted = FALSE;")){
                while(rs.next()){
                    builder.append(rs.getNString(1));
                }
            }
        }
        return builder.toString();
    }
    public static String getAuthorContent(Connection connection, int authorID) throws SQLException{
        StringBuilder builder = new StringBuilder();
        try(PreparedStatement ps = connection.prepareStatement("SELECT content FROM articles WHERE author = ? AND redacted = FALSE;")){
            ps.setInt(1,authorID);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    builder.append(rs.getNString(1));
                }
            }
        }
        return builder.toString();
    }
    public static boolean mainPicCollision(Connection connection, String fileName) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT mainPicture FROM articles WHERE mainPicture = ?;")){
            ps.setNString(1,fileName);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }
    public static boolean mediaCollision(Connection connection, String fileName) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT mainMedia FROM articles WHERE mainMedia = ?;")){
            ps.setNString(1,fileName);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        }
    }

    public static List<Article> searchArticle(Connection connection, String query) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM articles INNER JOIN users u on articles.author = u.id " +
                "WHERE (content LIKE ? OR title LIKE ? OR username LIKE ?) AND redacted = 0 ORDER BY updated DESC ;")){
            query = "%" + query + "%";
            ps.setNString(1,query);
            ps.setNString(2,query);
            ps.setNString(3,query);
            try(ResultSet rs = ps.executeQuery()){
                return makeArticles(connection,rs);
            }
        }
    }
    public static List<Article> searchArticleAsAdmin(Connection connection, String query) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT * FROM articles INNER JOIN users u on articles.author = u.id " +
                "WHERE (content LIKE ? OR title LIKE ? OR username LIKE ?) ORDER BY updated DESC;")){
            query = "%" + query + "%";
            ps.setNString(1,query);
            ps.setNString(2,query);
            ps.setNString(3,query);
            try(ResultSet rs = ps.executeQuery()){
                return makeArticlesForAdmin(connection,rs);
            }
        }
    }
    public static List<Article> makeArticles(Connection connection, ResultSet rs) throws SQLException{
        List<Article> articles = new ArrayList<>();
        while(rs.next()){
            articles.add(makeArticle(connection,rs));
        }
        return articles;
    }
    public static List<Article> makeArticlesForAdmin(Connection connection, ResultSet rs) throws SQLException{
        List<Article> articles = new ArrayList<>();
        while(rs.next()){
            articles.add(makeArticleForAdmin(connection,rs));
        }
        return articles;
    }
    public static List<Article> getLikedArticles(Connection connection, int userID) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement("SELECT a.* FROM articles AS a JOIN like_status l " +
                "ON a.id = l.articleID WHERE status = 1 AND userID = ? ORDER BY l.time DESC ;")){
            ps.setInt(1,userID);
            try(ResultSet rs = ps.executeQuery()){
                return makeArticles(connection,rs);
            }
        }
    }
}
