package demo02;

import java.io.*;

public class CopyTask implements Runnable {
    private Long start = null;
    private Long end = null;
    private File source;
    private File target;
    private long size;

    public CopyTask(File source, File target,long start, long end, long size){
        this.start = start;
        this.end = end;
        this.source = source;
        this.target = target;
        this.size = size;
    };

    private synchronized void doCopy(){
        RandomAccessFile reader = null;
        RandomAccessFile writer = null;

        try {
            reader = new RandomAccessFile(source, "r");
            writer = new RandomAccessFile(target, "rw");

            byte[] buffer = new byte[1024*100]; // 1KB

            int len = 0;
            reader.seek(start);  // 设置读文件指针的起点
            writer.seek(start); // 设置写文件指针的起点

            while ((len = reader.read(buffer)) != -1){
                writer.write(buffer, 0, len);
                // writer.flush();
                if(writer.getFilePointer() >= end){
                    System.out.println("读取完毕: "+target);
                    break;
                }
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        doCopy();

    }
}
