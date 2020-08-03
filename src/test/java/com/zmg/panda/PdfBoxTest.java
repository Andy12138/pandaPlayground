package com.zmg.panda;

import com.zmg.panda.utils.pdfbox.table.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PdfBoxTest {

    private static final String fontPath = "d:\\tmp\\arialuni.ttf";
    private PDDocument document;
    private PDType0Font font;

    private void init() throws IOException {
        this.document = new PDDocument();
        font = PDType0Font.load(document, new File(fontPath));
    }


    @Test
    public void test1() throws IOException {
        this.init();
        PDPage pdPage = new PDPage(PDRectangle.A4);

        PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);

        contentStream.beginText();
        
        contentStream.setFont(font, 18);
        contentStream.setLeading(20f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("结算单摘要");
        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(font, 13);
        contentStream.showText("结算单号：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a02022099");
        contentStream.newLine();
        contentStream.showText("结算时间段：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0从20200909到20200807");// 4
        contentStream.newLine();
        contentStream.showText("案件总数量(件)：\u00a0100000");
        contentStream.newLine();
        contentStream.showText("案件总标的(元)：\u00a0100000000000");
        contentStream.newLine();
        contentStream.showText("申请人名称：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0李白");// 4
        contentStream.newLine();
        contentStream.newLine();
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("公司(盖章)：");
        contentStream.newLine();
        contentStream.newLine();

        contentStream.showText("日期：");

        contentStream.newLine();
        contentStream.endText();
        // 划线
        contentStream.moveTo(25, 595);
        contentStream.lineTo(PDRectangle.A4.getWidth() - 25 , 595);
        contentStream.stroke();
        contentStream.moveTo(25, 475);
        contentStream.lineTo(PDRectangle.A4.getWidth() - 25 , 475);
        contentStream.stroke();
        // 贴图
        PDImageXObject pdImage = PDImageXObject.createFromFileByContent(new File("d:\\tmp\\条形码测试.png"), document);
        contentStream.drawImage(pdImage, (PDRectangle.A4.getWidth()/2)-78, 200, 150, 150 );

        contentStream.close();
        document.addPage(pdPage);
        writeSecondPage();
        document.save(new FileOutputStream(new File("d:\\tmp\\test2.pdf")));
        document.close();
    }

    private void writeSecondPage() throws IOException {
        PDRectangle pageSize = PDRectangle.A4;
        PDPage mainTablePage = new PDPage(pageSize);
        document.addPage(mainTablePage);
        PDPageContentStream contentStream = new PDPageContentStream(document, mainTablePage);
        contentStream.beginText();

        contentStream.setFont(font, 18);
        contentStream.setLeading(20f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("申请人案件提交明细");
        contentStream.newLine();
        contentStream.newLine();

        contentStream.endText();

        // 开始绘制table
        List<Column> header = new ArrayList<Column>();
        header.add(new Column("申请人名称", 100));
        header.add(new Column("提交人", 100));
        header.add(new Column("提交时间", 100));
        header.add(new Column("收案号", 100));
        header.add(new Column("案件标的(元)", 100));

        List<List<String>> records = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            records.add(Arrays.asList( "李太白" + i, "武藏", "20202020", "998", "10000000"));
        }

        Integer margin = 50;
        float tableHight = pageSize.getHeight() - (2 * margin);

        Table table = new TableBuilder()
                .setCellMargin(4)
                .setColumns(header)
                .setContent(records)
                .setHeight(tableHight)
                .setRowHeight(20)
                .setMargin(margin)
                .setPageSize(pageSize)
                .setTextFont(font)
                .setFontSize(13)
                .build();

        FirstTablePage firstTablePage = new FirstTablePage();
        firstTablePage.setDataNum(30);
        firstTablePage.setMargin(100f);
        firstTablePage.setFirstPdPage(mainTablePage);
        firstTablePage.setContentStream(contentStream);
        new PdfTableGenerator().drawTableCustom(document, firstTablePage, table);

    }
}
