package dao;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.DbConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Created by wzzz on 2019/3/17.
 */
public class test {
    static void tess(){
        Timestamp timestamp=Timestamp.valueOf("2019-4-1 11:14:12");
       DbConnection dbConnection=new DbConnection();
       String sql="INSERT INTO questionnaire(creator_id,que_deadline) VALUES(5,?)";
        try {
            PreparedStatement preparedStatement=dbConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,"2019-4-2 11:11:11");
            preparedStatement.executeUpdate();
//            while(resultSet.next()){
//                System.out.print(resultSet.getInt(1));
//                System.out.print(resultSet.getInt(2));
//                System.out.print(resultSet.getInt(3));
//                System.out.print(resultSet.getString(4));
//                System.out.print(resultSet.getString(5));
//                System.out.print(resultSet.getString(6));
//                System.out.print(resultSet.getString(7));
//                System.out.print(resultSet.getString(8));
//
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        File file=new File("D:/模板.xlsx");
//        String[] titleAndDis=new String[2];
//        if(file.exists())
//            System.out.print("exist");
//        Workbook workbook= null;
//        try {
//            workbook = new XSSFWorkbook(new FileInputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Sheet sheet = workbook.getSheetAt(0);
//        Row row=sheet.getRow(0);
//        titleAndDis[0]=row.getCell(0).getStringCellValue();
//        titleAndDis[1]=row.getCell(1).getStringCellValue();
    }
    public static void main(String[] args){


        String s="abc";
        String a[]=s.split("");
    }
}
