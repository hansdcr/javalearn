package com.hans.demo.learn.lambda.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileService {
    /**
     *  通过url获取本地文件内容，然后调用抽象接口处理
     * @param url
     * @param fileConsumer
     */
    public void fileHandle(String url, IFileConsumer fileConsumer) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "\n");
        };

        // 抽象方法需要接收String fileContent(这里就是stringBuilder.toString()), 然后由具体的使用着决定拿这个fileContent做什么
        fileConsumer.fileHandler(stringBuilder.toString());
    }
}
