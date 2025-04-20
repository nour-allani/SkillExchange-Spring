package tn.esprit.skillexchange.Controller.Mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.Mailing.EmailCodeRequest;
import tn.esprit.skillexchange.Entity.Mailing.EmailRequest;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private GmailService gmailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            gmailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return ResponseEntity.ok("Email sent successfully to " + emailRequest.getTo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send-reset-code")
    public ResponseEntity<String> sendPasswordResetCode(
            @RequestBody EmailCodeRequest emailCodeRequest) {
        try {
            gmailService.sendPasswordResetEmail(emailCodeRequest.getEmail(), emailCodeRequest.getCode());
            return ResponseEntity.ok("Password reset code sent to " + emailCodeRequest.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send reset code: " + e.getMessage());
        }
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(
            @RequestBody EmailCodeRequest emailCodeRequest) {
        try {
            gmailService.sendAccountVerificationEmail(emailCodeRequest.getEmail(), emailCodeRequest.getCode());
            return ResponseEntity.ok("Verification code sent to " + emailCodeRequest.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send verification code: " + e.getMessage());
        }
    }




}
