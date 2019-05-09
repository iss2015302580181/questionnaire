package impl;

import dao.QuestionnaireDao;
import entity.GroupQuestionnaire;
import entity.Options;
import entity.Question;
import entity.Questionnaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/18.
 */
public class QuestionnaireDaoImpl implements QuestionnaireDao {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public QuestionnaireDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createQuestionnaire(Questionnaire questionnaire) {
        int flag = -1;
//        0:未发布 1:已发布
        String sql = "INSERT INTO questionnaire(creator_id,que_title,que_dis,is_published,que_deadline) VALUES(?,?,?,0,?)";
        try {
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, questionnaire.getCreatorId());
            preparedStatement.setString(2, questionnaire.getQueTitle());
            preparedStatement.setString(3, questionnaire.getQueDis());
            preparedStatement.setTimestamp(4,questionnaire.getTimestamp());
            int result = preparedStatement.executeUpdate();
            if (result != -1){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (rs.next())
                    id = rs.getInt(1);
                return id;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean deleteQuestionnaire(int questionnaireId) {
        boolean flag = false;
        String sql = "DELETE FROM questionnaire WHERE questionnaire_id=?";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, questionnaireId);
            int result = preparedStatement.executeUpdate();
            if (result != -1)
                flag = true;

            sql="DELETE FROM question WHERE question_id IN (SELECT question_id FROM questionnaire_question WHERE questionnaire_id=?";
            preparedStatement.setInt(1,questionnaireId);
            preparedStatement.executeUpdate();
            connection.commit();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Questionnaire> getQuestionnairesByUserIdAndGroupIdA(int userId, int groupId) {
        List<Questionnaire> list = new ArrayList<>();
        String sql = "SELECT DISTINCT q.questionnaire_id,q.que_title,q.que_deadline FROM questionnaire AS q,group_questionnaire AS gq,user_answer AS ua WHERE gq.group_id=? AND" +
                " q.questionnaire_id  IN (SELECT questionnaire_id FROM user_answer WHERE user_id=? AND group_id=?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setQuestionnaireId(resultSet.getInt(1));
                questionnaire.setQueTitle(resultSet.getString(2));
                questionnaire.setTimestamp(resultSet.getTimestamp(3));
                list.add(questionnaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Questionnaire> getQuestionnairesByUserIdAndGroupIdB(int userId, int groupId) {

        List<Questionnaire> list = new ArrayList<>();
        String sql = "SELECT DISTINCT q.questionnaire_id,q.que_title,q.que_deadline FROM group_questionnaire AS gq LEFT JOIN user_answer AS ua ON gq.group_id=ua.group_id AND gq.questionnaire_id=ua.questionnaire_id" +
                " AND ua.user_id=? LEFT JOIN questionnaire AS q ON gq.questionnaire_id=q.questionnaire_id WHERE gq.group_id=? AND ua.user_id IS NULL";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setQuestionnaireId(resultSet.getInt(1));
                questionnaire.setQueTitle(resultSet.getString(2));
                questionnaire.setTimestamp(resultSet.getTimestamp(3));
                list.add(questionnaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Questionnaire> getQuestionnairesByCreatorId(int creatorId) {
        List<Questionnaire> list = new ArrayList<>();
        String sql="SELECT * FROM questionnaire WHERE creator_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, creatorId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setQuestionnaireId(resultSet.getInt(1));
                questionnaire.setQueTitle(resultSet.getString(3));
                questionnaire.setIsPublished(resultSet.getInt(5));
                questionnaire.setTimestamp(resultSet.getTimestamp(6));
                list.add(questionnaire);
            }
        }catch (SQLException e){

        }
        return list;
    }

    @Override
    public Questionnaire getQuestionnaireById(int questionnaireId) {
        Questionnaire questionnaire=null;
        String sql="SELECT * FROM questionnaire WHERE Questionnaire_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, questionnaireId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                questionnaire = new Questionnaire();
                questionnaire.setQuestionnaireId(resultSet.getInt(1));
                questionnaire.setQueTitle(resultSet.getString(3));
                questionnaire.setQueDis(resultSet.getString(4));
                questionnaire.setIsPublished(resultSet.getInt(5));
                questionnaire.setTimestamp(resultSet.getTimestamp(6));
            }
        }catch (SQLException e){

        }
        return questionnaire;
    }

    @Override
    public List<Question> getQuestionsByQuestionnaireId(int questionnaireId) {
        List<Question> list=new ArrayList<>();
        String sql="SELECT q.* FROM question AS q,questionnaire_question AS qq WHERE qq.questionnaire_id=? AND q.question_id=qq.question_id";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, questionnaireId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                Question question=new Question();
                question.setQuestionId(resultSet.getInt(1));
                int type=resultSet.getInt(3);
                question.setType(type);
                question.setContent(resultSet.getString(4));
                if(type==0||type==1) {
                    List<Options> options = new ArrayList<>();
                    String sql1 = "SELECT question_option FROM options WHERE question_id=?";
                    preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.setInt(1, resultSet.getInt(1));
                    ResultSet resultSet1 = preparedStatement.executeQuery();
                    int optionIndex = 0;
                    while (resultSet1.next()) {
                        Options option = new Options();
                        option.setQuestionOption((char)(65+optionIndex)+"、"+resultSet1.getString(1));
                        optionIndex++;
                        options.add(option);
                    }
                    question.setOptions(options);
                }
                list.add(question);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getAnswerByUserIdGroupIdQuestionnaireIdAndQuestionId(int userId,int groupId, int questionnaireId, int questionId) {
        String result=null;
        String sql="SELECT answer FROM user_answer WHERE user_id=? AND group_id=? AND questionnaire_id=? AND question_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.setInt(3, questionnaireId);
            preparedStatement.setInt(4, questionId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=resultSet.getString(1);
            }
        }catch (SQLException e){

        }
        return result;
    }

    @Override
    public List<Questionnaire> getQuestionnairesByGroupId(int groupId) {
        List<Questionnaire> list = new ArrayList<>();
        String sql = "SELECT q.* FROM questionnaire AS q,group_questionnaire AS gq WHERE q.questionnaire_id=gq.questionnaire_id AND gq.group_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setQuestionnaireId(resultSet.getInt(1));
                questionnaire.setQueTitle(resultSet.getString(3));
                questionnaire.setIsPublished(resultSet.getInt(5));
                list.add(questionnaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isContainGroupQuestionnaire(int groupId, int questionnaireId) {
        boolean flag=false;
        String sql="SELECT * FROM group_questionnaire WHERE group_id=? AND questionnaire_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,groupId);
            preparedStatement.setInt(2,questionnaireId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
                flag=true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean groupQuestionnaire(GroupQuestionnaire groupQuestionnaire) {
        boolean flag = false;
        String sql = "INSERT INTO group_questionnaire VALUES(?,?)";
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, groupQuestionnaire.getGroupId());
            preparedStatement.setInt(2, groupQuestionnaire.getQuestionnaireId());
            int result = preparedStatement.executeUpdate();
            if (result >0)
                flag = true;
            sql="UPDATE questionnaire SET is_published=1 WHERE questionnaire_id=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,groupQuestionnaire.getQuestionnaireId());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
