package servlet;

import entity.GroupQuestionnaire;
import entity.Questionnaire;
import entity.User;
import factory.DAOFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/19.
 */
@WebServlet(name = "QuestionnaireServlet",
        urlPatterns = {"/questionnaire"})
public class QuestionnaireServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = ((User) request.getSession().getAttribute("user")).getUserId();
        String e=request.getReader().readLine();
        JSONObject jsonObject = JSONObject.fromObject(e);
        int groupId=jsonObject.getInt("groupId");
        String action=jsonObject.getString("action");
        List<Questionnaire> list1;
        if("consumer".equals(action)){
            list1= DAOFactory.getQuestionnaireDaoInstance().getQuestionnaireByUserIdAndGroupId(userId,groupId);
            JSONArray jsonArray=JSONArray.fromObject(list1);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter printWriter=response.getWriter();
            printWriter.print(jsonArray);
            printWriter.close();
        }else if("productor".equals(action)){
            list1=DAOFactory.getQuestionnaireDaoInstance().getQuestionnairesByGroupId(groupId);
            JSONArray jsonArray=JSONArray.fromObject(list1);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter printWriter=response.getWriter();
            printWriter.print(jsonArray);
            printWriter.close();
        }else if("publish".equals(action)){
            int questionnaireId=Integer.parseInt(jsonObject.getString("questionnaireId"));
            GroupQuestionnaire groupQuestionnaire=new GroupQuestionnaire();
            groupQuestionnaire.setGroupId(groupId);
            groupQuestionnaire.setQuestionnaireId(questionnaireId);
            PrintWriter printWriter=response.getWriter();
            if(DAOFactory.getQuestionnaireDaoInstance().groupQuestionnaire(groupQuestionnaire)){
                printWriter.write("success");
            }else
                printWriter.write("fail");
            printWriter.close();
        }else if("delete".equals(action)){
            int questionnaireId=Integer.parseInt(jsonObject.getString("questionnaireId"));
            PrintWriter printWriter=response.getWriter();
            if(DAOFactory.getQuestionnaireDaoInstance().deleteQuestionnaire(questionnaireId))
                printWriter.write("success");
            printWriter.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
