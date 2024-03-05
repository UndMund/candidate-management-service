package com.zavadskiy.candidate_management_service.integration.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileUtils {
    public static byte[] readFileAsBytes(String fileName) throws IOException {
        Path path = Paths.get("src/test/resources/files/" + fileName);
        return Files.readAllBytes(path);
    }
}
