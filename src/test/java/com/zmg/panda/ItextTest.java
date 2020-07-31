package com.zmg.panda;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.junit.jupiter.api.Test;

import java.io.*;

public class ItextTest {


    @Test
    public void test1() throws IOException, DocumentException {
        final Font title_font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, 18);
        final Font content_font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, 13);
        final Font tail_font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED, 12);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\tmp\\test.pdf"));
        document.open();
        document.newPage();
        // 横线
        LineSeparator line = new LineSeparator(1f, 100, BaseColor.BLACK, Element.ALIGN_CENTER, 0);
        // 结算单摘要
        document.add(new Paragraph("结算单摘要", title_font));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("结算单号：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a02022099", content_font));// 6
        document.add(new Paragraph("结算时间段：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0从20200909到20200807", content_font));// 4
        document.add(new Paragraph("案件总数量(件)：100000", content_font));
        document.add(new Paragraph("案件总标的(元)：100000000000", content_font));
        document.add(new Paragraph("申请人名称：\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0李白", content_font));// 4
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(line);
        // 公司盖章
        document.add(new Paragraph(" "));
        document.add(new Paragraph("公司(盖章)：", content_font));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("日期：", content_font));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(line);
        // 防伪码
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Image image = Image.getInstance(getFileByte(new File("D:\\tmp\\条形码测试.png")));
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(120, 120);
        document.add(image);
        Paragraph imgTitle = new Paragraph("熊猫-防伪码", tail_font);
        imgTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(imgTitle);
        document.newPage();
        document.add(new Paragraph("申请人案件提交明细", title_font));
        PdfPTable table = createTable(5, Element.ALIGN_CENTER);
        table.addCell(createCell("申请人名称", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("提交人", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("提交时间", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("案件编号", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("案件标的(元)", content_font, Element.ALIGN_CENTER));

        table.addCell(createCell("李太白", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("武藏", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("20202020-22929299", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("998", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("10000000", content_font, Element.ALIGN_CENTER));

        table.addCell(createCell("李太白", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("武藏", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("20202020-22929299", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("998", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("10000000", content_font, Element.ALIGN_CENTER));

        table.addCell(createCell("李太白", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("武藏", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("20202020-22929299", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("998", content_font, Element.ALIGN_CENTER));
        table.addCell(createCell("10000000", content_font, Element.ALIGN_CENTER));
        document.add(table);
        document.close();
        writer.close();
    }

    private PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(align);
        cell.setHorizontalAlignment(align);
//        cell.set
        cell.setPhrase(new Phrase(value, font));
        cell.setFixedHeight(20f);
        return cell;
    }

    private PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            // 设置表格宽度比例为%100
            table.setWidthPercentage(100);
            // 设置表格的宽度
            table.setTotalWidth(PageSize.A4.getWidth() - 60);
            // 锁住宽度
            table.setLockedWidth(true);
            // 设置表格上面空白宽度
            table.setSpacingBefore(20f);
            // 设置表格下面空白宽度
            table.setSpacingAfter(20f);
            // 设置表格默认为无边框
            table.getDefaultCell().setBorder(1);
            // 设置翻页后表格头部还继续存在
            table.setHeaderRows(1);
            // 设置水平样式
            table.setHorizontalAlignment(align);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    private byte[] getFileByte(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inputStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inputStream.close();
        return outStream.toByteArray();
    }
}
