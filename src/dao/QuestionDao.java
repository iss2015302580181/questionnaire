package dao;

import entity.Options;
import entity.Question;
import entity.QuestionnaireQuestion;
import entity.UserAnswer;

import java.util.List;
import java.util.Map;

/**
 * Created by wzzz on 2019/3/21.
 */
public interface QuestionDao {
    int addQuestion(Question question, List<Options> options);
    boolean addAnswer(UserAnswer userAnswer);
    boolean addQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion);
    Map<String,Integer> getSingleAndMultiStatistic(int groupId, int questionnaireId, int questionId);
    List<String> getfillStatistic(int groupId,int questionnaireId,int questionId);
}
