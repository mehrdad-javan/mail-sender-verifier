package se.lexicon.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmailWithAttachment(final String to, final String body, final String subject) throws MessagingException, IOException {
        // Multipurpose Internet Mail Extensions (MIME)
        // It represents a MIME style email message
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        // Multipart message contains both plain text and HTML messages
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);
        helper.setSubject(subject != null ? subject : "");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(body, true);

        // hard coded a file path
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath("dir/img.png");

        helper.addAttachment("my_photo.png", path.toFile());

        javaMailSender.send(msg);

    }
}
