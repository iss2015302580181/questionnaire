package servlet;

import entity.User;
import factory.DAOFactory;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wzzz on 2019/3/17.
 */
@WebServlet(
        urlPatterns={"/login"},
        name = "LoginRegServlet"
)
public class LoginRegServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action=req.getParameter("action");
        String path="/login.jsp";

        if(("login").equals(action)) {
            //后端必须验证
            String userName = req.getParameter("userName");
            String userPsd = req.getParameter("userPsd");
            if(DAOFactory.getUserDaoInstance().validateUser(userName,userPsd)!=null){
                User user=DAOFactory.getUserDaoInstance().validateUser(userName,userPsd);
                req.getSession().setAttribute("user",user);
                //1：管理员  2：普通用户
                switch (user.getType()){
//                    case 0:
//                        path="/consumer?action=admin";
//                        break;
                    case 1:
                        path="/consumer?action=productor";
                        break;
                    case 2:
                        path="/consumer?action=consumer";
                }
            }else{
                req.setAttribute("status","loginFail");
            }
            req.getRequestDispatcher(path).forward(req,resp);
        }else if(("register").equals(action)){
            String userName = req.getParameter("userName");
            String userPsd = req.getParameter("userPsd");
            User user=new User();
            user.setUserName(userName);
            user.setType(2);
            user.setUserPsd(userPsd);
            if(DAOFactory.getUserDaoInstance().addUser(user)>-1){
                user=DAOFactory.getUserDaoInstance().getUserByName(userName);
                req.getSession().setAttribute("user",user);
                path="/consumer.jsp";
            }
            req.getRequestDispatcher(path).forward(req,resp);
        }else if(("logout").equals(action)){
            req.getSession().invalidate();
            req.getRequestDispatcher(path).forward(req,resp);
        }else{
            PrintWriter writer = resp.getWriter();
            String e = req.getReader().readLine();
            JSONObject jsonObject = JSONObject.fromObject(e);
            action=jsonObject.getString("action");
            String userName=jsonObject.getString("userName");
            if(DAOFactory.getUserDaoInstance().isContainUser(userName))
                writer.print("repeat");
            else
                writer.print("success");
            writer.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }
}
