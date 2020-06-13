package Servlets.Censor;

import Tools.Redacted;

import java.sql.Connection;
import java.sql.SQLException;
/*
uncensor something
 */
public class Free extends Redact {
    @Override
    protected void decide(Connection connection, String type, int id) throws SQLException {
        switch (type){
            case "article":
                Redacted.emancipateArticle(connection,id);
                break;
            case "comment":
                Redacted.emancipateComment(connection,id);
                break;
        }
    }
}
