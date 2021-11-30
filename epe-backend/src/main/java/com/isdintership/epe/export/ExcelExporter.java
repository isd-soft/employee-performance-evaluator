package com.isdintership.epe.export;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.EvaluationField;
import com.isdintership.epe.entity.User;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Element;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

public class ExcelExporter {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final UserDto user;


    public ExcelExporter(UserDto user) {
        this.user = user;
        workbook = new XSSFWorkbook();
    }

    public ExcelExporter(){
        workbook = new XSSFWorkbook();
        user = new UserDto();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Email", style);
        createCell(row, 1, "First name", style);
        createCell(row, 2, "Last name", style);
        createCell(row, 3, "Birth date", style);
        createCell(row, 4, "Employment date", style);
        createCell(row, 5, "Phone number", style);
        createCell(row, 6, "Role", style);
        createCell(row, 7, "Bio", style);


    }



    private void writeDataLines(List<User> users) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : users) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getFirstname(), style);
            createCell(row, columnCount++, user.getLastname(), style);
            createCell(row, columnCount++, String.valueOf(user.getBirthDate()), style);
            createCell(row, columnCount++, String.valueOf(user.getEmploymentDate()), style);
            createCell(row, columnCount++, user.getPhoneNumber(), style);
            createCell(row, columnCount++, user.getJob().getJobTitle(), style);
            createCell(row, columnCount++, user.getBio(), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeData() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);
        int rowNum = 1;

        CellStyle styleValue = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        styleValue.setFont(font);

        CellStyle styleKey = workbook.createCellStyle();
        font = workbook.createFont();
        font.setFontHeight(14);
        styleKey.setFont(font);

        createCell(row,0, "Email", styleKey);
        createCell(row,1,user.getEmail(),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "First name", styleKey);
        createCell(row,1,user.getFirstname(),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Last name", styleKey);
        createCell(row,1,user.getLastname(),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Phone number", styleKey);
        createCell(row,1,user.getPhoneNumber(),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Birth date", styleKey);
        createCell(row,1,String.valueOf(user.getBirthDate()),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Employment date", styleKey);
        createCell(row,1,String.valueOf(user.getEmploymentDate()),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Job", styleKey);
        createCell(row,1,String.valueOf(user.getJob()),styleValue);
        row = sheet.createRow(rowNum++);

        createCell(row,0, "Bio", styleKey);
        createCell(row,1,user.getBio(),styleValue);


    }

    @Transactional
    public void export(HttpServletResponse response) throws IOException {
//        writeHeaderLine();
//        writeDataLines();

        writeData();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

    @Transactional
    public void exportAllUsersToExcel(HttpServletResponse response,
                                      List<User> users) throws IOException {
        writeHeaderLine();
        writeDataLines(users);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

    @Transactional
    public void exportAssessmentToExcel(HttpServletResponse response,
                                        AssessmentDto assessment,
                                        UserDto evaluatedUser) throws IOException {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);
        int rowNum = 1;

        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont titleFont = workbook.createFont();
        titleStyle.setWrapText(true);
        titleFont.setBold(true);
        titleFont.setFontHeight(16);
        titleStyle.setFont(titleFont);

        CellStyle smartTitleStyle = workbook.createCellStyle();
        XSSFFont smartTitleFont = workbook.createFont();
        smartTitleStyle.setAlignment(HorizontalAlignment.CENTER);
        smartTitleStyle.setWrapText(true);
        smartTitleFont.setBold(true);
        smartTitleFont.setFontHeight(16);
        smartTitleStyle.setFont(smartTitleFont);

        CellStyle valueStyle = workbook.createCellStyle();
        XSSFFont valueFont = workbook.createFont();
        valueStyle.setWrapText(true);
        valueFont.setBold(false);
        valueFont.setFontHeight(16);
        valueStyle.setFont(valueFont);

        CellStyle goalStyle = workbook.createCellStyle();
        XSSFFont goalFont = workbook.createFont();
        goalStyle.setWrapText(true);
//        goalStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        goalFont.setBold(false);
        goalFont.setFontHeight(16);
        goalStyle.setFont(goalFont);



        createCell(row, 3, assessment.getTitle(), titleStyle);

        row = sheet.createRow(rowNum++);

        createCell(row, 3, evaluatedUser.getJob(), valueStyle);

        row = sheet.createRow(rowNum++);

        createCell(row, 1, "Start date", titleStyle);
        createCell(row, 2, String.valueOf(LocalDate.from(assessment.getStartDate())),valueStyle);

        row = sheet.createRow(rowNum++);

        createCell(row, 1, "End date", titleStyle);
        createCell(row, 2, String.valueOf(LocalDate.from(assessment.getEndDate())),valueStyle);

        row = sheet.createRow(rowNum++);

        createCell(row, 1, "Evaluated user", titleStyle);
        createCell(row, 2, assessment.getEvaluatedUserFullName(),valueStyle);

        row = sheet.createRow(rowNum++);

        createCell(row, 1, "Evaluator", titleStyle);
        createCell(row, 2, assessment.getEvaluatorFullName(),valueStyle);


        row = sheet.createRow(rowNum += 3);

        createCell(row, 1, "Evaluation groups", titleStyle);
        createCell(row, 2, "", titleStyle);
        createCell(row, 3, "First score", titleStyle);
        createCell(row, 4, "Second score", titleStyle);

        row = sheet.createRow(rowNum += 2);

        for (EvaluationGroupDto evaluationGroup : assessment.getEvaluationGroups()) {
            createCell(row, 1, evaluationGroup.getTitle(), titleStyle);
            createCell(row, 2, "Comments", titleStyle);
            createCell(row, 3, "", titleStyle);
            createCell(row, 4, "", titleStyle);
            row = sheet.createRow(rowNum += 2);
            for(EvaluationFieldDto evaluationField : evaluationGroup.getEvaluationFields()) {
                createCell(row, 1, evaluationField.getTitle(), valueStyle);
                createCell(row, 2, evaluationField.getComment(), valueStyle);
                createCell(row, 3, evaluationField.getFirstScore(), valueStyle);
                createCell(row, 4, evaluationField.getSecondScore(), valueStyle);
                row = sheet.createRow(rowNum += 2);
            }
            createCell(row, 3, "Total group score", titleStyle);
            createCell(row, 4, String.valueOf(evaluationGroup.getOverallScore()), valueStyle);
            row = sheet.createRow(rowNum += 2);
        }
        createCell(row, 3, "Total assessment score", titleStyle);
        createCell(row, 4, String.valueOf(assessment.getOverallScore()), valueStyle);
        row = sheet.createRow(rowNum += 4);

        createCell(row, 1, "Personal smart goals", titleStyle);
        row = sheet.createRow(rowNum += 2);

        for (PersonalGoalDto personalGoal : assessment.getPersonalGoals()) {
            createCell(row, 1, "S", smartTitleStyle);
            createCell(row, 2, personalGoal.getGoalSPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "M", smartTitleStyle);
            createCell(row, 2, personalGoal.getGoalMPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "A", smartTitleStyle);
            createCell(row, 2, personalGoal.getGoalAPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "R", smartTitleStyle);
            createCell(row, 2, personalGoal.getGoalRPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "T", smartTitleStyle);
            createCell(row, 2, personalGoal.getGoalTPart(), valueStyle);
            row = sheet.createRow(rowNum += 3);
        }

        row = sheet.createRow(rowNum += 2);
        createCell(row, 1, "Department smart goals", titleStyle);
        row = sheet.createRow(rowNum += 2);

        for (DepartmentGoalDto departmentGoal : assessment.getDepartmentGoals()) {
            createCell(row, 1, "S", smartTitleStyle);
            createCell(row, 2, departmentGoal.getGoalSPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "M", smartTitleStyle);
            createCell(row, 2, departmentGoal.getGoalMPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "A", smartTitleStyle);
            createCell(row, 2, departmentGoal.getGoalAPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "R", smartTitleStyle);
            createCell(row, 2, departmentGoal.getGoalRPart(), valueStyle);
            row = sheet.createRow(rowNum += 2);

            createCell(row, 1, "T", smartTitleStyle);
            createCell(row, 2, departmentGoal.getGoalTPart(), valueStyle);
            row = sheet.createRow(rowNum += 3);
        }

        row = sheet.createRow(rowNum += 2);
        createCell(row, 1, "Feedbacks", titleStyle);
        row = sheet.createRow(rowNum += 2);

        createCell(row, 1, "Author", titleStyle);
        createCell(row, 2, "Feedback", titleStyle);
        row = sheet.createRow(rowNum++);

        for (FeedbackDto feedback : assessment.getFeedbacks()) {
            createCell(row, 1, feedback.getAuthorFullName(), valueStyle);
            createCell(row, 2, feedback.getContext(), valueStyle);
            row = sheet.createRow(rowNum++);
        }

        row = sheet.createRow(rowNum += 3);
        createCell(row, 1, "Description", titleStyle);
        row = sheet.createRow(rowNum += 2);
        createCell(row, 1, assessment.getDescription(), valueStyle);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
