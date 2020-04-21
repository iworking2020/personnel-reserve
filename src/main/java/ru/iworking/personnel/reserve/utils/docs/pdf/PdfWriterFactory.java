package ru.iworking.personnel.reserve.utils.docs.pdf;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
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
import ru.iworking.personnel.reserve.utils.PdfUtil;
import ru.iworking.personnel.reserve.utils.docs.DocumentWriterFactory;

import java.io.IOException;
import java.util.Map;

public abstract class PdfWriterFactory implements DocumentWriterFactory {

    private static final Logger logger = LogManager.getLogger(PdfWriterFactory.class);

    public abstract void write(Map<String, Object> props);

    protected Cell createImgCell(byte[] imgBytes) {
        ImageData data = ImageDataFactory.create(imgBytes);
        Image img = new Image(data);
        img.setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell().add(img);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    protected Cell createTextCell(String text) {
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

    protected Cell createTableCell(Table table) {
        Cell cell = new Cell().add(table);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    protected PdfFont getStandartPdfFont() throws IOException {
        byte[] fontContents = IOUtils.toByteArray(PdfUtil.class.getClassLoader().getResourceAsStream("fonts/CenturyGothic.ttf"));
        FontProgram fontProgram = FontProgramFactory.createFont(fontContents);
        return PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
    }

}
