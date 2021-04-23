package demo01;

import java.io.*;

interface FileCopyRunner {
    public void copy(File source, File target);
}

/**
 *   文件拷贝
 */
public class CopyFileDemo implements FileCopyRunner{

    InputStream fin = null;
    OutputStream fout = null;

    @Override
    public void copy(File source, File target) {
        try {
            fin = new BufferedInputStream(new FileInputStream(source));
            fout = new BufferedOutputStream(new FileOutputStream(target));

            byte[] buffer = new byte[1024];

            int result = 0;
            while ((result = fin.read(buffer, 0, buffer.length)) != -1){
                fout.write(buffer, 0 , result);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File source = new File("/Users/11091752/Desktop/storage/data/srcdir/centos7.tar.gz");
        File target = new File("/Users/11091752/Desktop/storage/data/destdir/centos7.tar.gz");

        long startTime = System.currentTimeMillis();
        new CopyFileDemo().copy(source,target);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("复制花费了"+endTime+"时间");
    }
}
