package com.tourstravels.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.tourstravels.model.Booking;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class PlanPdfService {
    public String createPlanPdf(Booking booking) throws IOException {
        if (booking == null) {
            throw new IllegalArgumentException("Booking is required");
        }
        Path directory = Paths.get("uploads", "plans");
        Files.createDirectories(directory);
        Path file = directory.resolve("booking-" + booking.getId() + "-plan.pdf");

        try (var writer = new PdfWriter(Files.newOutputStream(file));
             var pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
             var document = new Document(pdf)) {
            document.add(new Paragraph("Tours & Travels Travel Plan")
                .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Customer: " + booking.getUser().getFullName()));
            document.add(new Paragraph("Email: " + booking.getUser().getEmail()));
            document.add(new Paragraph("Trip: " + booking.getDisplayName()));
            document.add(new Paragraph("Travel date: " + Objects.toString(booking.getTravelDate(), "Not decided")));
            document.add(new Paragraph("Travel time: " + Objects.toString(booking.getTravelTime(), "Not decided")));
            document.add(new Paragraph("Pickup location: " + Objects.toString(booking.getPickupLocation(), "To be shared")));
            document.add(new Paragraph("Places to visit: " + Objects.toString(booking.getTravelPlaces(), "To be shared")));
            document.add(new Paragraph("Travel plan:"));
            document.add(new Paragraph(Objects.toString(booking.getTravelPlan(), "No itinerary added yet")));
            document.add(new Paragraph("Admin notes: " + Objects.toString(booking.getAdminNotes(), "None")));
            document.add(new Paragraph("Total payable: ₹" + String.format("%,.0f", booking.getAmountDue().doubleValue())));
            document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        }

        return file.toAbsolutePath().toString();
    }
}
