package tn.esprit.skillexchange.Service.GestionProduit;

import tn.esprit.skillexchange.Entity.GestionProduit.Payment;

public interface IPaymentService {
    public Payment processPayment(Payment payment);
}
