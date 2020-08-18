package com.hans.demo.learn.file;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileDemoTest {
    @Test
    public void createFileTest(){
        String path="/Users/11091752/Desktop/HansDev/javalearn/demo2/src/test/java/com/hans/demo/learn/file/";
        File file = new File(path+"test.txt");
        try {

            boolean mark = file.createNewFile();
            if (mark){
                System.out.println("创建文件成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void printFiles(){
        String path="/Users/11091752/Desktop/HansDev/javalearn/demo2/src/test/java/com/hans/demo/learn";
        this.printFiles(path);
    }

    private void printFiles(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length == 0 ) {
            return;
        }

        for(int i=0; i< files.length; i++){
            if(files[i].isDirectory()){
                printFiles(files[i].toString());
            } else if(files[i].isFile()){
                System.out.println(JSON.toJSONString(files[i]));
            }
        }
    }
}
