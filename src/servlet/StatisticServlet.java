package servlet;

import entity.Question;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzzz on 2019/3/27.
 */
@WebServlet(name = "StatisticServlet",
        urlPatterns = {"/statistic"})
public class StatisticServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String e = request.getReader().readLine();
        JSONObject jsonObject = JSONObject.fromObject(e);
        int groupId = jsonObject.getInt("groupId");
        int questionnaireId = jsonObject.getInt("questionnaireId");
        int type = jsonObject.getInt("type");
        int questionId = jsonObject.getInt("questionId");
        JSONArray jsonArray = null;
        if (type == 0 || type == 1) {
            Map<String, Integer> map = DAOFactory.getQuestionDaoInstance().getSingleAndMultiStatistic(groupId, questionnaireId, questionId);
            jsonArray = JSONArray.fromObject(map);
        } else if (type == 2) {
            List list = DAOFactory.getQuestionDaoInstance().getfillStatistic(groupId, questionnaireId, questionId);
            jsonArray = JSONArray.fromObject(list);
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonArray);
        printWriter.close();
    }
}
