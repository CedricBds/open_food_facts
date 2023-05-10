package org.diginamic.creation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser {

    public static List parseCSV(String file) throws FileNotFoundException,
            IOException {
        CSVParser parser = new CSVParser(new FileReader("./inputs/" + file),
                CSVFormat.EXCEL);

        List<CSVRecord> records = parser.getRecords();
        String[] line = Arrays.toString(records.get(6).values()).replace("[", "").replace("]", "").split("\\|");

        System.out.println(line[1]);
   
        parser.close();
        return records;
    }
}