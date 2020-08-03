package com.zmg.panda.utils.pdfbox.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andy
 */
public class PDFTableGenerator {

    /**
     * Generates document from Table object
     * @param document
     * @param table
     * @throws IOException
     */
    public void generatePDF(PDDocument document, Table table) throws IOException{
        drawTable(document, table);
    }

    public void drawTableCustom(PDDocument doc, FirstTablePage firstTablePage, Table table) throws IOException {
        // 处理第一页是和业务相关，非独立的
        if (firstTablePage != null) {
            Integer dataNum = firstTablePage.getDataNum();
            PDPage firstPdPage = firstTablePage.getFirstPdPage();
            PDPageContentStream contentStream = firstTablePage.getContentStream();
            contentStream.setFont(table.getTextFont(), table.getFontSize());
            List<List<String>> content = table.getContent();
            dataNum = dataNum > content.size() ? content.size() : dataNum;
            List<List<String>> firstPageContent = new ArrayList<>(dataNum);
            Iterator<List<String>> iterator = content.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                List<String> next = iterator.next();
                firstPageContent.add(next);
                iterator.remove();
                index ++;
                if (index >= dataNum) {
                    break;
                }
            }
            table.setContent(content);
            drawFirstCurrentPage(table, firstPageContent, contentStream, firstTablePage.getMargin());
        }

        // 每页的行数
        int rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1;
        // 计算需要多少页
        int numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // 剩下的的页
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            List<List<String>> currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream);
        }
    }

    /**
     * Configures basic setup for the table and draws it page by page
     * @param doc
     * @param table
     * @throws IOException
     */
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        // 每页的行数
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1;
        // 计算需要多少页
        int numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            List<List<String>> currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream);
        }
    }

    /**
     * Draws current page table grid and border lines and content
     * @param table
     * @param currentPageContent
     * @param contentStream
     * @throws IOException
     */
    private void drawCurrentPage(Table table, List<List<String>> currentPageContent, PDPageContentStream contentStream)
            throws IOException {
        float tableTopY = table.getPageSize().getHeight() - table.getMargin();

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        for (int i = 0; i < currentPageContent.size(); i++) {
            writeContentLine(currentPageContent.get(i), contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        contentStream.close();
    }

    /**
     * Draws current page table grid and border lines and content
     * @param table
     * @param currentPageContent
     * @param contentStream
     * @throws IOException
     */
    private void drawFirstCurrentPage(Table table, List<List<String>> currentPageContent, PDPageContentStream contentStream, Float margin)
            throws IOException {
        float tableTopY = table.getPageSize().getHeight() - table.getMargin() - margin;

        // Draws grid and borders
        drawTableGrid(table, currentPageContent, contentStream, tableTopY);

        // Position cursor to start drawing content
        float nextTextX = table.getMargin() + table.getCellMargin();
        // Calculate center alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
                - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table.getFontSize()) / 4);

        // Write column headers
        writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
        nextTextY -= table.getRowHeight();
        nextTextX = table.getMargin() + table.getCellMargin();

        // Write content
        for (int i = 0; i < currentPageContent.size(); i++) {
            writeContentLine(currentPageContent.get(i), contentStream, nextTextX, nextTextY, table);
            nextTextY -= table.getRowHeight();
            nextTextX = table.getMargin() + table.getCellMargin();
        }

        contentStream.close();
    }

    /**
     * Writes the content for one line
     * @param lineContent
     * @param contentStream
     * @param nextTextX
     * @param nextTextY
     * @param table
     * @throws IOException
     */
    private void writeContentLine(List<String> lineContent, PDPageContentStream contentStream, float nextTextX, float nextTextY,
                                  Table table) throws IOException {
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            String text = lineContent.get(i);
            contentStream.beginText();
            contentStream.newLineAtOffset(nextTextX, nextTextY);
            contentStream.showText(text != null ? text : "");
            contentStream.endText();
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

    private void drawTableGrid(Table table, List<List<String>> currentPageContent, PDPageContentStream contentStream, float tableTopY)
            throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        for (int i = 0; i <= currentPageContent.size() + 1; i++) {
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            nextY -= table.getRowHeight();
        }

        // Draw column lines
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.size());
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private List<List<String>> getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        List<List<String>> content = table.getContent();
        List<List<String>> result = new ArrayList<>(endRange - startRange);
        for (int i = startRange; i < endRange; i ++){
            result.add(content.get(i));
        }
        return result;
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage(table.getPageSize());
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }
}
