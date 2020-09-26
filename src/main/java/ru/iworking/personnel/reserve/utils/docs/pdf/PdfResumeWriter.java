package ru.iworking.personnel.reserve.utils.docs.pdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.*;
import ru.iworking.personnel.reserve.service.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfResumeWriter extends PdfWriterFactory {

    private static final Logger logger = LogManager.getLogger(PdfResumeWriter.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

    private final ProfFieldService profFieldService;
    private final EducationService educationService;
    private final CurrencyService currencyService;
    private final WorkTypeService workTypeService;
    private final ImageContainerService imageContainerService;

    public enum props {
        PATH, RESUME
    }

    @Override
    public void write(Map<String, Object> props) {
        String path = (String) props.get(PdfResumeWriter.props.PATH);
        Resume resume = (Resume) props.get(PdfResumeWriter.props.RESUME);

        PdfFont pdfFont = null;
        try {
            pdfFont = getStandartPdfFont();
        } catch (IOException e) {
            logger.error(e);
        }

        //Initialize PDF writer
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(path);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        com.itextpdf.layout.Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 50, 30, 50);

        //Add paragraph to the document
        document.add(new Paragraph("Резюме").setFont(pdfFont).setTextAlignment(TextAlignment.CENTER));

        String FIO = "Ф.И.О.: " + resume.getProfile().getLastName() + " " + resume.getProfile().getFirstName() + " " + resume.getProfile().getMiddleName();
        String number = "тел.: " + resume.getNumberPhone().getNumber();
        String email = "емайл: " + resume.getEmail();
        String profession = "профессия: "+resume.getProfession();
        String wage = "зарплата: %s %s";
        if (resume.getWage() != null) {
            BigDecimal wageCount = resume.getWage().getCountBigDecimal();
            if (resume.getWage().getCurrencyId() != null) {
                Currency currency = currencyService.findById(resume.getWage().getCurrencyId());
                wage = String.format(wage, wageCount.toString(), currency.getNameView().getName());
            } else {
                wage = String.format(wage, wageCount.toString(), "");
            }
        } else {
            wage = String.format(wage, "не указана", "");
        }
        String profField = resume.getProfFieldId() != null ?
                "профобласть: "+
                        profFieldService.findById(resume.getProfFieldId()).getNameView().getName() :
                "профобласть: не указана";
        String workType = resume.getWorkTypeId() != null ?
                "график: "+
                        workTypeService.findById(resume.getWorkTypeId()).getNameView().getName() :
                "график: не указан";
        //TODO
        /*String education = resume.getEducationId() != null ?
                "образование: "+
                        educationService.findById(resume.getEducationId()).getNameView().getName() :
                "образование: не указано";*/

        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{40, 60}));

        ImageContainer imageContainer = imageContainerService.findById(resume.getPhotoId());
        headerTable.addCell(createImgCell(imageContainer.getImage()));

        Table rightBlockTable = new Table(UnitValue.createPercentArray(new float[]{100}));
        rightBlockTable.setBorder(Border.NO_BORDER);
        rightBlockTable.setMargins(0, 15, 0, 15);
        rightBlockTable.addCell(createTextCell(FIO));
        rightBlockTable.addCell(createTextCell(profession));
        rightBlockTable.addCell(createTextCell(profField));
        rightBlockTable.addCell(createTextCell(wage));
        rightBlockTable.addCell(createTextCell(workType));
        //TODO
        //rightBlockTable.addCell(createTextCell(education));
        rightBlockTable.addCell(createTextCell(number));
        rightBlockTable.addCell(createTextCell(email));
        headerTable.addCell(createTableCell(rightBlockTable));

        document.add(headerTable);

        if (resume.getLearningHistoryList() != null || !resume.getLearningHistoryList().isEmpty()) {
            Table learningHistoryListTable = new Table(UnitValue.createPercentArray(new float[]{100}));
            learningHistoryListTable.setBorder(Border.NO_BORDER);
            learningHistoryListTable.setMargins(10, 0, 10, 0);
            for (LearningHistory learningHistory : resume.getLearningHistoryList()) {
                Table learningHistoryTable = new Table(UnitValue.createPercentArray(new float[]{100}));
                learningHistoryTable.setBorder(Border.NO_BORDER);
                learningHistoryTable.setMargins(5, 0, 5, 0);
                String strEducation = "Образование: %s";
                String value = learningHistory.getEducation() != null ?
                        learningHistory.getEducation().getNameView().getName():
                        "не указано";
                learningHistoryTable.addCell(createTextCell(String.format(strEducation, value)));
                learningHistoryTable.addCell(createTextCell(learningHistory.getDescription()));
                learningHistoryListTable.addCell(createTableCell(learningHistoryTable));
            }
            document.add(learningHistoryListTable);
        }

        if (resume.getExperienceHistoryList() != null && !resume.getExperienceHistoryList().isEmpty()) {
            Table experienceHistoryListTable = new Table(UnitValue.createPercentArray(new float[]{100}));
            experienceHistoryListTable.setBorder(Border.NO_BORDER);
            experienceHistoryListTable.setMargins(10, 0, 10, 0);
            for (ExperienceHistory experienceHistory : resume.getExperienceHistoryList()) {
                Table experienceHistoryTable = new Table(UnitValue.createPercentArray(new float[]{100}));
                experienceHistoryTable.setBorder(Border.NO_BORDER);
                experienceHistoryTable.setMargins(5, 0, 5, 0);
                String strExp = "Опыт работы: %s - %s";
                String start = formatter.format(experienceHistory.getDateStart());
                String end = formatter.format(experienceHistory.getDateEnd());
                String value = String.format(strExp, start, end);
                experienceHistoryTable.addCell(createTextCell(value));
                experienceHistoryTable.addCell(createTextCell(experienceHistory.getDescription()));
                experienceHistoryListTable.addCell(createTableCell(experienceHistoryTable));
            }
            document.add(experienceHistoryListTable);
        }

        document.close();
    }

}
