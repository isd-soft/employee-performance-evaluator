package com.isdintership.epe.export;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PDFGenerator {


    public void exportUserToPdf(HttpServletResponse response, UserDto user) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph titleParagraph = new Paragraph(user.getFirstname() + " " + user.getLastname(), fontTitle);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);


        Paragraph emptyRow = new Paragraph("                     ");
        emptyRow.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph emailParagraphKey = new Paragraph("Email:                              ", fontParagraph);
        emailParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph emailParagraphValue = new Paragraph(user.getEmail(), fontTitle);
        emailParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        emailParagraphKey.add(emailParagraphValue);

        Paragraph phoneNumberParagraphKey = new Paragraph("Phone number:               ", fontParagraph);
        phoneNumberParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph phoneNumberParagraphValue = new Paragraph(user.getPhoneNumber(), fontTitle);
        phoneNumberParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        phoneNumberParagraphKey.add(phoneNumberParagraphValue);

        Paragraph birthDateParagraphKey = new Paragraph("Birth date:                       ", fontParagraph);
        birthDateParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph birthDateParagraphValue = new Paragraph(String.valueOf(user.getBirthDate()), fontTitle);
        birthDateParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        birthDateParagraphKey.add(birthDateParagraphValue);

        Paragraph employmentDateParagraphKey = new Paragraph("Employment date:           ", fontParagraph);
        employmentDateParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph employmentDateParagraphValue = new Paragraph(String.valueOf(user.getEmploymentDate()), fontTitle);
        employmentDateParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        employmentDateParagraphKey.add(employmentDateParagraphValue);

        Paragraph jobParagraphKey = new Paragraph("Job:                                 ", fontParagraph);
        jobParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph jobParagraphValue = new Paragraph(String.valueOf(user.getJob()), fontTitle);
        jobParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        jobParagraphKey.add(jobParagraphValue);

        Paragraph bioParagraphKey = new Paragraph("Bio:                                  ", fontParagraph);
        jobParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph bioParagraphValue = new Paragraph(user.getBio(), fontTitle);
        bioParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        bioParagraphKey.add(bioParagraphValue);



        document.add(titleParagraph);
        document.add(emptyRow);
        document.add(emptyRow);
        document.add(emailParagraphKey);
        document.add(emptyRow);
        document.add(phoneNumberParagraphKey);
        document.add(emptyRow);
        document.add(birthDateParagraphKey);
        document.add(emptyRow);
        document.add(employmentDateParagraphKey);
        document.add(emptyRow);
        document.add(jobParagraphKey);
        document.add(emptyRow);
        document.add(bioParagraphKey);
        document.add(emptyRow);
        document.close();
    }

    public void exportAllUsersToPdf(HttpServletResponse response, List<User> users) throws IOException {
        Document document = new Document(PageSize.A2);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 1.5f, 1.5f, 1.5f, 3.5f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA);
        titleFont.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("First name", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Last name", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phone number", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Birth date", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employment date", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Job", titleFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Bio", titleFont));
        table.addCell(cell);

        for (User user : users) {
            table.addCell(user.getFirstname());
            table.addCell(user.getLastname());
            table.addCell(user.getEmail());
            table.addCell(user.getPhoneNumber());
            table.addCell(String.valueOf(user.getBirthDate()));
            table.addCell(String.valueOf(user.getEmploymentDate()));
            table.addCell(user.getJob().getJobTitle());
            table.addCell(user.getBio());
        }

        document.add(table);

        document.close();
    }
}
