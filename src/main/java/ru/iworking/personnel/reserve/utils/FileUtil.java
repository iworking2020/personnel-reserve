package ru.iworking.personnel.reserve.utils;

import java.io.File;

public class FileUtil {

    public static void createProjectDir() {
        File folder = new File(DirProps.PROJECT_DIR);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

}
