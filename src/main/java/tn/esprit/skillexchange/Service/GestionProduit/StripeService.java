package tn.esprit.skillexchange.Service.GestionProduit;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CurrencyType;
import tn.esprit.skillexchange.Entity.GestionProduit.Payment;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.PaymentRepo;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class StripeService {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private  PaymentRepo paymentRepo;

    public String createCheckoutSessionFromCart(float amount, Long cartId) {
        Stripe.apiKey = stripeSecretKey;

        Cart cart = cartRepo.findById(cartId).orElse(null);
        if (cart == null) return null;

        String description = cart.getCartProducts().stream()
                .map(cp -> cp.getProduct().getProductName() + " x" + cp.getQuantity())
                .collect(Collectors.joining(", "));

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .putMetadata("cartId", String.valueOf(cartId))
                .putMetadata("userEmail", cart.getUser().getEmail()) // ✅ si Cart contient bien un user
                .putMetadata("currencyType", "TND")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (amount * 100)) // montant en cents
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Panier: " + description)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Session retrieveSession(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            return Session.retrieve(sessionId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la session Stripe", e);
        }
    }

}
