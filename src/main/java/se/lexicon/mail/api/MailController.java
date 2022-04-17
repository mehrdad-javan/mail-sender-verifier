package se.lexicon.mail.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mail/")
@CrossOrigin("*")
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping
    public ResponseEntity<String> sendEmailTo(@RequestParam String to) {
        System.out.println("to = " + to);
        System.out.println("Sending Email...");
        try {
            String ticket = UUID.randomUUID().toString();
            System.out.println("ticket = " + ticket);
            String link = "http://localhost:8080/api/v1/mail/verify/" + ticket;
            TicketStorage.tickets.put(ticket, to);
            String body = "<h1>Confirm your email address.</h1><br/><p><b>" +
                    "Please verify your email (<a href=" + link + ">click here</a>)" +
                    "</b></p>";
            mailService.sendEmailWithAttachment(to, body,"Test Verification Email");
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
        System.out.println("Done");
        return ResponseEntity.ok().body("Email sent successfully");
    }

    @GetMapping("verify/{ticket}")
    public ResponseEntity<String> verifyEmailByTicketId(@PathVariable String ticket) {
        System.out.println("ticket = " + ticket);
        System.out.println(TicketStorage.tickets.toString());
        if (TicketStorage.tickets.get(ticket) != null) {

            try {
                String body = "<h1>Email verification completed successfully</h1>";
                mailService.sendEmailWithAttachment(TicketStorage.tickets.get(ticket), body,"Test Verification Email");
                TicketStorage.tickets.remove(ticket);
            } catch (IOException | MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email verification Error");
        }
        System.out.println(TicketStorage.tickets.toString());
        return ResponseEntity.ok().body("Email verification completed successfully");
    }

}
