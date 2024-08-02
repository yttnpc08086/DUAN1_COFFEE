/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */

public class XEcel {

    private JTable table; // Giả sử bạn đã khai báo bảng này ở đâu đó trong lớp

    public void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("DSSV.xlsx")); // Đặt tên file mặc định

        // Hiển thị hộp thoại lưu file
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            MsgBox.alert(null, "Người dùng hủy chọn lưu file.");
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        // Nếu người dùng không nhập đuôi file, thêm đuôi ".xlsx"
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheetHd = workbook.createSheet("NV hoạt động");
            Sheet sheetkhd = workbook.createSheet("NV không hoạt động");

            exportTableToSheet(table, sheetHd);
            exportTableToSheet(table, sheetkhd);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                MsgBox.alert(null, "Xuất dữ liệu ra Excel thành công.");
            } catch (IOException e) {
                e.printStackTrace();
                MsgBox.alert(null, "Có lỗi xảy ra khi xuất dữ liệu ra Excel.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            MsgBox.alert(null, "Có lỗi xảy ra khi tạo workbook.");
        }
    }

    private void exportTableToSheet(JTable table, Sheet sheet) {
        if (table == null || sheet == null) {
            throw new IllegalArgumentException("Table or Sheet is null");
        }

        TableModel model = table.getModel();

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(model.getColumnName(i));
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(Objects.toString(model.getValueAt(i, j), ""));
            }
        }

        // Auto size columns once after all data is populated
        for (int i = 0; i < model.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }
    }
}