package com.zmg.panda.utils.reader;

import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class CsvUtils {

    public static void main(String[] args) {
        try {
            CsvReader csvReader = new CsvReader("d://lfq.csv", ',', Charset.forName("gbk"));
            while (csvReader.readRecord()) {
                System.out.println(csvReader.getColumnCount());
                System.out.println(csvReader.getRawRecord());
                String[] values = csvReader.getValues();
                System.out.println(values);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
