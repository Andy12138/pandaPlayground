package com.zmg.panda.utils.pdfbox.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDFSample {

    private static final String fontPath = "d:\\tmp\\arialuni.ttf";
    private PDDocument document;
    private static PDType0Font FONT;

    private void init() throws IOException {
        this.document = new PDDocument();
        FONT = PDType0Font.load(document, new File(fontPath));
    }

    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.A4;
    private static final float MARGIN = 50;

    // Font configuration
//    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 13;

    // Table configuration
    private static final float ROW_HEIGHT = 20;
    private static final float CELL_MARGIN = 4;

    public static void main(String[] args) throws IOException{
        PDFSample sample = new PDFSample();
        sample.init();
        new PDFTableGenerator().generatePDF(sample.document, createContent());
        sample.document.save("d:\\tmp\\sampleTable.pdf");
        sample.document.save("d:\\tmp\\sampleTable.pdf");
        sample.document.close();
    }

    private static Table createContent() {
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("FirstName", 90));
        columns.add(new Column("LastName", 90));
        columns.add(new Column("Email", 150));
        columns.add(new Column("ZipCode", 80));


        List<List<String>> content = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            content.add(Arrays.asList( "名桂" + i, "LastName", "fakemail@mock.com", "12345"));
        }

        // 页面高度
        float tableHeight = PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder()
                .setCellMargin(CELL_MARGIN)
                .setColumns(columns)
                .setContent(content)
                .setHeight(tableHeight)
                .setNumberOfRows(content.size())
                .setRowHeight(ROW_HEIGHT)
                .setMargin(MARGIN)
                .setPageSize(PAGE_SIZE)
                .setTextFont(FONT)
                .setFontSize(FONT_SIZE)
                .build();
        return table;
    }
}
