package demo02;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileCopy {
    private File source;
    private File target;
    private int COPY_THREAD_NUM = 5;
    private ExecutorService executor = Executors.newFixedThreadPool(COPY_THREAD_NUM);
    private String FILE_TEMP_SUFFIX = ".temp";
    private String TEMP_PAPTH = "/Users/11091752/Desktop/storage/data/tmp";

    public FileCopy(){}

    public FileCopy(File source, File target){
        this.source = source;
        this.target = target;
    }

    private void splitCopyTask(){
        /**
         *  假设文件44个字节，被5个线程分片处理，那么
         *  文件大小 fileSize = 44
         *  分片大小 size = 44 / 5 = 8
         *  那么前4个线程。每个线程处理8字节，第5个线程处理12个字节： 44 -（44 / 5）* （5 -1）= 12个字节
         */
        long fileSize = source.length();
        long size = fileSize / COPY_THREAD_NUM;
        long lastSize = fileSize - (fileSize / COPY_THREAD_NUM) * (COPY_THREAD_NUM -1);

        for(int i=0; i < COPY_THREAD_NUM; i++){
            long start = i * size;
            long window = (i==(COPY_THREAD_NUM - 1))?lastSize:size;
            long end = start + window;

            if(start != 0){
                start++;
            }

            String fileName = TEMP_PAPTH + "/"+i+FILE_TEMP_SUFFIX;
            File target_tmp = new File(fileName);
            CopyTask task = new CopyTask(source, target_tmp, start, end, size);
            executor.execute(task);
        }
    }

    public static void main(String[] args) {
        File source = new File("/Users/11091752/Desktop/storage/data/srcdir/centos7.tar.gz");
        File target = new File("/Users/11091752/Desktop/storage/data/destdir/centos7.tar.gz");
        new FileCopy(source, target).splitCopyTask();

    }
}
