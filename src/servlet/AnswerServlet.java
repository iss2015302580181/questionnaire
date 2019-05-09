package servlet;

import entity.Question;
import entity.User;
import entity.UserAnswer;
import factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/24.
 */
@WebServlet(name = "AnswerServlet",
        urlPatterns = {"/answer"})
public class AnswerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = ((User) request.getSession().getAttribute("user")).getUserId();
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        int questionnaireId = Integer.parseInt(request.getParameter("questionnaireId"));
        List<UserAnswer> list = new ArrayList<>();
        List<Question> questionList = DAOFactory.getQuestionnaireDaoInstance().getQuestionsByQuestionnaireId(questionnaireId);
        for (int i = 0; i < questionList.size(); i++) {
            if (null != request.getParameter("question_" + questionList.get(i).getQuestionId()) && !"".equals(request.getParameter("question_" + questionList.get(i).getQuestionId()))) {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setGroupId(groupId);
                userAnswer.setQuestionnaireId(questionnaireId);
                userAnswer.setQuestionId(questionList.get(i).getQuestionId());
                userAnswer.setUserId(userId);
                if (questionList.get(i).getType() == 0)
                    userAnswer.setAnswer(request.getParameter("question_" + questionList.get(i).getQuestionId()).substring(0,1));
                else if(questionList.get(i).getType() == 1){
                    String[] options = request.getParameterValues("question_" + questionList.get(i).getQuestionId());
                    String answer = "";
                    for (int j = 0; j < options.length; j++)
                        answer += options[j].substring(0,1);
                    userAnswer.setAnswer(answer);
                }else if(questionList.get(i).getType() == 2){
                    userAnswer.setAnswer(request.getParameter("question_" + questionList.get(i).getQuestionId()));
                }
                list.add(userAnswer);
            }
        }
        DAOFactory.getQuestionDaoInstance().addAnswer(list);
        request.getRequestDispatcher("/consumer?action=consumer").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
