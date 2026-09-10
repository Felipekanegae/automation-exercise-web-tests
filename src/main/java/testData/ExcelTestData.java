package testData;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelTestData {

    private final Map<String, String> testData = new HashMap<>();

    public void loadTestData(String filePath, String sheetName, String testCase) {

        try (InputStream input = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            Row header = sheet.getRow(0);

            if (header == null) {
                throw new RuntimeException("Header not found in spreadsheet.");
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String testCaseValue = getCellValue(row.getCell(0));

                if (testCaseValue.equals(testCase)) {

                    for (int column = 0; column < header.getLastCellNum(); column++) {

                        String columnName = getCellValue(header.getCell(column));
                        String cellValue = getCellValue(row.getCell(column));

                        testData.put(columnName, cellValue);
                    }

                    return;
                }
            }

            throw new RuntimeException("Test case not found in spreadsheet: " + testCase);

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel test data: " + e.getMessage(), e);
        }
    }

    public String getStringOf(String column) {

        String value = testData.get(column);

        if (value == null) {
            throw new RuntimeException("Column not found in test data: " + column);
        }

        return value;
    }

    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}