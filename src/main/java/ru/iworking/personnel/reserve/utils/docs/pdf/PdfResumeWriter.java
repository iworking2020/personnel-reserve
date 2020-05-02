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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.PhotoDao;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.service.CurrencyService;
import ru.iworking.personnel.reserve.service.EducationService;
import ru.iworking.personnel.reserve.service.ProfFieldService;
import ru.iworking.personnel.reserve.service.WorkTypeService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class PdfResumeWriter extends PdfWriterFactory {

    private static final Logger logger = LogManager.getLogger(PdfResumeWriter.class);

    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;
    private PhotoDao photoDao = PhotoDao.getInstance();

    public enum props {
        PATH, RESUME
    }

    private static volatile PdfResumeWriter instance;

    public static PdfResumeWriter getInstance() {
        PdfResumeWriter localInstance = instance;
        if (localInstance == null) {
            synchronized (PdfResumeWriter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PdfResumeWriter();
                }
            }
        }
        return localInstance;
    }

    private PdfResumeWriter() { }

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
        String wage = resume.getWage() != null ?
                "зарплата: "+resume.getWage().getCountBigDecimal().toString() + " " +
                        currencyService.findById(resume.getWage().getCurrencyId()).getNameView().getName() :
                "зарплата: не указана";
        String profField = resume.getProfFieldId() != null ?
                "профобласть: "+
                        profFieldService.findById(resume.getProfFieldId()).getNameView().getName() :
                "профобласть: не указана";
        String workType = resume.getWorkTypeId() != null ?
                "график: "+
                        workTypeService.findById(resume.getWorkTypeId()).getNameView().getName() :
                "график: не указан";
        String education = resume.getEducationId() != null ?
                "образование: "+
                        educationService.findById(resume.getEducationId()).getNameView().getName() :
                "образование: не указано";

        Table rightBlockTable = new Table(UnitValue.createPercentArray(new float[]{100}));
        rightBlockTable.setBorder(Border.NO_BORDER);
        rightBlockTable.setMargins(0, 15, 0, 15);
        rightBlockTable.addCell(createTextCell(FIO));
        rightBlockTable.addCell(createTextCell(profession));
        rightBlockTable.addCell(createTextCell(profField));
        rightBlockTable.addCell(createTextCell(wage));
        rightBlockTable.addCell(createTextCell(workType));
        rightBlockTable.addCell(createTextCell(education));
        rightBlockTable.addCell(createTextCell(number));
        rightBlockTable.addCell(createTextCell(email));

        Table parentTable = new Table(UnitValue.createPercentArray(new float[]{40, 60}));
        Photo photo = photoDao.find(resume.getPhotoId());
        parentTable.addCell(createImgCell(photo.getImage()));
        parentTable.addCell(createTableCell(rightBlockTable));

        document.add(parentTable);

        document.close();
    }

}
