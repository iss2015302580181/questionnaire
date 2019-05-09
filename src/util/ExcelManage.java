package util;

import entity.Options;
import entity.Question;
import entity.User;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/22.
 */
public class ExcelManage {
    public List<Question> manageExcel(InputStream inputStream, String[] titleAndDis) {
        List<Question> list = new ArrayList<>();
        int totalRows;
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            totalRows = sheet.getPhysicalNumberOfRows();
            titleAndDis[0] = sheet.getRow(0).getCell(0).getStringCellValue();
            titleAndDis[1] = sheet.getRow(0).getCell(1).getStringCellValue();
            titleAndDis[2] = sheet.getRow(0).getCell(2).getStringCellValue();
            for (int i = 1; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;
                int cols = row.getPhysicalNumberOfCells();
                Question question = new Question();
                String quesType = row.getCell(0).getStringCellValue();
                int type = 3;
                switch (quesType) {
                    case "单选":
                    case "单选题":
                        type = 0;
                        break;
                    case "多选":
                    case "多选题":
                        type = 1;
                        break;
                    case "问答":
                    case "问答题":
                        type = 2;
                }
                question.setType(type);
                question.setContent(row.getCell(1).getStringCellValue());
                List<Options> options = new ArrayList<>();
                int optionIndex = 2;
                while (optionIndex < cols) {
                    Options option = new Options();
                    option.setQuestionOption((char) (65 - 2 + optionIndex) + "、" + row.getCell(optionIndex).getStringCellValue());
                    optionIndex++;
                    options.add(option);
                    question.setOptions(options);
                }
                list.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> userGroupExcel(InputStream inputStream) throws IOException {
        List<User> list = new ArrayList<>();
        Workbook workbook;
        workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                continue;
            User user = new User();
            String userName = row.getCell(0).getStringCellValue();
            if (!userName.matches("^([\\u4e00-\\u9fa5]|\\w){6,8}$"))
                continue;
            String userPsd = row.getCell(1).getStringCellValue();
            if (!userPsd.matches("^\\w{6,8}$"))
                continue;
            user.setUserName(userName);
            user.setUserPsd(userPsd);
            user.setType(2);
            list.add(user);
        }
        return list;
    }
}
