package tn.esprit.skillexchange.Service.GestionProduit;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionProduit.Cart;
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









