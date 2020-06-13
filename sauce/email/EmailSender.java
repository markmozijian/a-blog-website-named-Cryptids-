package email;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import users.User;
/*
    class for sending emails
 */
public class EmailSender extends Emailer {
    protected final String from = "Cryptid Admin";
    protected String recipient, address, subject, content;
    protected Email email;

    public EmailSender(String recipient, String address, String subject, String content) {
        this.recipient = recipient;
        this.address = address;
        this.subject = subject;
        this.content = content;
        email = EmailBuilder.startingBlank()
                .from(from,user)
                .to(recipient,address)
                .withSubject(subject)
                .withHTMLText(content)
                .buildEmail();
    }

    public void send(){
        mailer.sendMail(email,true);
    }

    public static boolean sendEmail2User(User user, String subject, String content){
        if (user.getEmail() == null){
            return false;
        }
        EmailSender sender = new EmailSender(user.getUsername(),user.getEmail(),subject,content);
        sender.send();
        return true;
    }
}
