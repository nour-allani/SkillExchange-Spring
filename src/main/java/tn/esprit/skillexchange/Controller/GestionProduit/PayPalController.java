package tn.esprit.skillexchange.Controller.GestionProduit;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Service.GestionProduit.PayPalService;

@RestController
@RequestMapping("/api/paypal")
@AllArgsConstructor
public class PayPalController {
    private final PayPalService payPalService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(
            @RequestParam("amount") Double amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description) {

        try {
            String cancelUrl = "http://localhost:4200/payment/cancel";
            String successUrl = "http://localhost:4200/payment/success";

            String paymentLink = payPalService.createPayment(
                    amount,
                    currency,
                    "paypal",
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            return ResponseEntity.ok(paymentLink);

        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body("Erreur PayPal : " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {

        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            return ResponseEntity.ok("✅ Paiement confirmé ! ID: " + payment.getId());
        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body("Erreur lors de la confirmation : " + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("❌ Paiement annulé.");
    }
}
