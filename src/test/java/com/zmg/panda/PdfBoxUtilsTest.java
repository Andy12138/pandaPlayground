package com.zmg.panda;

import com.zmg.panda.utils.pdfbox.PdfBoxUtils;
import com.zmg.panda.utils.pdfbox.table.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PdfBoxUtilsTest {

    private PDRectangle pageSize = PDRectangle.A4;

    private Integer marginX = 50;
    private Integer marginY = 50;

    @Test
    public void test1() throws IOException {
        PDDocument document = new PDDocument();
        PDType0Font font = PDType0Font.load(document, new FileInputStream(new File("d:\\tmp\\simsun.ttf")));
        // drawFirstPage(document, font);
        drawSecondPage(document, font);
        document.save(new FileOutputStream(new File("d:\\tmp\\test2.pdf")));
        document.close();
    }

    private void drawFirstPage(PDDocument document, PDType0Font font) throws IOException {
        PDPage pdPage = new PDPage(pageSize);
        document.addPage(pdPage);
        PDPageContentStream contentStream = new PDPageContentStream(document, pdPage);

        PdfBoxUtils.beginTextSteam(contentStream, 20f, marginX.floatValue(), pageSize.getHeight()-(2*marginY));
        // 书写信息
        PdfBoxUtils.drawParagraph(contentStream, "物流单摘要", font, 18);
        PdfBoxUtils.createEmptyParagraph(contentStream, 2);

        contentStream.setFont(font, 13);
        PdfBoxUtils.drawParagraph(contentStream, "结算单号：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a02022099");
        PdfBoxUtils.drawParagraph(contentStream, "结算时间段：\u00a0\u00a0\u00a0\u00a0\u00a0从20200909到20200807");
        PdfBoxUtils.drawParagraph(contentStream, "案件总数量(件)：\u00a0100000");
        PdfBoxUtils.drawParagraph(contentStream, "案件总标的(元)：\u00a0100000000000");
        PdfBoxUtils.drawParagraph(contentStream, "申请人名称：\u00a0\u00a0\u00a0\u00a0\u00a0李白");
        PdfBoxUtils.createEmptyParagraph(contentStream, 4);

        PdfBoxUtils.drawParagraph(contentStream, "公司(盖章)：");
        PdfBoxUtils.createEmptyParagraph(contentStream, 2);
        contentStream.showText("日期：");

        PdfBoxUtils.createEmptyParagraph(contentStream, 16);
        contentStream.newLineAtOffset(195, 0);
        PdfBoxUtils.drawParagraph(contentStream, "小熊猫超级防伪码", font, 12);

        PdfBoxUtils.endTextSteam(contentStream);

        // 划线
        PdfBoxUtils.drawLine(contentStream, marginX, 545, PDRectangle.A4.getWidth() - marginX, 545);
        PdfBoxUtils.drawLine(contentStream, marginX, 410, PDRectangle.A4.getWidth() - marginX, 410);

        // 贴图
        PdfBoxUtils.drawImage(document, contentStream, new FileInputStream(new File("d:\\tmp\\条形码测试.png")),
                (pageSize.getWidth()/2)-80, 150, 160, 160);

        contentStream.close();
    }

    private void drawSecondPage(PDDocument document, PDType0Font font) throws IOException {
        PDPage mainTablePage = new PDPage(pageSize);
        document.addPage(mainTablePage);
        PDPageContentStream contentStream = new PDPageContentStream(document, mainTablePage);

        PdfBoxUtils.beginTextSteam(contentStream, 20f, marginX.floatValue(), pageSize.getHeight() - 2*marginY);
        // 书写信息
        PdfBoxUtils.drawParagraph(contentStream, "买卖人商品提交明细", font, 18);
        PdfBoxUtils.endTextSteam(contentStream);

        // 开始绘制table
        List<Column> header = initTableHeader();

        List<List<String>> records = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            records.add(Arrays.asList( "李太白" + i, "广州市分机构","20202020", "10000000"));
        }

        float tableHight = pageSize.getHeight() - (2 * marginY);

        Table table = new TableBuilder()
                .setCellMargin(4)
                .setRowHeight(20)
                .setColumns(header)
                .setContent(records)
                .setHeight(tableHight)
                .setMargin(marginX)
                .setPageSize(pageSize)
                .setTextFont(font)
                .setFontSize(13)
                .build();

        // 每页最多显示的条数
        Integer rowsPerPage = table.getRowsPerPage();
        // 首页
        Integer dataNum = 30;
        FirstTablePage firstTablePage = new FirstTablePage();
        firstTablePage.setDataNum(dataNum);
        firstTablePage.setMargin(100f);
        firstTablePage.setContentStream(contentStream);

        int firstBatch = rowsPerPage + dataNum;
        List<List<String>> firstRecords = new ArrayList<>(firstBatch);
        Iterator<List<String>> iterator = records.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            List<String> record = iterator.next();
            firstRecords.add(record);
            iterator.remove();
            index ++;
            if (index >= firstBatch) {
                break;
            }
        }
        table.setRecords(firstRecords);
        new PdfTableGenerator().drawTableCustom(document, firstTablePage, table);
        // 剩下的
        int batchNum = rowsPerPage * 2;
        List<List<String>> batchRecords = new ArrayList<>(batchNum);
        iterator = records.iterator();
        index = 0;
        while (iterator.hasNext()) {
            List<String> record = iterator.next();
            batchRecords.add(record);
            iterator.remove();
            index ++;
            if (index % batchNum == 0) {
                table.setRecords(batchRecords);
                new PdfTableGenerator().drawTableCustom(document, null, table);
                batchRecords = new ArrayList<>(batchNum);
            }
        }
        table.setRecords(batchRecords);
        new PdfTableGenerator().drawTableCustom(document, null, table);
    }

    private List<Column> initTableHeader() {
        List<Column> header = new ArrayList<Column>();
        header.add(new Column("买卖人人名称", 150));
        header.add(new Column("店铺名称", 150));
        header.add(new Column("商品号", 100));
        header.add(new Column("商品价格(元)", 100));
        return header;
    }


    @Test
    public void test2() throws IOException {
//        File file = new File("d:\\tmp\\testhtml.html");
//        String collect = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining("\n"));
        PdfBoxUtils.convertHtmlToPdf(new FileOutputStream(new File("d:\\tmp\\html.pdf")),
                new File("d:\\tmp\\testhtml.html"),
                new FileInputStream(new File("d:\\tmp\\arialuni.ttf")),
                "arialuni");
    }
}
