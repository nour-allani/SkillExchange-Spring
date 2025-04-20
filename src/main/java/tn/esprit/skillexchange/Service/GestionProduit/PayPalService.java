package tn.esprit.skillexchange.Service.GestionProduit;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PayPalService {
    private final APIContext apiContext;

    public String createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {

        // Total payé
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total)); // format "12.00"

        // Transaction
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Payer
        Payer payer = new Payer();
        payer.setPaymentMethod(method); // "paypal"

        Payment payment = new Payment();
        payment.setIntent(intent); // "sale"
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // Redirection après paiement
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        // Retourne l’URL d’approbation PayPal
        for (Links link : createdPayment.getLinks()) {
            if ("approval_url".equals(link.getRel())) {
                return link.getHref();
            }
        }

        return null;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId(payerId);
        return payment.execute(apiContext, execution);
    }
}
