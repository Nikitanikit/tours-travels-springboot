package com.tourstravels.service;

import com.tourstravels.model.Booking;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${travel.admin-email:tours&travels@gmail.com}")
    private String adminEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPlanEmail(Booking booking) {
        if (booking == null || booking.getUser() == null || booking.getUser().getEmail() == null) {
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(adminEmail);
            helper.setReplyTo(adminEmail);
            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Your travel plan is ready for " + booking.getDisplayName());
            StringBuilder body = new StringBuilder();
            body.append("<p>Hello ").append(booking.getUser().getFullName()).append(",</p>")
                .append("<p>Your travel plan has been prepared by Tours & Travels.</p>")
                .append("<p><strong>Trip:</strong> ").append(booking.getDisplayName()).append("</p>")
                .append("<p><strong>Travel date:</strong> ").append(Objects.toString(booking.getTravelDate(), "To be decided")).append("</p>")
                .append("<p><strong>Pickup:</strong> ").append(Objects.toString(booking.getPickupLocation(), "To be confirmed")).append("</p>")
                .append("<p><strong>Places:</strong> ").append(Objects.toString(booking.getTravelPlaces(), "Check your account for details")).append("</p>")
                .append("<p><strong>Plan:</strong><br>")
                .append(Objects.toString(booking.getTravelPlan(), "Please review your plan in the app").replace("\n", "<br>"))
                .append("</p>")
                .append("<p>After payment, our team will confirm your itinerary.</p>")
                .append("<p>For support, email: <a href=\"mailto:").append(adminEmail).append("\">")
                .append(adminEmail).append("</a></p>");
            helper.setText(body.toString(), true);
            if (booking.getPlanPdfPath() != null && !booking.getPlanPdfPath().isBlank()) {
                File pdf = new File(booking.getPlanPdfPath());
                if (pdf.exists()) {
                    helper.addAttachment("travel-plan.pdf", pdf);
                }
            }
            mailSender.send(message);
            booking.setPlanSent(true);
        } catch (Exception ex) {
            log.warn("Unable to send travel plan email for booking {}: {}", booking.getId(), ex.getMessage());
        }
    }

    public void sendConfirmationEmail(Booking booking) {
        if (booking == null || booking.getUser() == null || booking.getUser().getEmail() == null) {
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(adminEmail);
            helper.setReplyTo(adminEmail);
            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Your plan is confirmed for " + booking.getDisplayName());
            StringBuilder body = new StringBuilder();
            body.append("<p>Hello ").append(booking.getUser().getFullName()).append(",</p>")
                .append("<p>Your plan is confirmed by Tours & Travels.</p>")
                .append("<p><strong>Trip:</strong> ").append(booking.getDisplayName()).append("</p>")
                .append("<p><strong>Travel date:</strong> ").append(Objects.toString(booking.getTravelDate(), "To be confirmed")).append("</p>")
                .append("<p><strong>Pickup location:</strong> ").append(Objects.toString(booking.getPickupLocation(), "To be communicated")).append("</p>")
                .append("<p>Your itinerary PDF is attached below and also available in your account.</p>")
                .append("<p>We look forward to hosting you.</p>")
                .append("<p>Contact: <a href=\"mailto:").append(adminEmail).append("\">")
                .append(adminEmail).append("</a></p>");
            helper.setText(body.toString(), true);
            if (booking.getPlanPdfPath() != null && !booking.getPlanPdfPath().isBlank()) {
                File pdf = new File(booking.getPlanPdfPath());
                if (pdf.exists()) {
                    helper.addAttachment("confirmed-travel-plan.pdf", pdf);
                }
            }
            mailSender.send(message);
            booking.setFinalConfirmationSent(true);
        } catch (Exception ex) {
            log.warn("Unable to send final confirmation email for booking {}: {}", booking.getId(), ex.getMessage());
        }
    }
}
