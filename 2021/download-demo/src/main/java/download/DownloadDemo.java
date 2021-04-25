package download;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadDemo {
    private File source;
    private File target;
    private String TEMP_PATH = "/Users/lishihai/Desktop/code/data/temp";
    private String TEMP_SUFFIX = ".tmp";
    private int THREAD_NUM = 5;
    private int BUFFER_SIZE=1;
    private ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);

    private void splitFile(File source, List<Future<Boolean>> futureList ){
        long fileSize = source.length();
        String fileName = source.getName();
        long size = fileSize / THREAD_NUM;
        long lastSize = fileSize - (fileSize / THREAD_NUM) * (THREAD_NUM -1);

        for(int i=0; i<THREAD_NUM; i++){
            long start = i * size;
            long window = (i == (THREAD_NUM -1))? lastSize:size;
            long end = start + window;

            String tmpFIleName = TEMP_PATH+"/"+fileName+i+TEMP_SUFFIX;
            File targetTemp = new File(tmpFIleName);
            DownloadTask task = new DownloadTask(source,targetTemp, start, end);
            Future<Boolean> future = (Future<Boolean>) executor.submit(task);
            futureList.add(future);
        }
    }

    private boolean merge(File target,String fileName){
        RandomAccessFile reader = null;
        RandomAccessFile writer = null;
        byte[] buffer = new byte[BUFFER_SIZE];

        System.out.println("开始合并文件...");
        try {
            writer = new RandomAccessFile(target, "rw");
            for (int i = 0; i < THREAD_NUM; i++) {
                File tmpFIleName = new File(TEMP_PATH + "/" + fileName + i + TEMP_SUFFIX);
                long fileSize = tmpFIleName.length();
                reader = new RandomAccessFile(tmpFIleName, "r");
                reader.seek(0);
                int len = -1;
                while ((len = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, len);
                    if(reader.getFilePointer() >= fileSize){
                        break;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件合并完成！");
        return true;
    }

    private boolean clear(String fileName){
        for(int i=0;i<THREAD_NUM;i++){
            File tmpFIleName = new File(TEMP_PATH + "/"+ fileName + i + TEMP_SUFFIX);
            tmpFIleName.delete();
        }
        return true;
    }

    public void copy(File source, File target) throws ExecutionException, InterruptedException {
        List<Future<Boolean>> futureList = new ArrayList<>();
        String fileName = source.getName();
        // 分片
        splitFile(source, futureList);
        for(Future<Boolean> booleanFuture: futureList){
            booleanFuture.get();
        }
        // 合并
        merge(target, fileName);
        clear(fileName);
    }

    public static void main(String[] args) {
        File source = new File("/Users/lishihai/Desktop/code/data/src/l.docx");
        File target = new File("/Users/lishihai/Desktop/code/data/target/l.docx");
        try {
            new DownloadDemo().copy(source, target);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
