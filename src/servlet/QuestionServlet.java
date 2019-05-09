package servlet;

import entity.Question;
import entity.Questionnaire;
import entity.User;
import factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wzzz on 2019/3/21.
 */
@WebServlet(name = "QuestionServlet",
        urlPatterns = {"/question"})
public class QuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = ((User) request.getSession().getAttribute("user")).getUserId();
        int questionnaireId = Integer.parseInt(request.getParameter("questionnaireId"));
        int groupId=Integer.parseInt(request.getParameter("groupId"));
        if (action.equals("fill")) {
            Questionnaire questionnaire = DAOFactory.getQuestionnaireDaoInstance().getQuestionnaireById(questionnaireId);
            request.setAttribute("questionnaire", questionnaire);
            List<Question> list = DAOFactory.getQuestionnaireDaoInstance().getQuestionsByQuestionnaireId(questionnaireId);
            request.setAttribute("questions", list);
            request.setAttribute("groupId",groupId);
            request.getRequestDispatcher("/questionnaireFill.jsp").forward(request, response);
        } else if (action.equals("check")) {
            Questionnaire questionnaire = DAOFactory.getQuestionnaireDaoInstance().getQuestionnaireById(questionnaireId);
            request.setAttribute("questionnaire", questionnaire);
            List<Question> list=DAOFactory.getQuestionnaireDaoInstance().getQuestionsAndAnswer(questionnaireId,groupId,userId);
            request.setAttribute("questions", list);
            request.getRequestDispatcher("/questionnaire.jsp").forward(request, response);
        } else if(action.equals("statistic")){
            Questionnaire questionnaire = DAOFactory.getQuestionnaireDaoInstance().getQuestionnaireById(questionnaireId);
            request.setAttribute("questionnaire", questionnaire);
            List<Question> list = DAOFactory.getQuestionnaireDaoInstance().getQuestionsByQuestionnaireId(questionnaireId);
            request.setAttribute("questions", list);
            request.setAttribute("groupId",groupId);
            request.getRequestDispatcher("/questionnaireStatistic.jsp").forward(request, response);
        }else if("modify".equals(action)){
            Questionnaire questionnaire = DAOFactory.getQuestionnaireDaoInstance().getQuestionnaireById(questionnaireId);
            request.setAttribute("questionnaire", questionnaire);
            List<Question> list = DAOFactory.getQuestionnaireDaoInstance().getQuestionsByQuestionnaireId(questionnaireId);
            request.setAttribute("questions", list);
            request.getRequestDispatcher("/questionnaireModify.jsp").forward(request, response);
        }

    }
}
