package download;

import java.io.*;

/**
 *  分片下载
 */
public class DownloadTask implements Runnable{
    private File source;
    private File target;
    private long start;
    private long end;
    private int BUFFER_SIZE=1024;

    private RandomAccessFile reader = null;
    private RandomAccessFile writer = null;
    //private OutputStream writer = null;
    private byte[] buffer = new byte[BUFFER_SIZE];


    public DownloadTask(File source, File target, long start, long end){
        this.source = source;
        this.target = target;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            reader = new RandomAccessFile(source, "r");
            writer = new RandomAccessFile(target, "rw");
            // writer = new BufferedOutputStream(new FileOutputStream(target));


            reader.seek(start); // 设置指针开始读取的位置
            // writer.seek(0); // 由于每一个分片都是一个文件，所以每次都是从头开始写

            int len = -1;
            while ((len = reader.read(buffer)) != -1){
                writer.write(buffer, 0, len);
                // writer.flush();
                if (reader.getFilePointer() >= end){
                    break;
                }
            }

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
}
