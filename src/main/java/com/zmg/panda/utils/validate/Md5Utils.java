package com.zmg.panda.utils.validate;

import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Md5Utils {
    
    public void validateFileMd5(String filePath1, String filePath2) {
        String md5 = DigestUtils.md5DigestAsHex(fileConvertToByteArray(new File(filePath1)));
        System.out.println("p1_MD5:"+md5);

        String md52 = DigestUtils.md5DigestAsHex(fileConvertToByteArray(new File(filePath2)));
        System.out.println("p2_MD5:"+md52);
    }

    /**
     * 把一个文件转化为byte字节数组。
     *
     * @return
     */
    private byte[] fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toByteArray();
            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
