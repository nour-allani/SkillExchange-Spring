package tn.esprit.skillexchange.Service.Gestionquizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.Courses;
import tn.esprit.skillexchange.Entity.GestionQuiz.Quiz;
import tn.esprit.skillexchange.Repository.GestionFormation.CourseRepo;
import tn.esprit.skillexchange.Repository.GestionQuiz.QuizRepo;
import tn.esprit.skillexchange.Service.Gestionquizz.QuizService;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepo quizRepository;
    @Autowired
    private CourseRepo courseRepo;


    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz createQuiz(String title, String base64Image) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setImage(base64Image);  // Set the image (base64 string)
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Long id, String title, String base64Image) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setTitle(title);
        quiz.setImage(base64Image);  // Update the image (base64 string)
        return quizRepository.save(quiz);
    }
    @Override
    public Quiz updateQuizImage(Long id, String image) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        quiz.setImage(image);
        return quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Courses assignQuizCourse(Long quizId, Long courseid)
    {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Courses course = courseRepo.findById(courseid)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setQuiz(quiz);

        return courseRepo.save(course);
    }

}

