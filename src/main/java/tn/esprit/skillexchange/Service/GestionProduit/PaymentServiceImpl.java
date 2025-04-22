package tn.esprit.skillexchange.Service.GestionProduit;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
import tn.esprit.skillexchange.Entity.GestionProduit.CurrencyType;
import tn.esprit.skillexchange.Entity.GestionProduit.Payment;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionProduit.CartRepo;
import tn.esprit.skillexchange.Repository.GestionProduit.PaymentRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;

import java.util.Date;
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

        // Lier le vrai objet Cart récupéré
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
    /*public void saveStripePayment(String email, float amount, String sessionId, String currencyCode) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return;

        User user = userOpt.get();
        Cart cart = cartRepo.findByUserAndIsActiveTrue(user);
        Payment payment = new Payment();
        payment.setMontant(amount);
        payment.setDatePaiement(new Date());
        payment.setUserEmail(email);
        payment.setCart(cart);
        payment.setMethodePaiement(Payment.PaymentMethod.STRIPE);
        payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);

        payment.setCurrencyType(CurrencyType.valueOf(currencyCode.toUpperCase()));

        paymentRepository.save(payment);
    }
*/
    @Override
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

        // 🧠 Convertir "usd" ou autres en TND par défaut
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

 /* public Payment processPayment(Payment payment) {

        // Vérifie que l'utilisateur est bien défini
        if (payment.getUser() == null || payment.getUser().getId() == 0) {
            payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            payment.setDatePaiement(new Date());
            return paymentRepository.save(payment);
        }

        // Récupérer l'utilisateur en base
        User user = userRepository.findById(payment.getUser().getId()).orElse(null);
        if (user == null) {
            payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            payment.setDatePaiement(new Date());
            return paymentRepository.save(payment);
        }

        payment.setDatePaiement(new Date());

        if (payment.getMethodePaiement() == Payment.PaymentMethod.BALANCE) {
            if (user.getBalance() >= payment.getMontant()) {
                user.setBalance(user.getBalance() - payment.getMontant());
                payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
                userRepository.save(user);
            } else {
                payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            }
        } else {
            // Paiement externe (simulé)
            payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
        }

        return paymentRepository.save(payment);
    }*/

   /* public Payment processPayment(Payment payment) {
        String email = payment.getUserEmail();
        Optional<User> optionalUser = (email != null) ? userRepository.findByEmail(email) : Optional.empty();

        payment.setDatePaiement(new Date());

        if (optionalUser.isEmpty()) {
            payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            return paymentRepository.save(payment);
        }

        User user = optionalUser.get();

        if (payment.getMethodePaiement() == Payment.PaymentMethod.BALANCE) {
            if (user.getBalance() >= payment.getMontant()) {
                user.setBalance(user.getBalance() - payment.getMontant());
                payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
                userRepository.save(user);
            } else {
                payment.setStatutPaiement(Payment.PaymentStatus.FAILED);
            }
        } else {
            payment.setStatutPaiement(Payment.PaymentStatus.COMPLETED);
        }

        return paymentRepository.save(payment);
    }*/

}









