package sae;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CSVRow {
    private StringProperty column1;
    private StringProperty column2;

    public CSVRow(String column1, String column2) {
        this.column1 = new SimpleStringProperty(column1);
        this.column2 = new SimpleStringProperty(column2);
    }

    public String getColumn1() {
        return column1.get();
    }

    public StringProperty column1Property() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1.set(column1);
    }

    public String getColumn2() {
        return column2.get();
    }

    public StringProperty column2Property() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2.set(column2);
    }

    public boolean matchesFilters(String filter1, String filter2) {
        return column1.get().equals(filter1) && column2.get().equals(filter2);
    }

    public StringProperty valueProperty(int columnIndex) {
        if (columnIndex == 0) {
            return column1;
        } else if (columnIndex == 1) {
            return column2;
        } else {
            throw new IllegalArgumentException("Invalid column index: " + columnIndex);
        }
    }
    
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(column1.get());
        values.add(column2.get());
        return values;
    }
}

