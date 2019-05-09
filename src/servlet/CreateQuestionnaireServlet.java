package servlet;

import entity.*;
import factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/26.
 */
@WebServlet(name = "CreateQuestionnaireServlet",
        urlPatterns = {"/createQuestionnaire"})
public class CreateQuestionnaireServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user=(User)request.getSession().getAttribute("user");
        String queTitle=request.getParameter("queTitle");
        String queDis=request.getParameter("queDis");
        String queDeadline=request.getParameter("queDeadline");
        int questionnaireId=Integer.parseInt(request.getParameter("questionnaireId"));
        if(0!=questionnaireId){
            DAOFactory.getQuestionnaireDaoInstance().deleteQuestionnaire(questionnaireId);
        }
        Questionnaire questionnaire=new Questionnaire();
        questionnaire.setQueTitle(queTitle);
        questionnaire.setQueDis(queDis);
        questionnaire.setTimestamp(Timestamp.valueOf(queDeadline));
        questionnaire.setCreatorId(user.getUserId());
        int questionnaireIdNew=DAOFactory.getQuestionnaireDaoInstance().createQuestionnaire(questionnaire);
        int questionId;
        int i=0;

        while(request.getParameter("question_"+i+"_type")!=null){
            int type=Integer.parseInt(request.getParameter("question_"+i+"_type"));
            Question question=new Question();
            List<Options> options=new ArrayList<>();
            question.setUserId(user.getUserId());
            if(0==type||1==type){
                question.setType(type);
                String content=request.getParameter("question_"+i+"_content");
                question.setContent(content);
                int questionIndex=0;
                while(request.getParameter("question_"+i+"_"+questionIndex)!=null){
                    Options option=new Options();
                    //subString(2);
                    option.setQuestionOption(request.getParameter("question_"+i+"_"+questionIndex));
                    options.add(option);
                    questionIndex++;
                }
            }else if(2==type){
            question.setType(2);
            question.setContent(request.getParameter("question_"+i+"_content"));
            }

            questionId=DAOFactory.getQuestionDaoInstance().addQuestion(question,options);
            QuestionnaireQuestion questionnaireQuestion=new QuestionnaireQuestion();
            questionnaireQuestion.setQuestionnaireId(questionnaireIdNew);
            questionnaireQuestion.setQuestionId(questionId);
            DAOFactory.getQuestionDaoInstance().addQuestionnaireQuestion(questionnaireQuestion);
            i++;
        }
       response.sendRedirect("/consumer?action=productor");
    }
}
