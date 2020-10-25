package ru.iworking.personnel.reserve.utils.docs.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.component.FreeMarkerComponent;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.utils.FileUtil;
import ru.iworking.personnel.reserve.utils.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PdfResumeWriter extends PdfWriterFactory {

    private static final Logger logger = LogManager.getLogger(PdfResumeWriter.class);

    @Value("${spring.freemarker.template.folder.fonts}")
    private String folderFonts;

    private final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.YYYY");

    private final ImageUtil imageUtil;

    private final FreeMarkerComponent freeMarkerComponent;

    public enum props {
        PATH, RESUME
    }

    @Override
    public void write(Map<String, Object> props) {
        String path = (String) props.get(PdfResumeWriter.props.PATH);
        Resume resume = (Resume) props.get(PdfResumeWriter.props.RESUME);

        Map<String, Object> propsFtl = new HashMap<>();
        propsFtl.put("resume", resume);
        propsFtl.put("imageUtil", imageUtil);
        propsFtl.put("dateTimeFormatter", formatter);

        String html = freeMarkerComponent.createHtml("resume.ftl", propsFtl);

        try (FileOutputStream fos = new FileOutputStream(path)) {
            ConverterProperties properties = new ConverterProperties();

            FontProvider fontProvider = new DefaultFontProvider(false, false, false);

            try {
                File folder = FileUtil.getProjectFolder(folderFonts);
                File[] listFiles = folder.listFiles((dir, name) -> name.endsWith(".ttf"));

                Stream.of(listFiles).parallel()
                        .map(File::toURI)
                        .filter(Objects::nonNull)
                        .map(uri -> {
                            try {
                                return uri.toURL();
                            } catch (MalformedURLException e) {
                                logger.error(e);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .forEach(url -> {
                            try {
                                FontProgram fontProgram = FontProgramFactory.createFont(url.toString());
                                fontProvider.addFont(fontProgram);
                            } catch (IOException e) {
                                logger.error(e);
                            }
                        });
            } catch (NotFoundException e) {
                logger.error(e);
            }

            properties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(html, fos, properties);
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
