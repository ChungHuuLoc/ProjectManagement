/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author huulo
 */
public class helper {
    private static final String smtpServer = "smtp.gmail.com";
    private static final String sendFrom = "projectNHOM5@gmail.com";
    private static final String pass = "locpro123";
    private static final String subject = "Reset password code";

    public static String getSmtpServer() {
        return smtpServer;
    }

    public static String getSendFrom() {
        return sendFrom;
    }

    public static String getPass() {
        return pass;
    }

    public static String getSubject() {
        return subject;
    }
    
    

    public static void send_Email(String smtpServer, String sendTo, String sendFrom, String pass, String subject, String body) throws Exception {

    Properties props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    final String login = sendFrom;
    final String pwd = pass;
    Authenticator pa = null;
    if (login != null && pwd != null) {
        props.put("mail.smtp.auth", "true");
        pa = new Authenticator() {

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, pwd);
            }
        };
    }//else: no authentication
    Session session = Session.getInstance(props, pa);
// — Create a new message –
    Message msg = new MimeMessage(session);
// — Set the FROM and TO fields –
    msg.setFrom(new InternetAddress(sendFrom));
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo, false));

// — Set the subject and body text –
    msg.setSubject(subject);
    msg.setContent(body, "text/html");//Để gởi nội dung dạng utf-8 các bạn dùng msg.setContent(body, "text/html; charset=UTF-8");
// — Set some other header information –
    msg.setHeader("X-Mailer", "LOTONtechEmail");
    msg.setSentDate(new Date());
    msg.saveChanges();
// — Send the message –
    Transport.send(msg);
    System.out.println("Mail has sent");

    }
    
    public static boolean valEmail(String email){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        
        return matcher.find();
    }

    
}
