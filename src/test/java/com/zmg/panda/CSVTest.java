package com.zmg.panda;

import com.csvreader.CsvWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CSVTest {

    @Test
    public void writeCsvTest() throws IOException {
        File file = new File("C:\\Users\\Andy\\Desktop\\任务\\ODR\\csvTest.csv");
        CsvWriter csvWriter = new CsvWriter(file.getPath(), ',', StandardCharsets.UTF_8);
        csvWriter.writeRecord(new String[]{"1", "2"});
        csvWriter.writeRecord(new String[]{"黑", "白"});
        csvWriter.close();
    }
}
