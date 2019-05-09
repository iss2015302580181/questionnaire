package impl;

import dao.QuestionDao;
import entity.Options;
import entity.Question;
import entity.QuestionnaireQuestion;
import entity.UserAnswer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzzz on 2019/3/21.
 */
public class QuestionDaoImpl implements QuestionDao {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public QuestionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int addQuestion(Question question, List<Options> options) {
        int flag = -1;
        String sql = "INSERT INTO question(user_id,type,content) VALUES(?,?,?)";

        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, question.getUserId());
            preparedStatement.setInt(2, question.getType());
            preparedStatement.setString(3, question.getContent());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            int id = -1;
            if (rs.next())
                id = rs.getInt(1);
            flag = id;

            sql="INSERT INTO options VALUES(?,?)";
            for(int i=0;i<options.size();i++){
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,flag);
                preparedStatement.setString(2,options.get(i).getQuestionOption());
                preparedStatement.executeUpdate();
            }
            connection.commit();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public boolean addAnswer(UserAnswer userAnswer) {
        boolean flag = false;
        String sql = "INSERT INTO user_answer VALUES(?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userAnswer.getUserId());
            preparedStatement.setInt(2, userAnswer.getGroupId());
            preparedStatement.setInt(3, userAnswer.getQuestionnaireId());
            preparedStatement.setInt(4, userAnswer.getQuestionId());
            preparedStatement.setString(5, userAnswer.getAnswer());
            int result = preparedStatement.executeUpdate();
            if (result != -1)
                flag = true;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean addQuestionnaireQuestion(QuestionnaireQuestion questionnaireQuestion) {
        boolean flag = false;
        String sql = "INSERT INTO questionnaire_question VALUES(?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, questionnaireQuestion.getQuestionnaireId());
            preparedStatement.setInt(2, questionnaireQuestion.getQuestionId());
            int result = preparedStatement.executeUpdate();
            if (result != -1)
                flag = true;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Map<String,Integer> getSingleAndMultiStatistic(int groupId, int questionnaireId, int questionId) {
        String sql = "SELECT answer FROM user_answer WHERE group_id=? AND questionnaire_id=? AND question_id=?";
        Map<String,Integer> map=new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, questionnaireId);
            preparedStatement.setInt(3, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            map.put("total",resultSet.getRow());
            resultSet.beforeFirst();

            while (resultSet.next()) {
                String answer = resultSet.getString(1);
                String[] chars=answer.split("");
                for(int i=0;i<chars.length;i++){
                    if(map.containsKey(chars[i]))
                        map.put(chars[i],map.get(chars[i])+1);
                    else
                        map.put(chars[i],1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<String> getfillStatistic(int groupId, int questionnaireId, int questionId) {
        List<String> list = new ArrayList();
        String sql = "SELECT answer FROM user_answer WHERE group_id=? AND questionnaire_id=? AND question_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, questionnaireId);
            preparedStatement.setInt(3, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
