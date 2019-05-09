package dao;

import entity.GroupQuestionnaire;
import entity.Question;
import entity.Questionnaire;

import java.util.List;

/**
 * Created by wzzz on 2019/3/18.
 */
public interface QuestionnaireDao {
    int createQuestionnaire(Questionnaire questionnaire);
    boolean deleteQuestionnaire(int questionnaireId);
    //分别获得已填写/未填写问卷
    List<Questionnaire> getQuestionnairesByUserIdAndGroupIdA(int userId,int groupId);
    List<Questionnaire> getQuestionnairesByUserIdAndGroupIdB(int userId,int groupId);
    List<Questionnaire> getQuestionnairesByCreatorId(int creatorId);
    Questionnaire getQuestionnaireById(int questionnaireId);
    List<Question> getQuestionsByQuestionnaireId(int questionnaireId);
    String getAnswerByUserIdGroupIdQuestionnaireIdAndQuestionId(int userId,int groupId,int questionnaireId,int questionId);
    List<Questionnaire> getQuestionnairesByGroupId(int groupId);
    boolean isContainGroupQuestionnaire(int groupId,int questionnaireId);
    boolean groupQuestionnaire(GroupQuestionnaire groupQuestionnaire);
}
