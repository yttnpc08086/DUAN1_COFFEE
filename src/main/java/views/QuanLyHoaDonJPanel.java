/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import Helper.ConnectUtil;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class QuanLyHoaDonJPanel extends javax.swing.JPanel {

    private DefaultTableModel hoadonModel;
    private DefaultTableModel huyhoadonModel;

    /**
     * Creates new form QuanLyHoaDonJPanel
     */
    public QuanLyHoaDonJPanel() {
        initComponents();
        initTableModels();
        loadData();

    }

    private void loadData() {
        loadHoaDonData();
        loadHoaDonChiTietData();
//        filltoComboboxLSP();
    }

    private void loadHoaDonData() {
        String sql = "SELECT * FROM HoaDon";
        loadTableData(sql, tblHoadon, new String[]{"ID_Hoadon", "ID_Nhanvien", "Ngaytao", "TTThanhtoan", "Thanhtien", "ghichu", "SDT", "Ten", "tenShip"});
    }

    private void loadHoaDonChiTietData() {
        String sql = "SELECT * FROM HoaDonChiTiet";
        loadTableData(sql, tblHoadonchitiet, new String[]{"ID_HoaDon", "ID_SanPham", "SoLuong", "Gia", "TongGia", "TTthanhtoan", "Lydohuy", "ghichu"});
    }

    private void loadTableData(String sql, javax.swing.JTable table, String[] columnNames) {
        ResultSet rs = null;
        try {
            rs = ConnectUtil.query(sql);

            DefaultTableModel model = new DefaultTableModel();
            for (String columnName : columnNames) {
                model.addColumn(columnName);
            }

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (String columnName : columnNames) {
                    row.add(rs.getObject(columnName));
                }
                model.addRow(row);
            }

            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initTableModels() {
        // Initialize table models with column names
        hoadonModel = new DefaultTableModel(
                new String[]{"ID Hóa Đơn", "Nhân Viên", "Ngày Tạo", "Trạng Thái", "Thanh Toán", "Thành Tiền", "Lý Do Hủy", "Số Lượng SP Hủy", "Ghi Chú", "SDT", "Tên", "Địa Chỉ", "Tên Ship"}, 0
        );
        tblHoadon.setModel(hoadonModel);

        huyhoadonModel = new DefaultTableModel(
                new String[]{"ID Hóa Đơn", "Nhân Viên", "Ngày Tạo", "Trạng Thái", "Thanh Toán", "Thành Tiền", "Lý Do Hủy", "Số Lượng SP Hủy", "Ghi Chú", "SDT", "Tên", "Địa Chỉ", "Tên Ship"}, 0
        );
//        tblhuyhoadon.setModel(huyhoadonModel);
    }

//    private void loadData() {
//        DefaultTableModel modelHoadon = (DefaultTableModel) tblhoadon.getModel();
////        DefaultTableModel modelHuyhoadon = (DefaultTableModel) tblhuyhoadon.getModel();
//
//        try {
//            // Clear previous data
//            modelHoadon.setRowCount(0);
////            modelHuyhoadon.setRowCount(0);
//
//            // Load data for tblhoadon
//            ResultSet rs1 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 1");
//            while (rs1.next()) {
//                modelHoadon.addRow(new Object[]{
//                    rs1.getInt("ID_Hoadon"),
//                    rs1.getString("ID_Nhanvien"),
//                    rs1.getDate("Ngaytao"),
//                    rs1.getInt("Thanhtien"),
//                    rs1.getString("Lydohuy"),
//                    rs1.getInt("Soluongsanphamhuy"),
//                    rs1.getString("ghichu"),
//                    rs1.getString("SDT"),
//                    rs1.getString("Ten"),
//                    rs1.getString("diaChi"),
//                    rs1.getInt("tenShip")
//                });
//            }
//
//            // Load data for tblhuyhoadon
////            ResultSet rs2 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 0");
////            while (rs2.next()) {
////                modelHuyhoadon.addRow(new Object[]{
////                    rs2.getInt("ID_Hoadon"),
////                    rs2.getString("ID_Nhanvien"),
////                    rs2.getDate("Ngaytao"),
////                    rs2.getInt("Thanhtien"),
////                    rs2.getString("Lydohuy"),
////                    rs2.getInt("Soluongsanphamhuy"),
////                    rs2.getString("ghichu"),
////                    rs2.getString("SDT"),
////                    rs2.getString("Ten"),
////                    rs2.getString("diaChi"),
////                    rs2.getInt("tenShip")
////                });
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        btn_lammoi = new javax.swing.JButton();
        txtHD = new javax.swing.JTextField();
        cbTungay = new javax.swing.JComboBox<>();
        cbDenngay = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoadon = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoadonchitiet = new javax.swing.JTable();

        jPanel3.setBackground(new java.awt.Color(241, 241, 241));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Từ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã hóa đơn");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("đến");

        btnTim.setBackground(new java.awt.Color(255, 204, 204));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mã Nhân Viên");

        txtMaNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaNVKeyReleased(evt);
            }
        });

        btn_lammoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_lammoi.setText("Làm Mới");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        cbTungay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024-07-20 14:30:00.000", "2024-07-21 15:00:00.000", "2024-07-22 16:00:00.000", "2024-07-23 17:00:00.000", "2024-07-24 18:00:00.000", "2024-07-27 15:00:00.000", "2024-07-29 15:00:00.000", " " }));

        cbDenngay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024-07-20 14:30:00.000", "2024-07-21 15:00:00.000", "2024-07-22 16:00:00.000", "2024-07-23 17:00:00.000", "2024-07-24 18:00:00.000", "2024-07-27 15:00:00.000", "2024-07-29 15:00:00.000" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(60, 60, 60)
                        .addComponent(cbTungay, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43)
                        .addComponent(cbDenngay, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHD, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(106, 106, 106)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(cbTungay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbDenngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTim))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_lammoi)
                        .addGap(42, 42, 42))))
        );

        tblHoadon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoadonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoadonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblHoadonMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoadon);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1141, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );

        tabs.addTab("Hóa đơn", jPanel4);

        tblHoadonchitiet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHoadonchitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHoadonchitiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblHoadonchitietMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoadonchitiet);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1141, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Hóa đơn chi tiết", jPanel5);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

    }//GEN-LAST:event_jTextField1KeyReleased

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        String startDate = (String) cbTungay.getSelectedItem();
        String endDate = (String) cbDenngay.getSelectedItem();

        if (startDate != null && endDate != null) {
            // Gọi phương thức filterData với ngày bắt đầu và ngày kết thúc
            filterData(startDate, endDate);
        } else {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates.");
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void filterData(String startDate, String endDate) {
        DefaultTableModel modelHoadon = (DefaultTableModel) tblHoadon.getModel();
//        DefaultTableModel modelHuyhoadon = (DefaultTableModel) tblhuyhoadon.getModel();

        try {
            // Xóa dữ liệu cũ
            modelHoadon.setRowCount(0);
//            modelHuyhoadon.setRowCount(0);

            // Tìm dữ liệu cho tblhoadon
            ResultSet rs1 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 1 AND Ngaytao BETWEEN ? AND ?", startDate, endDate);
            while (rs1.next()) {
                modelHoadon.addRow(new Object[]{
                    rs1.getInt("ID_Hoadon"),
                    rs1.getString("ID_Nhanvien"),
                    rs1.getDate("Ngaytao"),
                    rs1.getInt("Thanhtien"),
                    rs1.getString("Lydohuy"),
                    rs1.getInt("Soluongsanphamhuy"),
                    rs1.getString("ghichu"),
                    rs1.getString("SDT"),
                    rs1.getString("Ten"),
                    rs1.getString("diaChi"),
                    rs1.getInt("tenShip")
                });
            }

            // Tìm dữ liệu cho tblhuyhoadon
//            ResultSet rs2 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 0 AND Ngaytao BETWEEN ? AND ?", startDate, endDate);
//            while (rs2.next()) {
//                modelHuyhoadon.addRow(new Object[]{
//                    rs2.getInt("ID_Hoadon"),
//                    rs2.getString("ID_Nhanvien"),
//                    rs2.getDate("Ngaytao"),
//                    rs2.getInt("Thanhtien"),
//                    rs2.getString("Lydohuy"),
//                    rs2.getInt("Soluongsanphamhuy"),
//                    rs2.getString("ghichu"),
//                    rs2.getString("SDT"),
//                    rs2.getString("Ten"),
//                    rs2.getString("diaChi"),
//                    rs2.getInt("tenShip")
//                });
//            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while retrieving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void txtMaNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaNVKeyReleased

    }//GEN-LAST:event_txtMaNVKeyReleased

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        txtHD.setText("");
        txtMaNV.setText("");
        cbTungay.setSelectedIndex(0);
        cbDenngay.setSelectedIndex(0);
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void tblHoadonchitietMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonchitietMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonchitietMouseReleased

    private void tblHoadonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonMouseReleased

    private void tblHoadonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonMousePressed

    private void tblHoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMouseClicked
        // TODO add your handling code here:
        int row = tblHoadon.getSelectedRow();
        if (row >= 0) {
            txtHD.setText(tblHoadon.getValueAt(row, 0).toString());
            txtMaNV.setText(tblHoadon.getValueAt(row, 1).toString());
        }
    }//GEN-LAST:event_tblHoadonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JComboBox<String> cbDenngay;
    private javax.swing.JComboBox<String> cbTungay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoadon;
    private javax.swing.JTable tblHoadonchitiet;
    private javax.swing.JTextField txtHD;
    private javax.swing.JTextField txtMaNV;
    // End of variables declaration//GEN-END:variables
}
