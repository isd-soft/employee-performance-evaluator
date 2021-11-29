package com.isdintership.epe.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import com.isdintership.epe.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.transaction.annotation.Transactional;

public class PDFGenerator {
    private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public static ByteArrayInputStream userPDFReport (User user) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER, 14,
                    BaseColor.BLACK);
            Paragraph para = new Paragraph("Employee Table", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            // Add PDF Table Header ->
            Stream.of("Email", "First Name", "Last Name", "Birth date", "Employment date", "Phone number",
                      "Job", "Bio").forEach(headerTitle ->
            {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            PdfPCell emailCell = new PdfPCell(new Phrase(String.valueOf(user.getEmail())));
            emailCell.setPaddingLeft(4);
            emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            emailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(emailCell);

            PdfPCell firstNameCell = new PdfPCell(new Phrase(String.valueOf(user.getFirstname())));
            firstNameCell.setPaddingLeft(4);
            firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(firstNameCell);

            PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(user.getLastname())));
            lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            lastNameCell.setPaddingRight(4);
            table.addCell(lastNameCell);

            PdfPCell birthDateCell = new PdfPCell(new Phrase(String.valueOf(user.getBirthDate())));
            birthDateCell.setPaddingLeft(4);
            birthDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            birthDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(birthDateCell);

            PdfPCell employmentDateCell = new PdfPCell(new Phrase(String.valueOf(user.getBirthDate())));
            employmentDateCell.setPaddingLeft(4);
            employmentDateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            employmentDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(employmentDateCell);

            PdfPCell phoneNumberCell = new PdfPCell(new Phrase(String.valueOf(user.getPhoneNumber())));
            phoneNumberCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            phoneNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phoneNumberCell.setPaddingRight(4);
            table.addCell(phoneNumberCell);

            PdfPCell jobCell = new PdfPCell(new Phrase(String.valueOf(user.getJob().getJobTitle())));
            jobCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            jobCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            jobCell.setPaddingRight(4);
            table.addCell(jobCell);

            PdfPCell bioCell = new PdfPCell(new Phrase(String.valueOf(user.getBio())));
            bioCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            bioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            bioCell.setPaddingRight(4);
            table.addCell(bioCell);

            document.add(table);

            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
