package servlet;

import entity.Question;
import entity.Questionnaire;
import entity.User;
import factory.DAOFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.ExcelManage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wzzz on 2019/3/22.
 */
@WebServlet(name = "UploadServlet",
        urlPatterns = {"/upload"})
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        InputStream inputStream = null;
        String action = null;
        int groupId = 0;
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    inputStream = item.getInputStream();
                } else {
                    if (item.getFieldName().equals("action"))
                        action = item.getString();
                    else if (item.getFieldName().equals("groupId"))
                        groupId = Integer.parseInt(item.getString());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        ExcelManage excelManage = new ExcelManage();
        if ("questionnaire".equals(action)) {
            Questionnaire questionnaire = new Questionnaire();
//        储存问卷名、问卷简介、截止日期
            String[] titleAndDis = new String[3];
            List<Question> list = excelManage.manageExcel(inputStream, titleAndDis);
            questionnaire.setQueTitle(titleAndDis[0]);
            questionnaire.setQueDis(titleAndDis[1]);
            questionnaire.setTimestamp(Timestamp.valueOf(titleAndDis[2]));
            request.setAttribute("questionnaire", questionnaire);
            request.setAttribute("questions", list);
            request.getRequestDispatcher("/questionnaireModify.jsp").forward(request, response);
        } else if ("userGroup".equals(action)) {
            int success = 0, fail = 0;
            int creatorId = ((User) request.getSession().getAttribute("user")).getUserId();
            List<User> list = excelManage.userGroupExcel(inputStream);
            for (int i = 0; i < list.size(); i++) {
                User user = list.get(i);
                int userId = DAOFactory.getUserDaoInstance().addUser(user);
                if (userId > -1) {
                    if (DAOFactory.getGroupDaoInstance().joinGroupByAdmin(userId, groupId))
                        success++;
                }
            }
//            request.setAttribute("numOfJoinedUser", success);
            response.sendRedirect("/consumer?action=productor");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
