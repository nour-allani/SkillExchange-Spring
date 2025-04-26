package tn.esprit.skillexchange.Controller.GestionProduit;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Service.GestionProduit.InvoicePdfService;

import java.io.OutputStream;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoicePdfService invoicePdfService;

    @GetMapping("/{paymentIntentId}")
    public void downloadInvoice(@PathVariable String paymentIntentId, HttpServletResponse response) {
        try {
            byte[] pdfBytes = invoicePdfService.generateInvoiceFromStripe(paymentIntentId);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice_" + paymentIntentId + ".pdf");

            OutputStream os = response.getOutputStream();
            os.write(pdfBytes);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
