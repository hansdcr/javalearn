package com.hans.demo.learn.lambda.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileServiceTest {
    @Test
    public void fileHandle() throws IOException {
        FileService fileService = new FileService();
        String path = "/Users/11091752/Desktop/HansDev/javalearn/demo2/src/test/java/com/hans/demo/lambda/file/FileServiceTest.java";
        fileService.fileHandle(path, fileContent -> System.out.println(fileContent));
    }
}
