package ru.iworking.personnel.reserve.utils;

import javassist.NotFoundException;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtil {

    public static String readFileAsString(String fileName, String encoding) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)), encoding);
        return data;
    }

    public static void createProjectDir() {
        File folder = new File(DirProps.PROJECT_DIR);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static File getProjectFolder(String path) throws NotFoundException {
        final File folder = new File(path);
        if (Objects.isNull(folder)) throw new NotFoundException(String.format("path folder %s not found", path));
        return folder;
    }

    public static File getResourceFolder(String path) throws NotFoundException, URISyntaxException {
        final File folder = new File(Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(path)).toURI());
        if (Objects.isNull(folder)) throw new NotFoundException(String.format("path folder %s not found", path));
        return folder;
    }

}
