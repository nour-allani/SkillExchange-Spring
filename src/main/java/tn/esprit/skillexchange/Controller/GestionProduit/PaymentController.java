package tn.esprit.skillexchange.Controller.GestionProduit;

import com.stripe.Stripe;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Payment;
import tn.esprit.skillexchange.Service.GestionProduit.IPaymentService;
import tn.esprit.skillexchange.Service.GestionProduit.InvoicePdfService;
import tn.esprit.skillexchange.Service.GestionProduit.StripeService;
import tn.esprit.skillexchange.Service.Mailing.GmailService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController  {
    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    @Autowired
    private IPaymentService payS;

    @Autowired
    private StripeService stripeService;
    @Autowired
    private InvoicePdfService invoicePdfService;
    @Autowired
    private GmailService gmailService;
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        System.out.println("📥 createPayment() appelé avec : " + payment);
        Payment result = payS.processPayment(payment);

        if (result.getStatutPaiement() == Payment.PaymentStatus.FAILED) {
            return ResponseEntity.badRequest().body("Le paiement a échoué. Solde insuffisant ou utilisateur invalide.");
        }

        return ResponseEntity.ok(result);
    }
    @PostMapping("/stripe-session")
    public ResponseEntity<String> createStripeSession(@RequestParam float amount,
                                                      @RequestParam Long cartId) {
        if (amount <= 0 || cartId == null ) {
            return ResponseEntity.badRequest().body("Montant ou produit invalide.");
        }

        String url = stripeService.createCheckoutSessionFromCart(amount,cartId);

        if (url == null) {
            return ResponseEntity.badRequest().body("Échec de la création de la session Stripe.");
        }

        return ResponseEntity.ok(url);
    }
    @GetMapping("/stripe/session-info")
    public ResponseEntity<Session> getStripeSession(@RequestParam String sessionId) {
        try {
            Session session = stripeService.retrieveSession(sessionId); // ✅ Appel de ton service
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        try {
            String payload = new BufferedReader(request.getReader())
                    .lines()
                    .collect(Collectors.joining("\n"));

            String sigHeader = request.getHeader("Stripe-Signature");
            Event event;

            try {
                event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);
            } catch (Exception e) {
                System.err.println("❌ Signature invalide : " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature invalide");
            }

            System.out.println("📦 Stripe Event Type: " + event.getType());

            if ("checkout.session.completed".equals(event.getType())) {
                Optional<StripeObject> dataObject = event.getDataObjectDeserializer().getObject();

                if (dataObject.isEmpty()) {
                    System.err.println("❌ Impossible de désérialiser la session Stripe !");
                    System.err.println("Payload brut : \n" + payload);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Session non parsée");
                }

                Session session = (Session) dataObject.get();

                if (session.getCustomerDetails() == null || session.getCustomerDetails().getEmail() == null) {
                    System.err.println("❌ Email non trouvé dans session.getCustomerDetails()");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email manquant");
                }

                String email = session.getCustomerDetails().getEmail();
                long amount = session.getAmountTotal(); // Stripe en centimes
                String currency = session.getCurrency();
                String sessionId = session.getId();

                System.out.println("✅ Paiement validé Stripe");
                System.out.println("→ Email: " + email);
                System.out.println("→ Montant: " + amount / 100f + " " + currency.toUpperCase());
                System.out.println("→ Session ID: " + sessionId);

                // Appel du service
                payS.saveStripePayment(email, amount / 100f, sessionId, currency);
                try {
                    // Génère la facture PDF à partir du PaymentIntent ID (récupérable via la session)
                    String paymentIntentId = session.getPaymentIntent();
                    byte[] pdf = invoicePdfService.generateInvoiceFromStripe(paymentIntentId);

                    String filename = "Invoice_" + paymentIntentId + ".pdf";
                    String subject = "📄 Your SkillExchange Invoice";

// Charger le template HTML depuis le fichier (tu peux aussi injecter un service de template)
                    String htmlContent = loadHtmlInvoiceTemplate(); // méthode à créer juste en bas

                    gmailService.sendInvoiceHtmlEmail(email, subject, htmlContent, pdf, filename);

                    gmailService.sendEmailWithAttachment(email, subject, htmlContent, pdf, filename);
                    System.out.println("📧 Facture envoyée à : " + email);
                } catch (Exception ex) {
                    System.err.println("❌ Erreur lors de l'envoi de la facture : " + ex.getMessage());
                }

            }

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            System.err.println("❌ Erreur fatale Stripe Webhook :");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error");
        }
    }
    public String loadHtmlInvoiceTemplate() {
        try (InputStream input = new ClassPathResource("templates/email/invoice-email.html").getInputStream()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "Invoice attached.";
        }
    }


}
