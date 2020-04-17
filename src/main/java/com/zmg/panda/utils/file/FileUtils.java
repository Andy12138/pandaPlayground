package com.zmg.panda.utils.file;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

public class FileUtils {

    public void previewPdf(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        if (file.exists()){
            byte[] data = null;
            try {
                FileInputStream input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
                input.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }else{
            return;
        }
    }
}
