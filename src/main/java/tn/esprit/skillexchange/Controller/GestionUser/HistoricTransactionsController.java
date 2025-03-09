package tn.esprit.skillexchange.Controller.GestionUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionUser.HistoricTransactions;
import tn.esprit.skillexchange.Service.GestionUser.IHistoricTransactionsService;

import java.util.List;

@RestController
@RequestMapping("/historicTransactions")
public class HistoricTransactionsController {

    @Autowired
    private IHistoricTransactionsService historicTransactionsService;


    @GetMapping
    public List<HistoricTransactions> getAllHistoricTransactions() {
        return historicTransactionsService.retrieveAllHistorique();
    }

    @PostMapping
    public HistoricTransactions addHistoricTransaction(@RequestBody HistoricTransactions historicTransactions) {
        return historicTransactionsService.add(historicTransactions);
    }

    @PutMapping
    public HistoricTransactions updateHistoricTransaction(@RequestBody HistoricTransactions historicTransactions) {
        return historicTransactionsService.update(historicTransactions);
    }

    @GetMapping("/{id}")
    public HistoricTransactions getHistoricTransactionById(@PathVariable Long id) {
        return historicTransactionsService.retrieveHistoriqueById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHistoricTransaction(@PathVariable Long id) {
        historicTransactionsService.remove(id);
    }

}
