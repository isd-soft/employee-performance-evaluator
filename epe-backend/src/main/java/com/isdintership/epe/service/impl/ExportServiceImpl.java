package com.isdintership.epe.service.impl;

import com.isdintership.epe.dto.*;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.exception.AssessmentNotFoundException;
import com.isdintership.epe.exception.UserNotFoundException;
import com.isdintership.epe.repository.AssessmentRepository;
import com.isdintership.epe.repository.UserRepository;
import com.isdintership.epe.service.ExportService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * {@code ExportServiceImpl} is a service class that implements the {@code ExportService} interface.
 *
 *  <p> This class manages all the requests related to generation of files with .xlsx or .pdf .
 *
 *  @author Andrei Chetrean
 *  @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Service
class ExportServiceImpl implements ExportService {

    /**
     * {@code JpaRepository} that handles the access to the user table
     */
    private final UserRepository userRepository;

    /**
     * {@code JpaRepository} that handles the assessment to the user table
     */
    private final AssessmentRepository assessmentRepository;

    /**
     * {@code XSSFSheet} that handles the creation of new page and each row in a .xlsx file.
     */
    private XSSFSheet sheet;

    /**
     * generates a .pdf file with the data of the user whose id was passed as the second parameter
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @param id {@code String} id of the user to be exported to .pdf file
     * @return a generated .pdf file
     * @throws {@code UserNotFoundException} if a user with the provided id was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public void exportUserToPdf(HttpServletResponse response, String id) throws IOException {

        User userDto = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} was not found" , id);
            return new UserNotFoundException("User with " + id + "was not found");
        });
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=test.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph titleParagraph = new Paragraph(userDto.getFirstname() + " " + userDto.getLastname(), fontTitle);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);


        Paragraph emptyRow = new Paragraph("                     ");
        emptyRow.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph emailParagraphKey = new Paragraph("Email:                              ", fontParagraph);
        emailParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph emailParagraphValue = new Paragraph(userDto.getEmail(), fontTitle);
        emailParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        emailParagraphKey.add(emailParagraphValue);

        Paragraph phoneNumberParagraphKey = new Paragraph("Phone number:               ", fontParagraph);
        phoneNumberParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph phoneNumberParagraphValue = new Paragraph(userDto.getPhoneNumber(), fontTitle);
        phoneNumberParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        phoneNumberParagraphKey.add(phoneNumberParagraphValue);

        Paragraph birthDateParagraphKey = new Paragraph("Birth date:                       ", fontParagraph);
        birthDateParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph birthDateParagraphValue = new Paragraph(String.valueOf(userDto.getBirthDate()), fontTitle);
        birthDateParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        birthDateParagraphKey.add(birthDateParagraphValue);

        Paragraph employmentDateParagraphKey = new Paragraph("Employment date:           ", fontParagraph);
        employmentDateParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph employmentDateParagraphValue = new Paragraph(String.valueOf(userDto.getEmploymentDate()), fontTitle);
        employmentDateParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        employmentDateParagraphKey.add(employmentDateParagraphValue);

        Paragraph jobParagraphKey = new Paragraph("Job:                                 ", fontParagraph);
        jobParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph jobParagraphValue = new Paragraph(String.valueOf(userDto.getJob()), fontTitle);
        jobParagraphValue.setAlignment(Paragraph.ALIGN_CENTER);
        jobParagraphKey.add(jobParagraphValue);

        Paragraph bioParagraphKey = new Paragraph("Bio:                                  ", fontParagraph);
        jobParagraphKey.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph bioParagraphValue = new Paragraph(userDto.getBio(), fontTitle);
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

        log.info("File with .pdf extension was successfully generated for user with id = {}", id);

        document.close();
    }

    /**
     * generates a .pdf file with the data of all users stored in a table
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @return a generated .pdf file
     * @since 1.0
     */
    @Override
    @Transactional
    public void exportAllUsersToPdf(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();
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

        log.info("File with .pdf extension was successfully generated for all users");

        document.close();
    }

    /**
     * generates a .pdf file with the data of the assessment whose id was passed as the second parameter
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @param id {@code String} id of the assessment to be exported to .pdf file
     * @return a generated .pdf file
     * @throws {@code AssessmentNotFoundException} if an assessment with the provided id was not found
     * @throws {@code UserNotFoundException} if a user with the id stored in assessment dto was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public void exportAssessmentToPdf(HttpServletResponse response,
                                      String id) throws IOException {

        AssessmentDto assessmentDto = AssessmentDto.fromAssessment(assessmentRepository.findById(id).orElseThrow(() -> {
            log.error("Assessment with id {} was not found", id);
            return new AssessmentNotFoundException("Assessment with " + id + " was not found");
        }));
        UserDto evaluatedUser = UserDto.fromUser(userRepository.findById(assessmentDto.getUserId()).orElseThrow(() -> {
            log.error("User with id {} was not found", assessmentDto.getUserId());
            return new UserNotFoundException("Evaluated user with " + assessmentDto.getUserId() + " was not found");
        }));
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.pdf";
        response.setHeader(headerKey, headerValue);
        response.flushBuffer();

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

        Paragraph titleParagraph = new Paragraph(assessmentDto.getTitle(), font);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph jobParagraph = new Paragraph(evaluatedUser.getJob(), valueFont);
        jobParagraph.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph startDateParagraphTitle = new Paragraph("Start date", titleFont);
        startDateParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph startDateParagraphValue =
                new Paragraph(String.valueOf(LocalDate.from(assessmentDto.getStartDate())),valueFont);
        startDateParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph endDateParagraphTitle = new Paragraph("End date",titleFont);
        endDateParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph endDateParagraphValue =
                new Paragraph(String.valueOf(LocalDate.from(assessmentDto.getEndDate())),valueFont);
        endDateParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph evaluateEmployeeParagraphTitle = new Paragraph("Evaluated employee",titleFont);
        evaluateEmployeeParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatedEmployeeParagraphValue =
                new Paragraph(assessmentDto.getEvaluatedUserFullName(),valueFont);
        evaluatedEmployeeParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatorParagraphTitle = new Paragraph("Evaluator",titleFont);
        evaluatorParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph evaluatorParagraphValue =
                new Paragraph(assessmentDto.getEvaluatorFullName(),valueFont);
        evaluatorParagraphValue.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph descriptionParagraphTitle = new Paragraph("Description",titleFont);
        descriptionParagraphTitle.setAlignment(Paragraph.ALIGN_LEFT);

        Paragraph descriptionParagraphValue =
                new Paragraph(assessmentDto.getDescription(),valueFont);
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

        for (EvaluationGroupDto evaluationGroup : assessmentDto.getEvaluationGroups()) {
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
        finalScoreCell.setPhrase(new Phrase("Total assessmentDto score",titleFont));
        finalScoreTable.addCell(finalScoreCell);
        finalScoreValueCell.setPhrase(new Phrase(String.valueOf(assessmentDto.getOverallScore()),valueFont));
        finalScoreTable.addCell(finalScoreValueCell);

        document.add(finalScoreTable);

        Paragraph personalGoals = new Paragraph("Personal SMART goals", titleFont);
        personalGoals.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(emptyRow);
        document.add(emptyRow);
        document.add(emptyRow);
        document.add(personalGoals);

        for (PersonalGoalDto personalGoal : assessmentDto.getPersonalGoals()) {
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

        for (DepartmentGoalDto departmentGoal : assessmentDto.getDepartmentGoals()) {
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

        for (FeedbackDto feedback : assessmentDto.getFeedbacks()) {
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

        log.info("File with .pdf extension was successfully generated for assessment with id = {}", id);
        document.close();
    }

    /**
     * generates a .xlsx file with the data of the user whose id was passed as the first parameter
     * @param id {@code String} id of the user to be exported to .xlsx file
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @return a generated .xlsxfile
     * @throws {@code UserNotFoundException} if a user with the provided id was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public void export(String id, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);

        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id {} was not found", id);
            return new UserNotFoundException("User with " + id + " was not found");
        });
//        List<User> userList = userRepository.findAll();
//        writeHeaderLine(workbook);
//        writeDataLines(userList, workbook);

        writeData(workbook, user);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        log.info("File with extension with .xlsx was successfully generated for user with id = {}", id);

        outputStream.close();

    }

    /**
     * generates a .xlsx file with the data of all users
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @return a generated .xlsx file
     * @since 1.0
     */
    @Override
    @Transactional
    public void exportAllUsersToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);
        List<User> users = userRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeaderLine(workbook);
        writeDataLines(users, workbook);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        log.info("File with extension with .xlsx was successfully generated for all users");

        outputStream.close();

    }

    /**
     * generates a .xlsx file with the data of the assessment whose id was passed as the second parameter
     * @param response {@code HttpServletResponse} object that will return the generated file
     * @param id {@code String} id of the assessment to be exported to .xlsx file
     * @return a generated .xlsx file
     * @throws {@code AssessmentNotFoundException} if an assessment with the provided id was not found
     * @throws {@code UserNotFoundException} if a user with the id stored in assessment dto was not found
     * @since 1.0
     */
    @Override
    @Transactional
    public void exportAssessmentToExcel(HttpServletResponse response, String id) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        AssessmentDto assessment = AssessmentDto.fromAssessment(assessmentRepository.findById(id).orElseThrow(() -> {
            log.error("Assessment with id {} was not found", id);
            return new AssessmentNotFoundException("Assessment with id " + id + " was not found");
        }));
        UserDto evaluatedUser = UserDto.fromUser(userRepository.findById(assessment.getUserId()).orElseThrow(() -> {
            log.error("User with id {} was not found", assessment.getUserId());
            return new UserNotFoundException("User with " + id + " was not found");
        }));
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users.xlsx";
        response.setHeader(headerKey, headerValue);
        sheet = workbook.createSheet("Users");

        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);
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

        log.info("File with extension with .xlsx was successfully generated for assessment with id = {}", id);

        outputStream.close();
    }

    /**
     * populates the .xlsx file with the data of user who was passed as the second parameter
     * @param workbook {@code XSSFWorkbook} object that will return populated .xlsx file
     * @param user {@code User} to be inserted to .xlsx file
     * @return populated .xlsx file
     * @since 1.0
     */
    private void writeData(XSSFWorkbook workbook, User user) {
        sheet = workbook.createSheet("Users");

        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);
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
        createCell(row,1, user.getBio(),styleValue);

        log.info("XXSFWorkbook was successfully populated");
    }

    /**
     * creates a cell template which will be used for each cell in .xlsx files.
     * @param row {@code Row} object that will set the row of cell in .xlsx file
     * @param columnCount {@code int} object that will set the row of cell in .xlsx file
     * @param style {@code CellStyle} object that will set the style of cell in .xlsx file
     * @return cell template
     * @since 1.0
     */
    private void createCell(org.apache.poi.ss.usermodel.Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);

        log.info("Cell template was successfully accessed");
    }

    /**
     * populates the header line for a .xlsx file for all users' data
     * @param workbook {@code XSSFWorkbook} object that will return populated .xlsx file
     * @return populated .xlsx file
     * @since 1.0
     */
    private void writeHeaderLine(XSSFWorkbook workbook) {
        sheet = workbook.createSheet("Users");

        org.apache.poi.ss.usermodel.Row row = sheet.createRow(0);

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

        log.info("XXSFWorkbook was successfully populated");
    }

    /**
     * populates a .xlsx file if data of all users
     * @param workbook {@code XSSFWorkbook} object that will return populated .xlsx file
     * @param users {@code List<User>} object that provides data of all user to be populated in .xlsx file
     * @return populated .xlsx file
     * @since 1.0
     */
    private void writeDataLines(List<User> users, XSSFWorkbook workbook) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : users) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowCount++);
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
        log.info("XXSFWorkbook was successfully populated");
    }
}
