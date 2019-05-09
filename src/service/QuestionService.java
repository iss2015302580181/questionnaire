package service;

import dao.QuestionDao;
import entity.Options;
import entity.Question;
import entity.QuestionnaireQuestion;
import entity.UserAnswer;
import impl.QuestionDaoImpl;
import util.DbConnection;

import java.util.List;
import java.util.Map;

/**
 * Created by wzzz on 2019/3/21.
 */
public class QuestionService {
    private DbConnection dbConnection;
    private QuestionDao questionDao;

    public QuestionService() {
        dbConnection = new DbConnection();
        questionDao = new QuestionDaoImpl(dbConnection.getConnection());
    }

    public int addQuestion(Question question, List<Options> options) {
        return questionDao.addQuestion(question,options);
    }

    public void addAnswer(List<UserAnswer> list) {
        for (int i = 0; i < list.size(); i++) {
            questionDao.addAnswer(list.get(i));
        }
    }

    public boolean addQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion){
        return questionDao.addQuestionnaireQuestion(questionnaireQuestion);
    }

    public List<String> getfillStatistic(int groupId, int questionnaireId, int questionId) {
        return  questionDao.getfillStatistic(groupId,questionnaireId,questionId);
    }
    public Map<String,Integer> getSingleAndMultiStatistic(int groupId, int questionnaireId, int questionId) {
        return questionDao.getSingleAndMultiStatistic(groupId,questionnaireId,questionId);
    }

}
