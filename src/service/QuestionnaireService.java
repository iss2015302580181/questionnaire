package service;

import dao.QuestionnaireDao;
import entity.GroupQuestionnaire;
import entity.Question;
import entity.Questionnaire;
import impl.QuestionnaireDaoImpl;
import util.DbConnection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/19.
 */
public class QuestionnaireService {
    private DbConnection dbConnection;
    private QuestionnaireDao questionnaireDao;

    public QuestionnaireService() {
        this.dbConnection = new DbConnection();
        this.questionnaireDao = new QuestionnaireDaoImpl(dbConnection.getConnection());
    }

    //借用isPublished属性标明是否填写，实际此属性用于问卷发布者，用以表示问卷是否发布，此处该功能用不到
    public List<Questionnaire> getQuestionnaireByUserIdAndGroupId(int userId, int groupId) {
        List<Questionnaire> list = new ArrayList<>();
        List<Questionnaire> list1 = questionnaireDao.getQuestionnairesByUserIdAndGroupIdB(userId, groupId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (Questionnaire q : list1) {
            if (q.getTimestamp().before(timestamp))
                q.setIsPublished(0);
            else
                q.setIsPublished(1);
            list.add(q);
        }
        list1 = questionnaireDao.getQuestionnairesByUserIdAndGroupIdA(userId, groupId);
        for (Questionnaire q : list1) {
                q.setIsPublished(2);
            list.add(q);
        }
        return list;
    }

    public List<Questionnaire> getQuestionnairesByGroupId(int groupId) {
        return questionnaireDao.getQuestionnairesByGroupId(groupId);
    }

    public Questionnaire getQuestionnaireById(int questionnaireId) {
        return questionnaireDao.getQuestionnaireById(questionnaireId);
    }

    //    做题/上传时所用，不涉及答案部分
    public List<Question> getQuestionsByQuestionnaireId(int questionnaireId) {
        return questionnaireDao.getQuestionsByQuestionnaireId(questionnaireId);
    }

    //    查看问卷部分，涉及到答案的查看
    public List<Question> getQuestionsAndAnswer(int questionnaireId, int groupId, int userId) {
        List<Question> list = questionnaireDao.getQuestionsByQuestionnaireId(questionnaireId);
        for (int i = 0; i < list.size(); i++) {
            Question question = list.get(i);
            String answer = questionnaireDao.getAnswerByUserIdGroupIdQuestionnaireIdAndQuestionId(userId, groupId, questionnaireId, question.getQuestionId());
            question.setAnswer(answer);
        }
        return list;
    }

    public List<Questionnaire> getQuestionnairesByCreatorId(int creatorId) {
        List<Questionnaire> list=questionnaireDao.getQuestionnairesByCreatorId(creatorId);
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        for(int i=0;i<list.size();i++){
            if(list.get(i).getTimestamp().before(timestamp))
                list.get(i).setIsOutOfDate(1);
        }
        return list;
    }

    public boolean groupQuestionnaire(GroupQuestionnaire groupQuestionnaire) {
        if (questionnaireDao.isContainGroupQuestionnaire(groupQuestionnaire.getGroupId(), groupQuestionnaire.getQuestionnaireId()))
            return false;
        return questionnaireDao.groupQuestionnaire(groupQuestionnaire);
    }

    public int createQuestionnaire(Questionnaire questionnaire) {
        return questionnaireDao.createQuestionnaire(questionnaire);
    }

    public boolean deleteQuestionnaire(int questionnaireId) {
        return questionnaireDao.deleteQuestionnaire(questionnaireId);
    }
}
