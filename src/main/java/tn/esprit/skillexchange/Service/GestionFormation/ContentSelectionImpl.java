package tn.esprit.skillexchange.Service.GestionFormation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionFormation.UserCourseContentSelection;
import tn.esprit.skillexchange.Repository.GestionFormation.UserCourseContentSelectionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentSelectionImpl implements ContentSelectionService {
    @Autowired
    private UserCourseContentSelectionRepository selectionRepository;

    @Override
    public List<UserCourseContentSelection> retrieveAllselections() {
        return selectionRepository.findAll();
    }

    @Override
    public UserCourseContentSelection retrieveSelection(int selectionId) {
        return selectionRepository.findById(selectionId).get();
    }

    @Override
    public UserCourseContentSelection addSelection(UserCourseContentSelection s) {
        return selectionRepository.save(s);
    }

    @Override
    public void removeSeletion(int selectionId) {
        selectionRepository.deleteById(selectionId);
    }

    @Override
    public List<UserCourseContentSelection> getUserSelections(int userId) {

        return selectionRepository.findByUserId(userId);
    }

    @Override
    public UserCourseContentSelection findByUserIdAndCourseContentId(int userId, int courseContentId) {
        return selectionRepository.findByUserIdAndCourseContentId(userId,courseContentId);
    }
}
