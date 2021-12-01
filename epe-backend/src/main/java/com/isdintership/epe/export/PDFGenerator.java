package com.isdintership.epe.export;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.EvaluationField;
import com.isdintership.epe.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Document document = new Document(PageSize.A3);
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

    public void exportAssessmentToPdf(HttpServletResponse response,
                                      AssessmentDto assessment, UserDto evaluatedUser) throws IOException {
        Document document = new Document(PageSize.A2);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph emptyRow = new Paragraph("                     ");
        emptyRow.setAlignment(Paragraph.ALIGN_LEFT);

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLACK);

        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA);
        valueFont.setSize(15);
        valueFont.setColor(Color.BLACK);

        Paragraph titleParagraph = new Paragraph(assessment.getTitle(), font);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
 
        Paragraph jobParagraph = new Paragraph(evaluatedUser.getJob(), valueFont);
        jobParagraph.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph startDateParagraphTitle = new Paragraph("Start date", titleFont);
        startDateParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph startDateParagraphValue =
                new Paragraph(String.valueOf(LocalDate.from(assessment.getStartDate())),valueFont);
        startDateParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph endDateParagraphTitle = new Paragraph("End date",titleFont);
        endDateParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph endDateParagraphValue =
                new Paragraph(String.valueOf(LocalDate.from(assessment.getEndDate())),valueFont);
        endDateParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph evaluateEmployeeParagraphTitle = new Paragraph("Evaluated employee",titleFont);
        evaluateEmployeeParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatedEmployeeParagraphValue = 
                new Paragraph(assessment.getEvaluatedUserFullName(),valueFont);
        evaluatedEmployeeParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatorParagraphTitle = new Paragraph("Evaluator",titleFont);
        evaluatorParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatorParagraphValue =
                new Paragraph(assessment.getEvaluatorFullName(),valueFont);
        evaluatorParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph descriptionParagraphTitle = new Paragraph("Description",titleFont);
        descriptionParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph descriptionParagraphValue =
                new Paragraph(assessment.getDescription(),valueFont);
        descriptionParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        PdfPTable titleTable = new PdfPTable(4);
        titleTable.setWidthPercentage(100f);
        titleTable.setWidths(new float[] {2.5f,3.5f, 2.0f, 2.0f});
        titleTable.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setBorderWidth(2f);

        cell.setPhrase(new Phrase("Evaluation groups",titleFont));
        titleTable.addCell(cell);

        cell.setPhrase(new Phrase("",titleFont));
        titleTable.addCell(cell);

        cell.setPhrase(new Phrase("First score",titleFont));
        titleTable.addCell(cell);

        cell.setPhrase(new Phrase("Second score",titleFont));
        titleTable.addCell(cell);


        document.add(titleParagraph);
        document.add(jobParagraph);
        document.add(startDateParagraphTitle);
        document.add(startDateParagraphValue);
        document.add(emptyRow);
        document.add(endDateParagraphTitle);
        document.add(endDateParagraphValue);
        document.add(emptyRow);
        document.add(evaluateEmployeeParagraphTitle);
        document.add(evaluatedEmployeeParagraphValue);
        document.add(emptyRow);
        document.add(evaluatorParagraphTitle);
        document.add(evaluatorParagraphValue);
        document.add(emptyRow);
        document.add(descriptionParagraphTitle);
        document.add(descriptionParagraphValue);
        document.add(emptyRow);
        document.add(emptyRow);

        document.add(titleTable);

        for (EvaluationGroupDto evaluationGroup : assessment.getEvaluationGroups()) {
            PdfPTable evaluationFields = new PdfPTable(4);
            evaluationFields.setWidthPercentage(100f);
            evaluationFields.setWidths(new float[] {2.5f,3.5f,2.0f,2.0f});
            evaluationFields.setSpacingBefore(10);

            PdfPCell evaluationCell = new PdfPCell();
            evaluationCell.setPadding(35);
            evaluationCell.setBorderWidth(2f);

            evaluationCell.setPhrase(new Phrase(evaluationGroup.getTitle(),titleFont));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase("Comments",titleFont));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase(""));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase(""));
            evaluationFields.addCell(evaluationCell);

            for (EvaluationFieldDto evaluationField : evaluationGroup.getEvaluationFields()) {
                evaluationCell.setPhrase(new Phrase(evaluationField.getTitle(),valueFont));
                evaluationFields.addCell(evaluationCell);
                evaluationCell.setPhrase(new Phrase(evaluationField.getComment(),valueFont));
                evaluationFields.addCell(evaluationCell);
                evaluationCell.setPhrase(new Phrase(String.valueOf(evaluationField.getFirstScore()),valueFont));
                evaluationFields.addCell(evaluationCell);
                evaluationCell.setPhrase(new Phrase(String.valueOf(evaluationField.getSecondScore()),valueFont));
                evaluationFields.addCell(evaluationCell);
            }

            evaluationCell.setPhrase(new Phrase(""));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase(""));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase("Total group score",titleFont));
            evaluationFields.addCell(evaluationCell);
            evaluationCell.setPhrase(new Phrase(String.valueOf(evaluationGroup.getOverallScore()),valueFont));
            evaluationFields.addCell(evaluationCell);

            document.add(evaluationFields);
        }

        PdfPTable finalScoreTable = new PdfPTable(4);
        finalScoreTable.setWidthPercentage(100f);
        finalScoreTable.setWidths(new float[] {2.5f,3.5f, 2.0f, 2.0f});
        finalScoreTable.setSpacingBefore(10);

        PdfPCell finalScoreCell = new PdfPCell();
        finalScoreCell.setPadding(5);
        finalScoreCell.setBorderWidth(2f);

        PdfPCell finalScoreValueCell = new PdfPCell();
        finalScoreValueCell.setPadding(5);
        finalScoreValueCell.setPaddingLeft(35);
        finalScoreValueCell.setBorderWidth(2f);

        finalScoreCell.setPhrase(new Phrase());
        finalScoreTable.addCell(finalScoreCell);
        finalScoreCell.setPhrase(new Phrase());
        finalScoreTable.addCell(finalScoreCell);
        finalScoreCell.setPhrase(new Phrase("Total assessment score",titleFont));
        finalScoreTable.addCell(finalScoreCell);
        finalScoreValueCell.setPhrase(new Phrase(String.valueOf(assessment.getOverallScore()),valueFont));
        finalScoreTable.addCell(finalScoreValueCell);

        document.add(finalScoreTable);

        Paragraph personalGoals = new Paragraph("Personal SMART goals", titleFont);
        personalGoals.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(emptyRow);
        document.add(emptyRow);
        document.add(emptyRow);
        document.add(personalGoals);

        for (PersonalGoalDto personalGoal : assessment.getPersonalGoals()) {
            PdfPTable personalGoalsTable = new PdfPTable(2);
            personalGoalsTable.setWidthPercentage(100f);
            personalGoalsTable.setWidths(new float[]{2.0f, 8.0f});
            personalGoalsTable.setSpacingBefore(10);

            PdfPCell personalGoalCell = new PdfPCell();
            personalGoalCell.setPadding(5);
            personalGoalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            personalGoalCell.setFixedHeight(50);
            personalGoalCell.setHorizontalAlignment(1);
            personalGoalCell.setBorderWidth(2f);

            PdfPCell emptyRowCell = new PdfPCell();
            emptyRowCell.setPaddingTop(5);
            emptyRowCell.setBorderWidth(0f);
            emptyRowCell.setFixedHeight(15);
            emptyRowCell.setBackgroundColor(Color.WHITE);
            emptyRowCell.setPhrase(new Phrase(""));

            personalGoalCell.setPhrase(new Phrase("S", titleFont));
            personalGoalCell.setBackgroundColor(Color.WHITE);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalCell.setPhrase(new Phrase(personalGoal.getGoalSPart(), titleFont));
            personalGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalsTable.addCell(emptyRowCell);
            personalGoalsTable.addCell(emptyRowCell);

            personalGoalCell.setPhrase(new Phrase("M", titleFont));
            personalGoalCell.setBackgroundColor(Color.WHITE);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalCell.setPhrase(new Phrase(personalGoal.getGoalMPart(), titleFont));
            personalGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalsTable.addCell(emptyRowCell);
            personalGoalsTable.addCell(emptyRowCell);


            personalGoalCell.setPhrase(new Phrase("A", titleFont));
            personalGoalCell.setBackgroundColor(Color.WHITE);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalCell.setPhrase(new Phrase(personalGoal.getGoalAPart(), titleFont));
            personalGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalsTable.addCell(emptyRowCell);
            personalGoalsTable.addCell(emptyRowCell);


            personalGoalCell.setPhrase(new Phrase("R", titleFont));
            personalGoalCell.setBackgroundColor(Color.WHITE);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalCell.setPhrase(new Phrase(personalGoal.getGoalRPart(), titleFont));
            personalGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalsTable.addCell(emptyRowCell);
            personalGoalsTable.addCell(emptyRowCell);

            personalGoalCell.setPhrase(new Phrase("T", titleFont));
            personalGoalCell.setBackgroundColor(Color.WHITE);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalCell.setPhrase(new Phrase(personalGoal.getGoalTPart(), titleFont));
            personalGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            personalGoalsTable.addCell(personalGoalCell);

            personalGoalsTable.addCell(emptyRowCell);
            personalGoalsTable.addCell(emptyRowCell);

            document.add(personalGoalsTable);
        }

        Paragraph departmentGoals = new Paragraph("Department SMART goals", titleFont);
        departmentGoals.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(emptyRow);
        document.add(emptyRow);
        document.add(emptyRow);
        document.add(departmentGoals);

        for (DepartmentGoalDto departmentGoal : assessment.getDepartmentGoals()) {
            PdfPTable departmentGoalsTable = new PdfPTable(2);
            departmentGoalsTable.setWidthPercentage(100f);
            departmentGoalsTable.setWidths(new float[]{2.0f, 8.0f});
            departmentGoalsTable.setSpacingBefore(10);

            PdfPCell departmentGoalCell = new PdfPCell();
            departmentGoalCell.setPadding(5);
            departmentGoalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            departmentGoalCell.setFixedHeight(50);
            departmentGoalCell.setHorizontalAlignment(1);
            departmentGoalCell.setBorderWidth(2f);

            PdfPCell emptyRowCell = new PdfPCell();
            emptyRowCell.setPaddingTop(5);
            emptyRowCell.setBorderWidth(0f);
            emptyRowCell.setFixedHeight(15);
            emptyRowCell.setBackgroundColor(Color.WHITE);
            emptyRowCell.setPhrase(new Phrase(""));

            departmentGoalCell.setPhrase(new Phrase("S", titleFont));
            departmentGoalCell.setBackgroundColor(Color.WHITE);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalCell.setPhrase(new Phrase(departmentGoal.getGoalSPart(), titleFont));
            departmentGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalsTable.addCell(emptyRowCell);
            departmentGoalsTable.addCell(emptyRowCell);

            departmentGoalCell.setPhrase(new Phrase("M", titleFont));
            departmentGoalCell.setBackgroundColor(Color.WHITE);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalCell.setPhrase(new Phrase(departmentGoal.getGoalMPart(), titleFont));
            departmentGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalsTable.addCell(emptyRowCell);
            departmentGoalsTable.addCell(emptyRowCell);


            departmentGoalCell.setPhrase(new Phrase("A", titleFont));
            departmentGoalCell.setBackgroundColor(Color.WHITE);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalCell.setPhrase(new Phrase(departmentGoal.getGoalAPart(), titleFont));
            departmentGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalsTable.addCell(emptyRowCell);
            departmentGoalsTable.addCell(emptyRowCell);


            departmentGoalCell.setPhrase(new Phrase("R", titleFont));
            departmentGoalCell.setBackgroundColor(Color.WHITE);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalCell.setPhrase(new Phrase(departmentGoal.getGoalRPart(), titleFont));
            departmentGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalsTable.addCell(emptyRowCell);
            departmentGoalsTable.addCell(emptyRowCell);

            departmentGoalCell.setPhrase(new Phrase("T", titleFont));
            departmentGoalCell.setBackgroundColor(Color.WHITE);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalCell.setPhrase(new Phrase(departmentGoal.getGoalTPart(), titleFont));
            departmentGoalCell.setBackgroundColor(Color.LIGHT_GRAY);
            departmentGoalsTable.addCell(departmentGoalCell);

            departmentGoalsTable.addCell(emptyRowCell);
            departmentGoalsTable.addCell(emptyRowCell);

            document.add(departmentGoalsTable);
        }

        Paragraph feedbacks = new Paragraph("Feedbacks", titleFont);
        departmentGoals.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(emptyRow);
        document.add(emptyRow);
        document.add(emptyRow);
        document.add(feedbacks);

        PdfPTable feedbacksTable = new PdfPTable(2);
        feedbacksTable.setWidthPercentage(100f);
        feedbacksTable.setWidths(new float[]{4.0f, 6.0f});
        feedbacksTable.setSpacingBefore(10);

        PdfPCell feedbackTitleCell = new PdfPCell();
        feedbackTitleCell.setPadding(5);
        feedbackTitleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        feedbackTitleCell.setFixedHeight(80);
        feedbackTitleCell.setHorizontalAlignment(1);
        feedbackTitleCell.setBorderWidth(2f);
        feedbackTitleCell.setBackgroundColor(Color.WHITE);

        feedbackTitleCell.setPhrase(new Phrase("Author",titleFont));
        feedbacksTable.addCell(feedbackTitleCell);
        feedbackTitleCell.setPhrase(new Phrase("Feedback",titleFont));
        feedbacksTable.addCell(feedbackTitleCell);

        for (FeedbackDto feedback : assessment.getFeedbacks()) {
            PdfPCell feedbackValueCell = new PdfPCell();
            feedbackValueCell.setPadding(5);
            feedbackValueCell.setPaddingLeft(35);
            feedbackValueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            feedbackValueCell.setFixedHeight(80);
            feedbackValueCell.setHorizontalAlignment(0);
            feedbackValueCell.setBorderWidth(2f);
            feedbackValueCell.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell emptyRowCell = new PdfPCell();
            emptyRowCell.setPaddingTop(5);
            emptyRowCell.setBorderWidth(0f);
            emptyRowCell.setFixedHeight(15);
            emptyRowCell.setBackgroundColor(Color.WHITE);
            emptyRowCell.setPhrase(new Phrase(""));

            feedbackTitleCell.setPhrase(new Phrase(feedback.getAuthorFullName(),titleFont));
            feedbacksTable.addCell(feedbackTitleCell);

            feedbackValueCell.setPhrase(new Phrase(feedback.getContext(),valueFont));
            feedbacksTable.addCell(feedbackValueCell);

            feedbacksTable.addCell(emptyRowCell);
            feedbacksTable.addCell(emptyRowCell);
        }

        document.add(feedbacksTable);

        document.close();
    }
}
