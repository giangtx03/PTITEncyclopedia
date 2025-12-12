package com.project.ptittoanthu.infra.files;

import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.model.Question;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Question> excelToQuestions(InputStream is) {
        try (Workbook workbook = new XSSFWorkbook(is)) { // Dùng try-with-resources để tự đóng file
            Sheet sheet = workbook.getSheetAt(0);
            List<Question> questions = new ArrayList<>();

            // --- CHÌA KHÓA ĐỂ FIX LỖI ---
            DataFormatter formatter = new DataFormatter();

            int rowIndex = 0;
            for (Row row : sheet) {
                // 1. Bỏ qua header
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                if (row == null) continue;
                String content = formatter.formatCellValue(row.getCell(0));
                if (content == null || content.trim().isEmpty()) {
                    continue;
                }
                Question question = new Question();
                question.setContent(content);
                if (question.getOptions() == null) {
                    question.setOptions(new ArrayList<>());
                }
                int correctIndex = -1;
                try {
                    String indexStr = formatter.formatCellValue(row.getCell(5));
                    if (!indexStr.isEmpty()) {
                        double val = Double.parseDouble(indexStr);
                        correctIndex = (int) val - 1;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi format cột Index ở dòng " + rowIndex + ": " + e.getMessage());
                }
                boolean hasOptions = false;
                for (int i = 1; i <= 4; i++) {
                    String optionVal = formatter.formatCellValue(row.getCell(i));

                    if (!optionVal.isEmpty()) {
                        Option option = new Option();
                        option.setValue(optionVal);
                        option.setQuestion(question);
                        if ((i - 1) == correctIndex) {
                            option.setCorrect(true);
                        } else {
                            option.setCorrect(false);
                        }
                        question.getOptions().add(option);
                        hasOptions = true;
                    }
                }
                if (hasOptions) {
                    questions.add(question);
                }
                rowIndex++;
            }
            return questions;

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi phân tích file Excel: " + e.getMessage());
        }
    }
}
