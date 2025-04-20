package tn.esprit.skillexchange.Controller.GestionProduit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionProduit.Payment;
import tn.esprit.skillexchange.Service.GestionProduit.IPaymentService;
import tn.esprit.skillexchange.Service.GestionProduit.StripeService;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController  {
    @Autowired
    private IPaymentService payS;

    @Autowired
    private StripeService stripeService;
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
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
}
