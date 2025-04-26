package tn.esprit.skillexchange.Service.GestionProduit;

import com.itextpdf.text.pdf.draw.LineSeparator;
import com.stripe.model.ChargeCollection;
import com.stripe.param.ChargeListParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoicePdfService {

    // Color scheme from HTML template
    private static final BaseColor PRIMARY_ORANGE = new BaseColor(249, 115, 22);  // #f97316
    private static final BaseColor LIGHT_GRAY = new BaseColor(240, 240, 240);     // #f0f0f0
    private static final BaseColor DARK_GRAY = new BaseColor(51, 51, 51);         // #333
    private static final BaseColor WHITE = new BaseColor(255, 255, 255);          // #fff

    public byte[] generateInvoiceFromStripe(String paymentIntentId) throws Exception {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        ChargeCollection charges = Charge.list(
                ChargeListParams.builder()
                        .setPaymentIntent(paymentIntentId)
                        .build()
        );
        List<Charge> chargeList = charges.getData();

        if (chargeList.isEmpty()) {
            return generateEmptyInvoice(paymentIntentId);
        }

        Charge charge = chargeList.get(0);
        String email = charge.getBillingDetails().getEmail() != null ?
                charge.getBillingDetails().getEmail() : "not specified";
        Long amount = charge.getAmount();
        String currency = "TND"; // Force TND currency
        Date date = new Date(intent.getCreated() * 1000L);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Table for logo (left) and header (right)
        PdfPTable headerLogoTable = new PdfPTable(2);
        headerLogoTable.setWidthPercentage(100);
        headerLogoTable.setWidths(new float[]{1, 3});

        // Logo cell (left)
        PdfPCell logoCell = new PdfPCell();
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setPadding(5f);
        logoCell.setVerticalAlignment(Element.ALIGN_TOP);

        // Add logo
        try {
            String logoPath = "src/main/resources/static/logo25.png";
            if (new File(logoPath).exists()) {
                Image logo = Image.getInstance(logoPath);
                logo.scaleToFit(80, 80);
                logoCell.addElement(logo);
            }
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        // Header cell (right)
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.setPadding(5f);

        // Title with orange background
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, WHITE);
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(100);
        PdfPCell titleCell = new PdfPCell(new Phrase("INVOICE", titleFont));
        titleCell.setBackgroundColor(PRIMARY_ORANGE);
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setPadding(10f);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTable.addCell(titleCell);
        headerCell.addElement(titleTable);
        headerCell.addElement(Chunk.NEWLINE);

        // Invoice information
        Font companyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, DARK_GRAY);
        Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DARK_GRAY);

        Paragraph invoiceTitle = new Paragraph("Invoice #" + paymentIntentId.substring(0, 8), companyFont);
        Paragraph dateInfo = new Paragraph("Date: " + date.toString(), infoFont);
        Paragraph clientInfo = new Paragraph("Client: " + email, infoFont);
        Paragraph amountInfo = new Paragraph("Amount: " + String.format("%.2f %s", amount / 100.0, currency), infoFont);

        headerCell.addElement(invoiceTitle);
        headerCell.addElement(dateInfo);
        headerCell.addElement(clientInfo);
        headerCell.addElement(amountInfo);

        headerLogoTable.addCell(logoCell);
        headerLogoTable.addCell(headerCell);
        document.add(headerLogoTable);

        // Orange separator line
        LineSeparator line = new LineSeparator();
        line.setLineWidth(1.5f);
        line.setLineColor(PRIMARY_ORANGE);
        document.add(line);
        document.add(Chunk.NEWLINE);

        // Company information
        PdfPTable companyTable = new PdfPTable(1);
        companyTable.setWidthPercentage(100);
        companyTable.setSpacingBefore(10f);

        Paragraph company = new Paragraph("SkillExchange", companyFont);
        Paragraph address1 = new Paragraph("123 Education Street", infoFont);
        Paragraph address2 = new Paragraph("Tunis, Tunisia", infoFont);
        Paragraph emailInfo = new Paragraph("contact@skillexchange.tn", infoFont);
        Paragraph website = new Paragraph("www.skillexchange.tn",
                new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, PRIMARY_ORANGE));

        PdfPCell companyCell = new PdfPCell();
        companyCell.setBorder(Rectangle.NO_BORDER);
        companyCell.addElement(company);
        companyCell.addElement(address1);
        companyCell.addElement(address2);
        companyCell.addElement(emailInfo);
        companyCell.addElement(website);
        companyTable.addCell(companyCell);
        document.add(companyTable);

        // Invoice details with light gray background
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new float[]{1, 3});
        detailsTable.setSpacingBefore(15f);
        detailsTable.setSpacingAfter(25f);

        addStyledDetailRow(detailsTable, "Payment ID:", paymentIntentId);
        addStyledDetailRow(detailsTable, "Date:", date.toString());
        addStyledDetailRow(detailsTable, "Client Email:", email);
        addStyledDetailRow(detailsTable, "Amount Paid:", String.format("%.2f %s", amount / 100.0, currency));
        addStyledDetailRow(detailsTable, "Payment Method:", "Credit Card");
        addStyledDetailRow(detailsTable, "Status:", "Completed");

        document.add(detailsTable);

        // Thank you message
        Font thankYouFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, DARK_GRAY);
        Paragraph thankYou = new Paragraph("Thank you for choosing SkillExchange for your learning journey!", thankYouFont);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        thankYou.setSpacingBefore(20f);
        document.add(thankYou);

        // Footer with light gray background
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setWidthPercentage(100);
        footerTable.setSpacingBefore(15f);

        PdfPCell footerCell = new PdfPCell();
        footerCell.setBackgroundColor(LIGHT_GRAY);
        footerCell.setBorder(Rectangle.NO_BORDER);
        footerCell.setPadding(10f);

        Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, DARK_GRAY);
        Font footerAccentFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, PRIMARY_ORANGE);

        Paragraph footer = new Paragraph();
        footer.add(new Chunk("© 2025 SkillExchange. All rights reserved. ", footerFont));
        footer.add(new Chunk("Contact: support@skillexchange.tn", footerAccentFont));
        footer.setAlignment(Element.ALIGN_CENTER);

        footerCell.addElement(footer);
        footerTable.addCell(footerCell);
        document.add(footerTable);

        document.close();
        return baos.toByteArray();
    }

    private void addStyledDetailRow(PdfPTable table, String label, String value) {
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, DARK_GRAY);
        Font valueFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DARK_GRAY);

        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(8f);
        labelCell.setBackgroundColor(LIGHT_GRAY);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(8f);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private byte[] generateEmptyInvoice(String paymentIntentId) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, PRIMARY_ORANGE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, DARK_GRAY);

        Paragraph title = new Paragraph("No Invoice Available", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        Paragraph idInfo = new Paragraph("Payment ID: " + paymentIntentId, normalFont);
        Paragraph message = new Paragraph("There is no transaction associated with this payment.", normalFont);

        document.add(idInfo);
        document.add(message);

        document.close();
        return baos.toByteArray();
    }
}