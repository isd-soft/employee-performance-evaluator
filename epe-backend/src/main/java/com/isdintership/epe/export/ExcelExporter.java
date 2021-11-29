package com.isdintership.epe.export;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
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


//    private void writeHeaderLine() {
//        sheet = workbook.createSheet("Users");
//
//        Row row = sheet.createRow(0);
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setBold(true);
//        font.setFontHeight(16);
//        style.setFont(font);
//
//        createCell(row, 0, "Email", style);
//        createCell(row, 1, "First name", style);
//        createCell(row, 2, "Last name", style);
//        createCell(row, 3, "Phone number", style);
//        createCell(row, 4, "Bio", style);
//
//
//    }



//    private void writeDataLines() {
//        int rowCount = 1;
//
//        CellStyle style = workbook.createCellStyle();
//        XSSFFont font = workbook.createFont();
//        font.setFontHeight(14);
//        style.setFont(font);
//
//        for (User user : listUsers) {
//            Row row = sheet.createRow(rowCount++);
//            int columnCount = 0;
//
//            createCell(row, columnCount++, user.getEmail(), style);
//            createCell(row, columnCount++, user.getFirstname(), style);
//            createCell(row, columnCount++, user.getLastname(), style);
//            createCell(row, columnCount++, user.getPhoneNumber(), style);
//            createCell(row, columnCount++, user.getBio(), style);
//        }
//    }

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
}
