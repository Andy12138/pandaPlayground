package com.zmg.panda;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EasyExcelTest {

    @Test
    public void writeExcelTest() throws FileNotFoundException {
        OutputStream outputStream = null;
        List<String> list = Arrays.asList("1", "2", "小黑", "4");
        List<List<String>> header = new ArrayList<>();
        list.forEach(e -> header.add(Collections.singletonList(e)));
        outputStream = new FileOutputStream("C:\\Users\\Andy\\Desktop\\任务\\ODR\\xlsxTest.xlsx");
        File file = new File("C:\\Users\\Andy\\Desktop\\任务\\ODR\\xlsxTest2.xlsx");
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setFile(file);
        ExcelWriter writer = new ExcelWriter(writeWorkbook);
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetName("我本善良_andy");
        sheet.setSheetNo(1);
        writer.write(list, sheet);
        sheet.setHead(header);

        writer.finish();
    }
}
