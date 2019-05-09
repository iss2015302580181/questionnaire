package servlet;

import entity.Gro;
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
 * Created by wzzz on 2019/3/17.
 */
@WebServlet(
        name = "ConsumerInitServlet",
        urlPatterns = {"/consumer"}
)
public class ConsumerInitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user= (User) request.getSession().getAttribute("user");
        String action=request.getParameter("action");
        if("consumer".equals(action)){
            List<Gro> list= DAOFactory.getGroupDaoInstance().getAllGroupsByUserId(user.getUserId());
            request.setAttribute("groups",list);
            request.getRequestDispatcher("/consumer.jsp").forward(request,response);
        }
        else if("productor".equals(action)){
            List<Gro> list=DAOFactory.getGroupDaoInstance().getAllGroupsByCreatorId(user.getUserId());
            List<Questionnaire> list1=DAOFactory.getQuestionnaireDaoInstance().getQuestionnairesByCreatorId(user.getUserId());
            request.setAttribute("groups",list);
            request.setAttribute("questionnaires",list1);
            request.getRequestDispatcher("/productor.jsp").forward(request,response);
        }
        else if ("admin".equals(action)){

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
