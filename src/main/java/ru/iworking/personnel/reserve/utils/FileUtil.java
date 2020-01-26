package ru.iworking.personnel.reserve.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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

}
