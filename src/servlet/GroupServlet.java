package servlet;

import entity.Gro;
import entity.User;
import factory.DAOFactory;
import net.sf.json.JSON;
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
 * Created by wzzz on 2019/3/18.
 */
@WebServlet(name = "GroupServlet",
        urlPatterns = {"/group"})
public class GroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = ((User) request.getSession().getAttribute("user")).getUserId();

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String e = request.getReader().readLine();
        JSONObject jsonObject = JSONObject.fromObject(e);
        String action=jsonObject.getString("action");
        if("join".equals(action)) {
            String creatorName = jsonObject.getString("creatorName");
            String groupName = jsonObject.getString("groupName");
            int groupId = DAOFactory.getGroupDaoInstance().joinGroup(userId, groupName, creatorName);
            if (groupId > -1) {
                writer.print("success");
                writer.print(groupName);
                writer.print("_" + groupId);
            } else
                writer.print("fail");
        }
        else if("create".equals(action)){
            String groupName = jsonObject.getString("groupName");
            Gro gro =new Gro();
            gro.setName(groupName);
            gro.setCreatorId(userId);
            int groupId=DAOFactory.getGroupDaoInstance().createGroup(gro);
            if(groupId>-1){
                writer.print("success");
                writer.print(groupName);
                writer.print("_" + groupId);
            }else
                writer.print("fail");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
