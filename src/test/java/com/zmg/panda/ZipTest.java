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
            zos.putNextEntry(new ZipEntry("file/a/"));
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
