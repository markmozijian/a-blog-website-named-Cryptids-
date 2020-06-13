package email;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

/*
    For sending emails.
    I want to seperate our details with recipient details. Hence the seperate class
 */

public class Emailer {
    protected final String host = "smtp.gmail.com";
    protected final String user = "illegalcryptid@gmail.com";
    protected final String pass = "nakochan";
    protected final int port = 587;
    boolean sessionDebug = false;
    protected Mailer mailer;
    public Emailer(){
        mailer = MailerBuilder.withSMTPServer(host,port,user,pass).buildMailer();
    }
}
