package com.project.ptittoanthu.infra.files;

import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.model.Question;
import org.apache.poi.ss.usermodel.Cell;
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
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            List<Question> questions = new ArrayList<>();

            int rowIndex = 0;
            for (Row row : sheet) {
                // Bỏ qua header row
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Question question = new Question();

                // Cột 0: Nội dung câu hỏi
                Cell questionCell = row.getCell(0);
                if(questionCell == null) continue; // Bỏ qua dòng trống
                question.setContent(questionCell.getStringCellValue());

                // Lấy index đáp án đúng (Cột 5 - Index F)
                // Giả sử người dùng nhập 1, 2, 3, 4. Trừ 1 để ra index mảng (0-3)
                int correctIndex = (int) row.getCell(5).getNumericCellValue() - 1;

                // Duyệt các cột Option (Từ cột 1 đến 4)
                for (int i = 1; i <= 4; i++) {
                    Cell optionCell = row.getCell(i);
                    if (optionCell != null) {
                        Option option = new Option();
                        option.setValue(optionCell.getStringCellValue());

                        // Kiểm tra xem đây có phải đáp án đúng không
                        if ((i - 1) == correctIndex) {
                            option.setCorrect(true);
                        } else {
                            option.setCorrect(false);
                        }

                        // Thêm option vào question (dùng helper method ở Entity)
                        question.getOptions().add(option);
                    }
                }

                questions.add(question);
                rowIndex++;
            }
            workbook.close();
            return questions;

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi phân tích file Excel: " + e.getMessage());
        }
    }
}
