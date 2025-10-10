package edu.js.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired(required = false)
    private TemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    public boolean sendOtpEmail(String toEmail, String otp, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("VidyaSarthi - Email Verification OTP");
            
            String htmlContent = createOtpEmailContent(otp, userName);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            return true;
            
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createOtpEmailContent(String otp, String userName) {
        // If Thymeleaf is available, use template
        if (templateEngine != null) {
            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("userName", userName);
            return templateEngine.process("otp-email", context);
        }

        // Otherwise, use simple HTML
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f9f9f9; }
                    .otp-box { 
                        background-color: #fff; 
                        border: 2px solid #4CAF50; 
                        padding: 15px; 
                        text-align: center; 
                        font-size: 24px; 
                        letter-spacing: 5px; 
                        margin: 20px 0;
                        font-weight: bold;
                    }
                    .footer { text-align: center; padding: 10px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>VidyaSarthi</h1>
                        <p>Email Verification</p>
                    </div>
                    <div class="content">
                        <h2>Hello %s,</h2>
                        <p>Thank you for registering with VidyaSarthi. To complete your registration, please use the following OTP:</p>
                        <div class="otp-box">%s</div>
                        <p><strong>This OTP is valid for 10 minutes.</strong></p>
                        <p>If you didn't request this verification, please ignore this email.</p>
                        <p>Best regards,<br>VidyaSarthi Team</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated email. Please do not reply.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(userName, otp);
    }
}