package ru.iworking.personnel.reserve.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.regex.Pattern;

@Component
public class ImageUtil {

    private final static Logger logger = LogManager.getLogger(ImageUtil.class);

    @Value("${image.util.default.image.format}")
    private String defaultImageFormat;

    @Value("${image.util.default.resume.image.file}")
    private String defaultResumeImageFile;
    @Value("${image.util.default.company.image.file}")
    private String defaultCompanyImageFile;
    @Value("${image.util.default.vacancy.image.file}")
    private String defaultVacancyImageFile;

    public byte[] scaleToSize(byte[] img, Integer width, Integer height) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(img);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.Builder builder = Thumbnails.of(inputStream);
            if (width != null) builder.width(width);
            if (height != null) builder.height(height);
            builder.toOutputStream(outputStream);
        } catch (IOException e) {
            logger.debug("Error parse image...", e);
        }
        return outputStream.toByteArray();
    }

    public byte[] convertBase64ToImage(String base64Text) {
        try {
            if (this.isValidImageBase64(base64Text)) {
                String[] parts = base64Text.split(",");
                return Base64.getDecoder().decode(parts[1]);
            } else {
                logger.error("Data base64 not valid...");
                return null;
            }
        } catch (Exception ex) {
            logger.error("Error from decode image-base64", ex);
            return null;
        }
    }

    public String convertImageToBase64(byte[] image) {
        return this.convertImageToBase64(image, null);
    }

    public String convertImageToBase64(byte[] image, String imageFormat) {
        if (Strings.isBlank(imageFormat)) imageFormat = defaultImageFormat;
        String base64 = Base64.getEncoder().encodeToString(image);
        return String.format("data:image/%s;base64, %s", imageFormat, base64);
    }

    public Boolean isValidImageBase64(String text) {
        Pattern pattern = Pattern.compile("^(data:image/(.*);)(.*)base64(.*)(,)(.*)$", Pattern.DOTALL);
        return text != null && !text.isEmpty() && text.length() > 0 && pattern.matcher(text).matches();
    }

    public byte[] getDefaultResumeImage() {
        byte[] bytes = new byte[0];
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(defaultResumeImageFile)) {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error(e);
        }
        return bytes;
    }

    public byte[] getDefaultCompanyImage() {
        byte[] bytes = new byte[0];
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(defaultCompanyImageFile)) {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error(e);
        }
        return bytes;
    }

    public byte[] getDefaultVacancyImage() {
        byte[] bytes = new byte[0];
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(defaultVacancyImageFile)) {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error(e);
        }
        return bytes;
    }

}
