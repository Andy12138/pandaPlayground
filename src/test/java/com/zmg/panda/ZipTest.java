package com.zmg.panda;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {

    @Test
    public void test1() {
        File zipf = new File("d:\\tmp\\hello.zip");
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipf));
            // 压缩的文件
            File infile = new File("d:\\tmp\\条形码测试.png");
            FileInputStream fis = new FileInputStream(infile);
            // 设置zip中的组织结构
            zos.putNextEntry(new ZipEntry(infile.getName()));
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) > 0) {
                zos.write(bytes, 0, len);
            }
            fis.close();
            // *******压缩包内放入一个含有文件的目录
            createFileDir(zos, infile);
            // *******
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileDir(ZipOutputStream zos, File infile) throws IOException {
        zos.putNextEntry(new ZipEntry("file/a/条形码测试.png"));
        FileInputStream fis2 = new FileInputStream(infile);
        int len2 = 0;
        byte[] bytes2 = new byte[1024];
        while ((len2 = fis2.read(bytes2)) > 0) {
            zos.write(bytes2, 0, len2);
        }
        fis2.close();

        zos.putNextEntry(new ZipEntry("file"+File.separator+"a/test2.pdf"));
        FileInputStream fis3 = new FileInputStream(new File("d:\\tmp\\test2.pdf"));
        int len3 = 0;
        byte[] bytes3 = new byte[1024];
        while ((len3 = fis3.read(bytes3)) > 0) {
            zos.write(bytes3, 0, len3);
        }
        fis3.close();
    }
}
