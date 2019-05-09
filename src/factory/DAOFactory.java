package factory;

import service.GroupService;
import service.QuestionService;
import service.QuestionnaireService;
import service.UserService;

/**
 * Created by wzzz on 2019/3/17.
 */
public class DAOFactory {
    public static UserService getUserDaoInstance() {
        return new UserService();
    }

    public static GroupService getGroupDaoInstance() {
        return new GroupService();
    }

    public static QuestionnaireService getQuestionnaireDaoInstance() {
        return new QuestionnaireService();
    }

    public static QuestionService getQuestionDaoInstance() {
        return new QuestionService();
    }
}
