package tn.esprit.skillexchange.Service.Gestionquizz;

import tn.esprit.skillexchange.Entity.GestionQuiz.Questions;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuestionRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionsServiceImpl implements IQuestionsService {

    @Autowired
    private QuestionRepo questionsRepository;

    @Override
    public Questions saveQuestion(Questions question) {
        return questionsRepository.save(question);
    }

    @Override
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }

    @Override
    public Optional<Questions> getQuestionById(Long id) {
        return questionsRepository.findById(id);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionsRepository.deleteById(id);
    }
}