package tn.esprit.skillexchange.Service.GestionUser.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Repository.GestionUser.BannedRepo;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;

import java.util.Date;

@Service
public class BanScheduler {

    @Autowired
    private IUserService userService;

    @Autowired
    private BannedRepo bannedRepository;

    //@Scheduled(cron = "0 0 * * * ?") // every hour
    //@Scheduled(cron = "0 * * * * ?") every min for testing
    public void checkExpiredBans() {
        Date now = new Date();
        bannedRepository.findByEndDateBefore(now).forEach(ban -> {
            userService.unbanUser(ban.getUser().getId());
        });
    }
}
