package Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
/*
nuff said
 */
public class SQLtools {
    public static void checkTooManyRowsAffected(int rowsAffected, int max) throws SQLException {
        if (rowsAffected > max) {
            String message = String.format("Too many rows affected. Should be %d row max.",max);
            throw new SQLException(message);
        }
    }
    public static boolean checkOnly1RowChanged(int rowsAffected) throws SQLException{
        checkTooManyRowsAffected(rowsAffected,1);
        return rowsAffected == 1;
    }
    public static Timestamp timestampNow(Connection connection) throws SQLException{
        try (Statement statement = connection.createStatement()){
            try (ResultSet rs = statement.executeQuery("SELECT CURRENT_TIMESTAMP AS timestampnow")){
                if (rs.next()){
                    return rs.getTimestamp(1);
                } else {
                    throw new SQLException("Problem with getting timestamp");
                }
            }
        }
    }
    public static void squeal(SQLException e, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            e.printStackTrace();
            String warning = "Database error occured, try not to upload images larger then 5 MB into the editor";
            req.setAttribute("warning",warning);
            req.getRequestDispatcher("./WEB-INF/frontend/warning.jsp").forward(req,resp);
    }
}
