/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import Helper.ConnectUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    private JButton btnOpenEmailForm;

    /**
     * Creates new form ThongKeJPanel
     */
    public ThongKeJPanel() {
        initComponents();
        loadDataToTable();
        calculateTotalRevenue();
        calculateTotalCancelledRevenue();
        calculateTotalQuantities();
        setupEmailButton();
    }

    private void setupEmailButton() {
        btnGuiMail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmailForm();
            }
        });

        btnOpenEmailForm = new JButton("Open Email Form");
        btnOpenEmailForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEmailForm();
            }
        });

        this.add(btnOpenEmailForm);

        btnbieudo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBieuDoForm();
            }
        });
    }

    private void showBieuDoForm() {
        BieuDo bieuDoForm = new BieuDo();
        bieuDoForm.setVisible(true); // Hiển thị JFrame
    }

    private void showEmailForm() {
        EmailForm emailForm = new EmailForm();
        emailForm.setVisible(true); // Hiển thị JFrame
    }

    private void loadDataToTable() {
        try {
            DefaultTableModel modelDaBan = (DefaultTableModel) tblbang.getModel();
            modelDaBan.setRowCount(0); // Xóa dữ liệu cũ nếu có

            DefaultTableModel modelDaHuy = (DefaultTableModel) tblbanghuy.getModel();
            modelDaHuy.setRowCount(0); // Xóa dữ liệu cũ nếu có

            String sql = "SELECT TenSanPham, SoLuongDaBan, TongTienDaBan, SoLuongDaHuy, TongTienDaHuy FROM ThongKe";
            ResultSet rs = ConnectUtil.query(sql);

            while (rs.next()) {
                // Thêm dữ liệu vào bảng tblbang
                Object[] rowDaBan = {
                    rs.getString("TenSanPham"),
                    rs.getInt("SoLuongDaBan"),
                    rs.getInt("TongTienDaBan")
                };
                modelDaBan.addRow(rowDaBan);

                // Thêm dữ liệu vào bảng tblbanghuy
                Object[] rowDaHuy = {
                    rs.getString("TenSanPham"),
                    rs.getInt("SoLuongDaHuy"),
                    rs.getInt("TongTienDaHuy")
                };
                modelDaHuy.addRow(rowDaHuy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateTotalRevenue() {
        int totalRevenue = 0;
        DefaultTableModel modelDaBan = (DefaultTableModel) tblbang.getModel();
        for (int i = 0; i < modelDaBan.getRowCount(); i++) {
            totalRevenue += (int) modelDaBan.getValueAt(i, 2); // Cột thứ 3 là TongTienDaBan
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotalRevenue = currencyFormat.format(totalRevenue).replace("₫", "VND");
        lblDoanhthu.setText(formattedTotalRevenue);
    }

    private void calculateTotalCancelledRevenue() {
        int totalCancelledRevenue = 0;
        DefaultTableModel modelDaHuy = (DefaultTableModel) tblbanghuy.getModel();
        for (int i = 0; i < modelDaHuy.getRowCount(); i++) {
            totalCancelledRevenue += (int) modelDaHuy.getValueAt(i, 2); // Cột thứ 3 là TongTienDaHuy
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotalCancelledRevenue = currencyFormat.format(totalCancelledRevenue).replace("₫", "VND");
        lblsosanhso.setText(formattedTotalCancelledRevenue);
    }

    private void calculateTotalQuantities() {
        int totalSoldQuantity = 0;
        int totalCancelledQuantity = 0;

        DefaultTableModel modelDaBan = (DefaultTableModel) tblbang.getModel();
        for (int i = 0; i < modelDaBan.getRowCount(); i++) {
            totalSoldQuantity += (int) modelDaBan.getValueAt(i, 1); // Cột thứ 2 là SoLuongDaBan
        }

        DefaultTableModel modelDaHuy = (DefaultTableModel) tblbanghuy.getModel();
        for (int i = 0; i < modelDaHuy.getRowCount(); i++) {
            totalCancelledQuantity += (int) modelDaHuy.getValueAt(i, 1); // Cột thứ 2 là SoLuongDaHuy
        }

        lblDoanhthu1.setText(String.valueOf(totalSoldQuantity));
        lbltongsanpham.setText(String.valueOf(totalCancelledQuantity));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        lbldoanhthutext = new javax.swing.JLabel();
        lblDoanhthu = new javax.swing.JLabel();
        lblsosanh = new javax.swing.JLabel();
        lblsosanhso = new javax.swing.JLabel();
        lblDoanhthu1 = new javax.swing.JLabel();
        lbldoanhthutext1 = new javax.swing.JLabel();
        lbltongsanpham = new javax.swing.JLabel();
        lbldoanhthutext2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblbanghuy = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnbieudo = new javax.swing.JButton();
        btnGuiMail = new javax.swing.JButton();
        btnIn = new javax.swing.JButton();
        txttimkiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbang = new javax.swing.JTable();

        jPanel4.setBackground(new java.awt.Color(255, 193, 75));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        lbldoanhthutext.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbldoanhthutext.setForeground(new java.awt.Color(255, 255, 255));
        lbldoanhthutext.setText("Doanh thu");

        lblDoanhthu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDoanhthu.setText("00000000000000");

        lblsosanh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsosanh.setForeground(new java.awt.Color(255, 255, 255));
        lblsosanh.setText("Doanh Thu So Với ");

        lblsosanhso.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblsosanhso.setText("00000000000000");

        lblDoanhthu1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDoanhthu1.setText("00000000000000");

        lbldoanhthutext1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbldoanhthutext1.setForeground(new java.awt.Color(255, 255, 255));
        lbldoanhthutext1.setText("Tổng số lương đã bán");

        lbltongsanpham.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbltongsanpham.setText("00000000000000");

        lbldoanhthutext2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbldoanhthutext2.setForeground(new java.awt.Color(255, 255, 255));
        lbldoanhthutext2.setText("Tổng số lượng đã hủy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbldoanhthutext, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoanhthu, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldoanhthutext1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoanhthu1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblsosanh, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsosanhso)
                    .addComponent(lbltongsanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldoanhthutext2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbldoanhthutext)
                    .addComponent(lblsosanh))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDoanhthu)
                    .addComponent(lblsosanhso))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbldoanhthutext1)
                        .addGap(15, 15, 15)
                        .addComponent(lblDoanhthu1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbldoanhthutext2)
                        .addGap(15, 15, 15)
                        .addComponent(lbltongsanpham)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(241, 241, 241));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn Hủy Theo Nhân Viên:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblbanghuy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên Sản Phẩm", "Số Lượng Đã Hủy", "Tổng Tiền"
            }
        ));
        jScrollPane4.setViewportView(tblbanghuy);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 193, 75));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        btnbieudo.setText("Biểu Đồ");
        btnbieudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbieudoActionPerformed(evt);
            }
        });

        btnGuiMail.setText("Gửi Mail Báo Cáo");
        btnGuiMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiMailActionPerformed(evt);
            }
        });

        btnIn.setText("IN ");
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        txttimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttimkiemKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("tìm kiếm(theo tên)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnbieudo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuiMail, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIn, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(btnbieudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuiMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel1.setBackground(new java.awt.Color(241, 241, 241));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Doanh Thu Sản Phẩm:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên Sản Phẩm", "Số Lượng Đã Bán", "Tổng Tiền"
            }
        ));
        jScrollPane2.setViewportView(tblbang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnbieudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbieudoActionPerformed

    }//GEN-LAST:event_btnbieudoActionPerformed

    private void btnGuiMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiMailActionPerformed

    }//GEN-LAST:event_btnGuiMailActionPerformed
    private void exportToExcel(File file) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetBang = workbook.createSheet("Doanh thu sản phẩm");
        Sheet sheetBangHuy = workbook.createSheet("Đơn hủy theo nhân viên");

        // Xuất dữ liệu từ tblbang
        exportTableToSheet(tblbang, sheetBang);

        // Xuất dữ liệu từ tblbanghuy
        exportTableToSheet(tblbanghuy, sheetBangHuy);

        // Lưu file Excel vào máy
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
            javax.swing.JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra Excel thành công.");
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất dữ liệu ra Excel.");
        }
    }

    private void exportTableToSheet(javax.swing.JTable table, Sheet sheet) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Tạo tiêu đề cột
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            headerRow.createCell(i).setCellValue(model.getColumnName(i));
        }

        // Tạo dữ liệu bảng
        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < model.getColumnCount(); j++) {
                row.createCell(j).setCellValue(model.getValueAt(i, j).toString());
            }
        }
        for (int i = 0; i < model.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave + ".xlsx");
            }
            exportToExcel(fileToSave);
        }
    }//GEN-LAST:event_btnInActionPerformed

    private void filterTableByProductCode(String productCode) {
        DefaultTableModel model = (DefaultTableModel) tblbang.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        tblbang.setRowSorter(tr);

        // Sử dụng RowFilter để lọc theo mã sản phẩm (ID_Sanpham)
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + productCode, 0)); // Cột 0 là cột chứa mã sản phẩm
    }

    private void txttimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttimkiemKeyReleased
        // TODO add your handling code here:
        String searchText = txttimkiem.getText().trim();
        filterTableByProductCode(searchText);
    }//GEN-LAST:event_txttimkiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuiMail;
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnbieudo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDoanhthu;
    private javax.swing.JLabel lblDoanhthu1;
    private javax.swing.JLabel lbldoanhthutext;
    private javax.swing.JLabel lbldoanhthutext1;
    private javax.swing.JLabel lbldoanhthutext2;
    private javax.swing.JLabel lblsosanh;
    private javax.swing.JLabel lblsosanhso;
    private javax.swing.JLabel lbltongsanpham;
    private javax.swing.JTable tblbang;
    private javax.swing.JTable tblbanghuy;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
