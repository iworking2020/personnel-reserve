package ru.iworking.personnel.reserve.utils;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.dao.*;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.service.api.utils.LocaleUtil;

import java.io.IOException;

public class PdfUtil {

    static final Logger logger = LogManager.getLogger(PdfUtil.class);

    private static volatile PdfUtil instance;

    private ProfFieldDao profFieldDao = ProfFieldDao.getInstance();
    private EducationDao educationDao = EducationDao.getInstance();
    private CurrencyDao currencyDao = CurrencyDao.getInstance();
    private WorkTypeDao workTypeDao = WorkTypeDao.getInstance();
    private PhotoDao photoDao = PhotoDao.getInstance();

    public void createResumePdf(String dest, Resume resume) throws IOException {
        PdfFont pdfFont = getStandartPdfFont();

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 50, 30, 50);

        //Add paragraph to the document
        document.add(new Paragraph("Резюме").setFont(pdfFont).setTextAlignment(TextAlignment.CENTER));

        String FIO = "Ф.И.О.: " + resume.getProfile().getLastName() + " " + resume.getProfile().getFirstName() + " " + resume.getProfile().getMiddleName();
        String number = "тел.: "+resume.getNumberPhone().getNumber();
        String email = "емайл: "+resume.getEmail();
        String profession = "профессия: "+resume.getProfession();
        String wage = resume.getWage() != null ?
                "зарплата: "+resume.getWage().getCountBigDecimal().toString() + " " +
                        currencyDao.find(resume.getWage().getCurrencyId()).getNameToView(LocaleUtil.getDefault()) :
                "зарплата: не указана";
        String profField = resume.getProfFieldId() != null ?
                "профобласть: "+
                        profFieldDao.find(resume.getProfFieldId()).getNameToView(LocaleUtil.getDefault()) :
                "профобласть: не указана";
        String workType = resume.getWorkTypeId() != null ?
                "график: "+
                        workTypeDao.find(resume.getWorkTypeId()).getNameToView(LocaleUtil.getDefault()) :
                "график: не указан";
        String education = resume.getEducationId() != null ?
                "образование: "+
                        educationDao.find(resume.getEducationId()).getNameToView(LocaleUtil.getDefault()) :
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

    private Cell createImgCell(byte[] imgBytes) {
        ImageData data = ImageDataFactory.create(imgBytes);
        Image img = new Image(data);
        img.setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell().add(img);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell createTextCell(String text) {
        Cell cell = new Cell();
        Paragraph p = new Paragraph(text);
        p.setTextAlignment(TextAlignment.LEFT);
        try {
            p.setFont(getStandartPdfFont());
        } catch (IOException e) {
            logger.error(e);
        }
        cell.add(p).setVerticalAlignment(VerticalAlignment.BOTTOM);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell createTableCell(Table table) {
        Cell cell = new Cell().add(table);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private PdfFont getStandartPdfFont() throws IOException {
        byte[] fontContents = IOUtils.toByteArray(PdfUtil.class.getClassLoader().getResourceAsStream("fonts/CenturyGothic.ttf"));
        FontProgram fontProgram = FontProgramFactory.createFont(fontContents);
        return PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
    }

    public static PdfUtil getInstance() {
        PdfUtil localInstance = instance;
        if (localInstance == null) {
            synchronized (PdfUtil.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PdfUtil();
                }
            }
        }
        return localInstance;
    }

}
