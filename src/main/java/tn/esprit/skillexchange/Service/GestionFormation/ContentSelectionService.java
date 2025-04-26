package tn.esprit.skillexchange.Service.GestionFormation;

import tn.esprit.skillexchange.Entity.GestionFormation.CourseContent;
import tn.esprit.skillexchange.Entity.GestionFormation.UserCourseContentSelection;

import java.util.List;

public interface ContentSelectionService {
    public List<UserCourseContentSelection> retrieveAllselections();
    public UserCourseContentSelection retrieveSelection(int selectionId);
    public UserCourseContentSelection addSelection(UserCourseContentSelection s);
    public void removeSeletion(int selectionId);

    public List<UserCourseContentSelection> getUserSelections(int userId) ;
    public UserCourseContentSelection findByUserIdAndCourseContentId(int userId, int courseContentId) ;

}
