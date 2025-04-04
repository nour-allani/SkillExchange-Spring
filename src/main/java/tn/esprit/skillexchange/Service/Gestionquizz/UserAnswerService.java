package tn.esprit.skillexchange.Service.Gestionquizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;
import tn.esprit.skillexchange.Repository.GestionQuiz.UserAnswerRepository;

@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    // Method to save the user's answer
    public UserAnswer saveUserAnswer(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }
}
