package com.isdintership.epe.export;

import java.awt.Color;
import java.io.IOException;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;

public class PdfExporter {

    private User user;

    public PdfExporter(User user) {
        this.user = user;
    }

    private void createTable(PdfPTable table) {

    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("First name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Birth date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employment date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone number", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Bio", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Job", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        table.addCell(user.getEmail());
        table.addCell(user.getFirstname());
        table.addCell(user.getLastname());
        table.addCell(String.valueOf(user.getBirthDate()));
        table.addCell(String.valueOf(user.getEmploymentDate()));
        table.addCell(user.getPhoneNumber());
        table.addCell(user.getBio());
        table.addCell(user.getJob().getJobTitle());
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("User's data", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
