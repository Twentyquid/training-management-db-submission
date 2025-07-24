package com.litmus7.employeeManager.util;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadCSVFile {

    public static List<String[]> readCSV(String filePath) throws IOException, CsvException {
        try (FileReader fileReader = new FileReader(filePath);
             CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {
            return csvReader.readAll();
        }
    }
}
