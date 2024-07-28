/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import Helper.ConnectUtil;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class QuanLyHoaDonJPanel extends javax.swing.JPanel {

    private DefaultTableModel hoadonModel;
    private DefaultTableModel huyhoadonModel;
    private Object dateFormatter;

    /**
     * Creates new form QuanLyHoaDonJPanel
     */
    public QuanLyHoaDonJPanel() {
        initComponents();
        initTableModels();
        loadData();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    private void initTableModels() {
        // Initialize table models with column names
        hoadonModel = new DefaultTableModel(
                new String[]{"ID Hóa Đơn", "Nhân Viên", "Ngày Tạo", "Trạng Thái", "Thanh Toán", "Thành Tiền", "Lý Do Hủy", "Số Lượng SP Hủy", "Ghi Chú", "SDT", "Tên", "Địa Chỉ", "Tên Ship"}, 0
        );
        tblhoadon.setModel(hoadonModel);

        huyhoadonModel = new DefaultTableModel(
                new String[]{"ID Hóa Đơn", "Nhân Viên", "Ngày Tạo", "Trạng Thái", "Thanh Toán", "Thành Tiền", "Lý Do Hủy", "Số Lượng SP Hủy", "Ghi Chú", "SDT", "Tên", "Địa Chỉ", "Tên Ship"}, 0
        );
        tblhuyhoadon.setModel(huyhoadonModel);
    }

    private void loadData() {
        DefaultTableModel modelHoadon = (DefaultTableModel) tblhoadon.getModel();
        DefaultTableModel modelHuyhoadon = (DefaultTableModel) tblhuyhoadon.getModel();

        try {
            // Clear previous data
            modelHoadon.setRowCount(0);
            modelHuyhoadon.setRowCount(0);

            // Load data for tblhoadon
            ResultSet rs1 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 1");
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

            // Load data for tblhuyhoadon
            ResultSet rs2 = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 0");
            while (rs2.next()) {
                modelHuyhoadon.addRow(new Object[]{
                    rs2.getInt("ID_Hoadon"),
                    rs2.getString("ID_Nhanvien"),
                    rs2.getDate("Ngaytao"),
                    rs2.getInt("Thanhtien"),
                    rs2.getString("Lydohuy"),
                    rs2.getInt("Soluongsanphamhuy"),
                    rs2.getString("ghichu"),
                    rs2.getString("SDT"),
                    rs2.getString("Ten"),
                    rs2.getString("diaChi"),
                    rs2.getInt("tenShip")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertDateFormat(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(dateStr);

            // Initialize dateFormatter with the desired output format
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

            return dateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblhoadon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblhuyhoadon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtHD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtfind_mnv = new javax.swing.JTextField();
        btn_lammoi = new javax.swing.JButton();
        cbotrangthai = new javax.swing.JComboBox<>();
        txttungay = new javax.swing.JTextField();
        txtdenngay = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(241, 241, 241));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tblhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Trạng thái", "Thanh toán", "Người tạo", "Thành tiền", "Số Lượng SP bị hủy", "SDT"
            }
        ));
        jScrollPane2.setViewportView(tblhoadon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(241, 241, 241));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hóa đơn hủy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tblhuyhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Trạng thái", "Thanh toán", "Người tạo", "Thành tiền", "Số Lượng SP bị hủy", "SDT"
            }
        ));
        jScrollPane4.setViewportView(tblhuyhoadon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(241, 241, 241));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Từ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã hóa đơn");

        txtHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHDKeyReleased(evt);
            }
        });

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

        txtfind_mnv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfind_mnvKeyReleased(evt);
            }
        });

        btn_lammoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_lammoi.setText("Làm Mới");
        btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiActionPerformed(evt);
            }
        });

        cbotrangthai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbotrangthaiItemStateChanged(evt);
            }
        });
        cbotrangthai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbotrangthaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtfind_mnv, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(125, 125, 125)
                                .addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtHD, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(408, 408, 408)
                                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txttungay, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(jLabel3)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbotrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdenngay, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addComponent(txtdenngay, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txttungay, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(btnTim))
                    .addComponent(cbotrangthai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfind_mnv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_lammoi)))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHDKeyReleased

    }//GEN-LAST:event_txtHDKeyReleased

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        loadData();
    }//GEN-LAST:event_btnTimActionPerformed

    private void txtfind_mnvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfind_mnvKeyReleased

    }//GEN-LAST:event_txtfind_mnvKeyReleased

    private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiActionPerformed
        txtHD.setText("");
        txtfind_mnv.setText("");
        txttungay.setText("");
        txtdenngay.setText("");
        cbotrangthai.setSelectedIndex(0); // Reset combo box to the first item (if applicable)

        // Reload data
        loadData();
    }//GEN-LAST:event_btn_lammoiActionPerformed

    private void cbotrangthaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbotrangthaiItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedStatus = (String) cbotrangthai.getSelectedItem();

            // Clear previous data
            hoadonModel.setRowCount(0);
            huyhoadonModel.setRowCount(0);

            ResultSet rs = null;
            try {
                if ("All".equals(selectedStatus)) {
                    // Load all data
                    rs = ConnectUtil.query("SELECT * FROM HoaDon");
                } else if ("Active".equals(selectedStatus)) {
                    // Load only active invoices (Trangthai = 1)
                    rs = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 1");
                } else if ("Canceled".equals(selectedStatus)) {
                    // Load only canceled invoices (Trangthai = 0)
                    rs = ConnectUtil.query("SELECT * FROM HoaDon WHERE Trangthai = 0");
                } else {
                    // Default case, handle more statuses if needed
                    rs = ConnectUtil.query("SELECT * FROM HoaDon");
                }

                while (rs.next()) {
                    hoadonModel.addRow(new Object[]{
                        rs.getInt("ID_Hoadon"),
                        rs.getString("ID_Nhanvien"),
                        rs.getDate("Ngaytao"),
                        rs.getInt("Thanhtien"),
                        rs.getString("Lydohuy"),
                        rs.getInt("Soluongsanphamhuy"),
                        rs.getString("ghichu"),
                        rs.getString("SDT"),
                        rs.getString("Ten"),
                        rs.getString("diaChi"),
                        rs.getInt("tenShip")
                    });
                }
            } catch (SQLException e) {
                Logger.getLogger(QuanLyHoaDonJPanel.class.getName()).log(Level.SEVERE, null, e);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyHoaDonJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        Logger.getLogger(QuanLyHoaDonJPanel.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }
    }//GEN-LAST:event_cbotrangthaiItemStateChanged

    private void cbotrangthaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbotrangthaiActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbotrangthaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btn_lammoi;
    private javax.swing.JComboBox<String> cbotrangthai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblhoadon;
    private javax.swing.JTable tblhuyhoadon;
    private javax.swing.JTextField txtHD;
    private javax.swing.JTextField txtdenngay;
    private javax.swing.JTextField txtfind_mnv;
    private javax.swing.JTextField txttungay;
    // End of variables declaration//GEN-END:variables
}
