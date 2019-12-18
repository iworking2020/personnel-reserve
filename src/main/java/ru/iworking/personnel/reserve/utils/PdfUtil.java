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
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.apache.commons.io.IOUtils;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.service.api.utils.LocaleUtils;

import java.io.IOException;

public class PdfUtil {

    public static void createResumePdf(String dest, Resume resume) throws IOException {
        PdfFont pdfFont = PdfUtil.getStandartPdfFont();


        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 50, 30, 50);

        //Add paragraph to the document
        document.add(new Paragraph("Резюме").setFont(pdfFont).setTextAlignment(TextAlignment.CENTER));

        if (resume.getPhoto() != null) {
            ImageData data = ImageDataFactory.create(resume.getPhoto());
            // Creating an Image object
            Image image = new Image(data);
            image.scaleToFit(200F, 200F);
            document.add(image);
        }

        document.add(new Paragraph("Ф.И.О.: "+
                resume.getLastName() + " " +
                resume.getFirstName() + " " +
                resume.getMiddleName()
        ).setFont(pdfFont));
        document.add(new Paragraph("тел.: "+resume.getNumberPhone()).setFont(pdfFont));
        document.add(new Paragraph("емайл: "+resume.getEmail()).setFont(pdfFont));
        document.add(new Paragraph("профессия: "+resume.getProfession()).setFont(pdfFont));
        if (resume.getWage() != null) {
            document.add(new Paragraph("зарплата: "+resume.getWage().toString() + " " + resume.getCurrency().getNameToView(LocaleUtils.getDefault())).setFont(pdfFont));
        } else {
            document.add(new Paragraph("зарплата: не указана").setFont(pdfFont));
        }
        if (resume.getProfField() != null) {
            document.add(new Paragraph("профобласть: "+resume.getProfField().getNameToView(LocaleUtils.getDefault())).setFont(pdfFont));
        } else {
            document.add(new Paragraph("профобласть: не указана").setFont(pdfFont));
        }
        if (resume.getWorkType() != null) {
            document.add(new Paragraph("график: "+resume.getWorkType().getNameToView(LocaleUtils.getDefault())).setFont(pdfFont));
        } else {
            document.add(new Paragraph("график: не указан").setFont(pdfFont));
        }
        if (resume.getEducation() != null) {
            document.add(new Paragraph("образование: "+resume.getEducation().getNameToView(LocaleUtils.getDefault())).setFont(pdfFont));
        } else {
            document.add(new Paragraph("образование: не указано").setFont(pdfFont));
        }

        //Close document
        document.close();
    }

    private static PdfFont getStandartPdfFont() throws IOException {
        byte[] fontContents = IOUtils.toByteArray(PdfUtil.class.getClassLoader().getResourceAsStream("fonts/CenturyGothic.ttf"));
        FontProgram fontProgram = FontProgramFactory.createFont(fontContents);
        return PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
    }

}
