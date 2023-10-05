package sae;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    public static List<CSVRow> readCSV(File file) throws IOException {
        List<CSVRow> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 2) {
                String column1 = values[0].trim();
                String column2 = values[1].trim();
                CSVRow row = new CSVRow(column1, column2);
                data.add(row);
            }
        }
        reader.close();

        return data;
    }
}