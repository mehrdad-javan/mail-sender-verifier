package se.lexicon.mail.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmailWithAttachment(String to, String body, String subject) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);
        helper.setSubject(subject);

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
