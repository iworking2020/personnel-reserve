package ru.iworking.personnel.reserve.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    private final static Logger logger = LogManager.getLogger(ImageUtil.class);

    public static byte[] scaleToSize(byte[] img, Integer width, Integer height) {
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

}
