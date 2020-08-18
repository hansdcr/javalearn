package com.hans.demo.learn.lambda.file;

@FunctionalInterface
public interface IFileConsumer {
    void fileHandler(String fileContent);
}
