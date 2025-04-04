package tn.esprit.skillexchange.Service.Gestionquizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionQuiz.Result;
import tn.esprit.skillexchange.Entity.GestionQuiz.UserAnswer;
import tn.esprit.skillexchange.Repository.GestionQuiz.ResultRepository;
import tn.esprit.skillexchange.Repository.GestionQuiz.UserAnswerRepository;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private ResultRepository resultRepository;

    // Method to calculate the result and save it
    public Result calculateResult(int participationCourseId) {
        // Retrieve all user answers for this participation course
        List<UserAnswer> userAnswers = userAnswerRepository.findAllByParticipationCourse_Idp(participationCourseId);

        int correctAnswers = 0;
        int totalQuestions = userAnswers.size();

        // Count the number of correct answers
        for (UserAnswer userAnswer : userAnswers) {
            if (userAnswer.isCorrect()) {
                correctAnswers++;
            }
        }

        // Calculate the score (this can be adjusted based on your requirements)
        int score = (int) ((correctAnswers / (double) totalQuestions) * 100);

        // Create and save the result
        Result result = new Result();
        result.setParticipationCourse(userAnswers.get(0).getParticipationCourse());
        result.setScore(score);
        result.setCorrectAnswers(correctAnswers);
        result.setTotalQuestions(totalQuestions);

        return resultRepository.save(result);
    }
}
