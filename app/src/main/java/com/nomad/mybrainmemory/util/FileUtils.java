package com.nomad.mybrainmemory.util;

import java.io.File;

public class FileUtils {

    public static void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}

