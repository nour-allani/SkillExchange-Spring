package tn.esprit.skillexchange.Service.GestionProduit;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.*;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;
import tn.esprit.skillexchange.Entity.GestionUser.TransactionType;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionProduit.CartProductRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.PaymentRepo;
import tn.esprit.skillexchange.Repository.GestionUser.HistoricTransictionRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class PaymentServiceImpl implements  IPaymentService {
   @Autowired
    private  PaymentRepo paymentRepository;
   @Autowired
    private  UserRepo userRepository;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartProductRepo cartProductRepo;

    @Autowired
    private HistoricTransictionRepo transactionRepo;
    @Transactional

    public Payment processPayment(Payment payment) {

        String email = payment.getUserEmail();
        Optional<User> optionalUser = (email != null) ? userRepository.findByEmail(email) : Optional.empty();

        Optional<Cart> optionalCart = (payment.getCart() != null && payment.getCart().getId() != 0)
                ? cartRepo.findById(payment.getCart().getId())
                : Optional.empty();

        payment.setDatePaiement(new Date());

        if (optionalUser.isEmpty() || optionalCart.isEmpty()) {
            payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            return paymentRepository.save(payment);
        }

        User user = optionalUser.get();
        Cart cart = optionalCart.get();


        payment.setCart(cart);

        if (payment.getMethodePaiement() == Payment.PaymentMethod.BALANCE) {
            if (user.getBalance() >= payment.getMontant()) {
                user.setBalance(user.getBalance() - payment.getMontant());
                userRepository.save(user);

                payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
            } else {
                payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            }
        } else {
            payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
        }

        return paymentRepository.save(payment);
    }


   /* @Override
    public void saveStripePayment(String email, float amount, String sessionId, String stripeCurrency) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            System.err.println("❌ Utilisateur introuvable pour l’email : " + email);
            return;
        }

        User user = optionalUser.get();
        Cart cart = cartRepo.findByUserAndIsActiveTrue(user);

        if (cart == null) {
            System.err.println("❌ Aucun panier actif trouvé pour l’utilisateur : " + user.getEmail());
            return;
        }


        CurrencyType currencyType;
        try {
            if (stripeCurrency.equalsIgnoreCase("USD")) {
                currencyType = CurrencyType.TND; // ou CurrencyType.USD si tu veux l'ajouter
            } else {
                currencyType = CurrencyType.valueOf(stripeCurrency.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Devise inconnue : " + stripeCurrency + " → Utilisation de TND par défaut.");
            currencyType = CurrencyType.TND;
        }

        Payment payment = new Payment();
        payment.setUserEmail(email);
        payment.setCart(cart);
        payment.setMontant(amount);
        payment.setMethodePaiement(Payment.PaymentMethod.STRIPE);
        payment.setCurrencyType(currencyType);
        payment.setDatePaiement(new Date());
        payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);

        paymentRepository.save(payment);



        System.out.println("✅ Paiement Stripe enregistré avec succès pour " + email);
    }
*/
   @Override
   @Transactional
   public void saveStripePayment(String email, float amount, String sessionId, String stripeCurrency) {
       Optional<User> optionalUser = userRepository.findByEmail(email);

       if (optionalUser.isEmpty()) {
           System.err.println("❌ Utilisateur introuvable pour l’email : " + email);
           return;
       }

       User user = optionalUser.get();
       Cart cart = cartRepo.findByUserAndIsActiveTrue(user);

       if (cart == null) {
           System.err.println("❌ Aucun panier actif trouvé pour l’utilisateur : " + user.getEmail());
           return;
       }


       CurrencyType currencyType;
       try {
           if (stripeCurrency.equalsIgnoreCase("USD")) {
               currencyType = CurrencyType.TND; // adapter selon ton enum
           } else {
               currencyType = CurrencyType.valueOf(stripeCurrency.toUpperCase());
           }
       } catch (IllegalArgumentException e) {
           System.err.println("❌ Devise inconnue : " + stripeCurrency + " → Utilisation de TND par défaut.");
           currencyType = CurrencyType.TND;
       }


       Payment payment = new Payment();
       payment.setUserEmail(email);
       payment.setCart(cart);
       payment.setMontant(amount);
       payment.setMethodePaiement(Payment.PaymentMethod.STRIPE);
       payment.setCurrencyType(currencyType);
       payment.setDatePaiement(new Date());
       payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
       paymentRepository.save(payment);


       List<CartProduct> cartProducts = cartProductRepo.findByCart(cart);
       for (CartProduct cp : cartProducts) {
           Product product = cp.getProduct();
           int quantity = cp.getQuantity();

           System.out.println("🔎 Produit : " + product.getProductName() + " (type: " + product.getType() + ")");

           if (product.getType() == ProductType.TOKENS) {
               int tokens = extractTokenAmount(product.getProductName()) * quantity;

               System.out.println("🧮 Tokens calculés : " + tokens);

               // Ajouter les tokens au solde utilisateur
               user.setBalance(user.getBalance() + tokens);
               userRepository.save(user);

               // Enregistrer la transaction
               HistoricTransactions tx = new HistoricTransactions();
               tx.setUser(user);
               tx.setAmount(tokens);
               tx.setType(TransactionType.DEPOSIT);
               tx.setDescription("Recharge via Stripe: " + product.getProductName());
               tx.setDate(new Date());

               transactionRepo.save(tx);

               System.out.println("✅ " + tokens + " tokens ajoutés au solde de " + user.getEmail());
           }
       }
   }


    private int extractTokenAmount(String productName) {
        try {
            return Integer.parseInt(productName.trim().split(" ")[0]);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur d’extraction de tokens pour : " + productName);
            return 0;
        }
    }


}









