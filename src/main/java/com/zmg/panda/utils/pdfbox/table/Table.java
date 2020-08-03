package com.zmg.panda.utils.pdfbox.table;

import lombok.Data;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.ArrayList;
import java.util.List;

@Data
public class Table {
    /**
     * Table attributes
     */
    private float margin;
    private float height;
    private PDRectangle pageSize;
    private float rowHeight;

    /**
     * font attributes
     */
    private PDFont textFont;
    private float fontSize;

    /**
     * Content attributes
     */
    private Integer numberOfRows;
    private List<Column> columns;
    private List<List<String>> content;
    private float cellMargin;


    public float getWidth() {
        float tableWidth = 0f;
        for (Column column : columns) {
            tableWidth += column.getWidth();
        }
        return tableWidth;
    }

    public List<String> getColumnsNamesAsArray() {
        List<String> columnNames = new ArrayList<>(getNumberOfColumns());
        columns.forEach(e -> columnNames.add(e.getName()));
        return columnNames;
    }

    public Integer getNumberOfColumns() {
        return this.getColumns().size();
    }

    public Integer getNumberOfRows() {
        return this.content.size();
    }
}
