package com.isdintership.epe.service;

import com.isdintership.epe.dto.UserDto;
import com.isdintership.epe.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PDFGeneratorService {


    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("This is a title", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }

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
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph titleParagraph = new Paragraph("All users", fontTitle);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);



        document.close();
    }
}
