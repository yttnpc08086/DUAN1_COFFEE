package views;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Helper.ConnectUtil;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QuanLyBanHangJPanel extends javax.swing.JPanel {

    private CardLayout cardLayout;
    private JPanel containerPanel;

    public QuanLyBanHangJPanel(String url, String username, String password) {
        initComponents();
        loadData();

        tblBan.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblBan.getSelectedRow() != -1) {
                displayBanDetails();
            }
        });
//        cboLoaisanpham.addItemListener(evt -> filltableSanPham());
        txtTimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
//                filterTableByProduct();
            }
        });

        tblHoadon.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblHoadon.getSelectedRow() != -1) {
                displayHoaDonDetails();
            }
        });

        tblHoadonchitiet.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblHoadonchitiet.getSelectedRow() != -1) {
                displayHoaDonChiTietDetails();
            }
        });

        tblSanPham.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblSanPham.getSelectedRow() != -1) {
//                displaySanPhamDetails();
            }
        });

        tblSize.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblSize.getSelectedRow() != -1) {
//                displaySizeDetails();
            }
        });

        tblBan.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblBan.getSelectedRow() != -1) {
//                displaySizeDetails();
            }
        });
        btnDonViDouong.addActionListener(e -> openQLDonViDoUongDialog());
        btnThemDo.addActionListener(e -> openQLDoUongDialog());
//        btnThanhToan.addActionListener(e ->  openChucNangThanhToanJDialog());

        txttienKhachTra.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTienThoi();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTienThoi();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTienThoi();
            }
        });

        // Giả sử bạn đã có một nút thanh toán trong JPanel
        JButton btnThanhToan = new JButton("Thanh Toán");

        // Thêm trình xử lý sự kiện cho nút thanh toán
        btnThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private void updateTienThoi() {
        try {
            // Get the values from the text fields
            String tongTienText = txtTongTien.getText().trim();
            String tienKhachTraText = txttienKhachTra.getText().trim();

            // Parse the values as doubles
            double tongTien = !tongTienText.isEmpty() ? Double.parseDouble(tongTienText) : 0;
            double tienKhachTra = !tienKhachTraText.isEmpty() ? Double.parseDouble(tienKhachTraText) : 0;

            // Calculate the total
            double tienThoi = tienKhachTra - tongTien;

            // Set the result to txttienThoi
            txttienThoi.setText(String.format("%.2f", tienThoi));
        } catch (NumberFormatException e) {
            // Handle invalid number format
            txttienThoi.setText("0.00");
        }
    }

    private void openQLDonViDoUongDialog() {
        // Tạo và hiển thị cửa sổ QLDonViDoUongJDialog
        QLDonViDoUongJDialog dialog = new QLDonViDoUongJDialog((JFrame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);
    }

    private void openQLDoUongDialog() {
        QLDoUongJDialog dialog = new QLDoUongJDialog((JFrame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);
    }

//    private void openChucNangThanhToanJDialog() {
//        ChucNangThanhToanJDialog dialog = new ChucNangThanhToanJDialog((JFrame) SwingUtilities.getWindowAncestor(this), true);
//        dialog.setVisible(true);
//    }
//    private void openChucNangThanhToanDialog(JFrame parent) {
//        ChucNangThanhToanJDialog dialog = new ChucNangThanhToanJDialog(parent, true);
//
//        // Set data for the dialog
//        dialog.setMaHoaDon(lblMaHoaDon.getText());
//        dialog.setTongTien(txtTongTien.getText());
//        dialog.setTienKhachTra(txttienKhachTra.getText());
//        dialog.setTienThoi(txttienThoi.getText());
//        dialog.setSoluong(txtsoluong.getText());
//        dialog.setBanso(txtBanso.getText());
//        dialog.setTrangthai(txttrangthai.getText());
//        dialog.setHoatdong(txthoatdong.getText());
//        dialog.setNgay(lblNgay.getText());
//        dialog.setHientennhanvien(txthientennhanvien.getText());
//        dialog.setBan(lblBan.getText());
//
//        dialog.setVisible(true);
//    }
    private void loadData() {
        loadHoaDonData();
        loadHoaDonChiTietData();
        loadSanPhamData();
        loadSizeData();
        loadBanData();
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

    private void loadSanPhamData() {
        String sql = "SELECT * FROM SanPham";
        loadTableData(sql, tblSanPham, new String[]{"ID_SanPham", "TenSP", "ID_LoaiSP", "Gia"});
    }

    private void loadSizeData() {
        String sql = "SELECT * FROM DonViSanPham";
        loadTableData(sql, tblSize, new String[]{"TenDonvi", "ThemTien"});
    }

    private void loadBanData() {
        String sql = "SELECT * FROM Ban";
        loadTableData(sql, tblBan, new String[]{"ID_Ban", "Trangthai", "Hoatdong", "Soluongcho"});
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

    private void displayBanDetails() {
        int selectedRow = tblBan.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblBan.getModel();
            txtBanso.setText(model.getValueAt(selectedRow, 0).toString());
            txttrangthai.setText(model.getValueAt(selectedRow, 1).toString());
            txthoatdong.setText(model.getValueAt(selectedRow, 2).toString());
            txtsoluong.setText(model.getValueAt(selectedRow, 3).toString());
        }
    }

    private void displayHoaDonDetails() {
        int selectedRow = tblHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
            lblMaHoaDon.setText(model.getValueAt(selectedRow, 0).toString());
            lblBan.setText(model.getValueAt(selectedRow, 8).toString());
            txtTongTien.setText(model.getValueAt(selectedRow, 4).toString());
            lblNgay.setText(model.getValueAt(selectedRow, 2).toString());
            txthientennhanvien.setText(model.getValueAt(selectedRow, 1).toString());
        }
    }

    private void displayHoaDonChiTietDetails() {
        int selectedRow = tblHoadonchitiet.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblHoadonchitiet.getModel();
            lblMaHoaDon.setText(model.getValueAt(selectedRow, 0).toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmnHoadonchitiet = new javax.swing.JPopupMenu();
        btnxoaHDchitiet = new javax.swing.JMenuItem();
        mnSuaSl = new javax.swing.JMenuItem();
        pmnHoadonctt = new javax.swing.JPopupMenu();
        mnChuyenban = new javax.swing.JMenuItem();
        mnHuyDon = new javax.swing.JMenuItem();
        mnTachDon = new javax.swing.JMenuItem();
        mnGopDon = new javax.swing.JMenuItem();
        mnSuaThongtinkhach = new javax.swing.JMenuItem();
        pmnBan = new javax.swing.JPopupMenu();
        mnXoaDan = new javax.swing.JMenuItem();
        mnSua = new javax.swing.JMenuItem();
        mnNhomBan = new javax.swing.JMenuItem();
        mnGopBan = new javax.swing.JMenuItem();
        pnBan = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBan = new javax.swing.JTable();
        pnHoadon = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        lblNgay = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txttienKhachTra = new javax.swing.JTextField();
        txttienThoi = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        btnHuydon = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txthientennhanvien = new javax.swing.JLabel();
        btnTaoDon = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoadon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoadonchitiet = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        lblBan = new javax.swing.JLabel();
        btnIN = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnDonViDouong = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSize = new javax.swing.JTable();
        btnThemDo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        pnTaiQuay = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBanso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txttrangthai = new javax.swing.JTextField();
        txthoatdong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtsoluong = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();

        btnxoaHDchitiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnxoaHDchitiet.setText("Xóa sản phẩm");
        btnxoaHDchitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaHDchitietActionPerformed(evt);
            }
        });
        pmnHoadonchitiet.add(btnxoaHDchitiet);

        mnSuaSl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnSuaSl.setText("Sửa số lượng");
        mnSuaSl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSuaSlActionPerformed(evt);
            }
        });
        pmnHoadonchitiet.add(mnSuaSl);

        mnChuyenban.setText("Chuyển bàn");
        mnChuyenban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChuyenbanActionPerformed(evt);
            }
        });
        pmnHoadonctt.add(mnChuyenban);

        mnHuyDon.setText("Hủy Đơn");
        mnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHuyDonActionPerformed(evt);
            }
        });
        pmnHoadonctt.add(mnHuyDon);

        mnTachDon.setText("Tách đơn");
        mnTachDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTachDonActionPerformed(evt);
            }
        });
        pmnHoadonctt.add(mnTachDon);

        mnGopDon.setText("Gộp tới");
        mnGopDon.setToolTipText("");
        mnGopDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGopDonActionPerformed(evt);
            }
        });
        pmnHoadonctt.add(mnGopDon);

        mnSuaThongtinkhach.setText("Sửa thông tin khách hàng");
        mnSuaThongtinkhach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSuaThongtinkhachActionPerformed(evt);
            }
        });
        pmnHoadonctt.add(mnSuaThongtinkhach);

        mnXoaDan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnXoaDan.setText("Xoá bàn");
        mnXoaDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnXoaDanActionPerformed(evt);
            }
        });
        pmnBan.add(mnXoaDan);

        mnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnSua.setText("Sửa");
        mnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSuaActionPerformed(evt);
            }
        });
        pmnBan.add(mnSua);

        mnNhomBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnNhomBan.setText("Nhóm Bàn");
        mnNhomBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNhomBanActionPerformed(evt);
            }
        });
        pmnBan.add(mnNhomBan);

        mnGopBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        mnGopBan.setText("Gộp bàn tới");
        mnGopBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGopBanActionPerformed(evt);
            }
        });
        pmnBan.add(mnGopBan);

        setBackground(new java.awt.Color(241, 241, 241));

        pnBan.setBackground(new java.awt.Color(241, 241, 241));
        pnBan.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnBanMouseReleased(evt);
            }
        });

        tblBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bàn", "Trạng thái", "Hoạt động", "Số lương"
            }
        ));
        jScrollPane6.setViewportView(tblBan);
        if (tblBan.getColumnModel().getColumnCount() > 0) {
            tblBan.getColumnModel().getColumn(3).setHeaderValue("Giá giảm (VND)");
        }

        javax.swing.GroupLayout pnBanLayout = new javax.swing.GroupLayout(pnBan);
        pnBan.setLayout(pnBanLayout);
        pnBanLayout.setHorizontalGroup(
            pnBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnBanLayout.setVerticalGroup(
            pnBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnHoadon.setBackground(new java.awt.Color(241, 241, 241));
        pnHoadon.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setText("Mã Hóa Đơn: ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setText("Ngày:");

        lblMaHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaHoaDon.setText("......");

        lblNgay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNgay.setText("......");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel16.setText("Tổng tiền:");

        txtTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongTien.setText("0");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel17.setText("Tiền khách trả");

        txttienKhachTra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttienKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttienKhachTraKeyReleased(evt);
            }
        });

        txttienThoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttienThoi.setText("0");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setText("Tiền thối:");

        btnThanhToan.setBackground(new java.awt.Color(0, 255, 0));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnHuydon.setBackground(new java.awt.Color(255, 51, 0));
        btnHuydon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuydon.setForeground(new java.awt.Color(255, 255, 255));
        btnHuydon.setText("Hủy đơn");
        btnHuydon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuydonActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel20.setText("Nhân viên:");

        txthientennhanvien.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txthientennhanvien.setText("..............................");

        btnTaoDon.setBackground(new java.awt.Color(0, 255, 0));
        btnTaoDon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnTaoDon.setText("Tạo đơn");
        btnTaoDon.setToolTipText("");
        btnTaoDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoDonActionPerformed(evt);
            }
        });

        tblHoadon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoadonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblHoadonMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoadon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        tabs.addTab("Hóa đơn", jPanel2);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("Hóa đơn chi tiết", jPanel3);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel19.setText("Bàn:");

        lblBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBan.setText("......");

        btnIN.setBackground(new java.awt.Color(255, 204, 204));
        btnIN.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIN.setText("In");
        btnIN.setToolTipText("");
        btnIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnINActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnHoadonLayout = new javax.swing.GroupLayout(pnHoadon);
        pnHoadon.setLayout(pnHoadonLayout);
        pnHoadonLayout.setHorizontalGroup(
            pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabs)
                    .addGroup(pnHoadonLayout.createSequentialGroup()
                        .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnHoadonLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txthientennhanvien))
                            .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnHoadonLayout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(18, 18, 18)
                                    .addComponent(txttienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(173, 173, 173)
                                    .addComponent(btnIN, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnHoadonLayout.createSequentialGroup()
                                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnHoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18))
                                        .addGroup(pnHoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnHoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblBan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnHuydon, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnHoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(20, 20, 20)
                                            .addComponent(txttienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(39, 39, 39)
                                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTaoDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnHoadonLayout.setVerticalGroup(
            pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoadonLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblBan))
                        .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(lblMaHoaDon)))
                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHuydon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txttienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(lblNgay)
                        .addComponent(btnIN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(txttienThoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(pnHoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txthientennhanvien))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(241, 241, 241));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        txtTimkiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Tìm kiếm(theo tên)");

        btnDonViDouong.setBackground(new java.awt.Color(0, 255, 0));
        btnDonViDouong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDonViDouong.setText("Chọn Đơn vị đồ uống");
        btnDonViDouong.setBorder(null);
        btnDonViDouong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDonViDouongMouseClicked(evt);
            }
        });
        btnDonViDouong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDonViDouongActionPerformed(evt);
            }
        });

        tblSize.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSize.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Size", "Giá upsize"
            }
        ));
        jScrollPane5.setViewportView(tblSize);

        btnThemDo.setBackground(new java.awt.Color(0, 255, 0));
        btnThemDo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemDo.setText("Chọn đồ uống");
        btnThemDo.setBorder(null);
        btnThemDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDoMouseClicked(evt);
            }
        });
        btnThemDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDoActionPerformed(evt);
            }
        });

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản phẩm", "Tên sản phẩm", "Loại", "Giá giảm(VND)", "Giá gốc (VND)"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(261, 261, 261)
                        .addComponent(btnThemDo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDonViDouong)))
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDonViDouong, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemDo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pnTaiQuay.setBackground(new java.awt.Color(241, 241, 241));
        pnTaiQuay.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tại quầy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnTaiQuay.setPreferredSize(new java.awt.Dimension(440, 100));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        jPanel4.setForeground(new java.awt.Color(255, 102, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Bàn số:");

        txtBanso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setText("Số lương khách");

        btnthem.setBackground(new java.awt.Color(250, 182, 124));
        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Hoạt động");

        txttrangthai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txthoatdong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Trạng Thái");

        btnSua.setBackground(new java.awt.Color(250, 182, 124));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnxoa.setBackground(new java.awt.Color(250, 182, 124));
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txttrangthai)
                            .addComponent(txtBanso)
                            .addComponent(txthoatdong, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addComponent(txtsoluong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBanso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txttrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txthoatdong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnTaiQuayLayout = new javax.swing.GroupLayout(pnTaiQuay);
        pnTaiQuay.setLayout(pnTaiQuayLayout);
        pnTaiQuayLayout.setHorizontalGroup(
            pnTaiQuayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTaiQuayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnTaiQuayLayout.setVerticalGroup(
            pnTaiQuayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnHoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnHoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnTaiQuay, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDonViDouongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDonViDouongActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_btnDonViDouongActionPerformed

    private void pnBanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnBanMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_pnBanMouseReleased
    private void filterTableByProductCode(String productCode) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        tblSanPham.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter("(?i)" + productCode, 1));
    }

    private void txtTimkiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimkiemKeyReleased
        // TODO add your handling code here:
        String searchText = txtTimkiem.getText().trim();
        filterTableByProductCode(searchText);
    }//GEN-LAST:event_txtTimkiemKeyReleased

    private void btnTaoDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaoDonActionPerformed

    private void deleteHoaDon(String idHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE ID_Hoadon = ?";
        try {
            ConnectUtil.update(sql, idHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể hủy hóa đơn.");
        }
    }

    private void deleteHoaDonChiTiet(String idHoaDon) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE ID_HoaDon = ?";
        try {
            ConnectUtil.update(sql, idHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể hủy chi tiết hóa đơn.");
        }
    }

    private void btnHuydonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuydonActionPerformed
        // TODO add your handling code here: 
        int selectedRow = tblHoadon.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblHoadon.getModel();
            String idHoaDon = model.getValueAt(selectedRow, 0).toString();

            int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đơn này?", "Xác nhận", javax.swing.JOptionPane.YES_NO_OPTION);
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                deleteHoaDon(idHoaDon);
                deleteHoaDonChiTiet(idHoaDon);

                loadHoaDonData();
                loadHoaDonChiTietData();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần hủy.");
        }
    }//GEN-LAST:event_btnHuydonActionPerformed

    private void tblHoadonchitietMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonchitietMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonchitietMouseReleased

    private void tblHoadonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonMouseReleased

    private void txttienKhachTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttienKhachTraKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txttienKhachTraKeyReleased

//    private boolean isDataValid() {
//        return !txtBanso.getText().trim().isEmpty()
//                && !txttrangthai.getText().trim().isEmpty()
//                && !txthoatdong.getText().trim().isEmpty()
//                && !txtsoluong.getText().trim().isEmpty()
//                && !txtTongTien.getText().trim().isEmpty()
//                && !txttienKhachTra.getText().trim().isEmpty();
//    }

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (txtBanso.getText().trim().isEmpty()
                || txtTongTien.getText().trim().isEmpty()
                || txttienKhachTra.getText().trim().isEmpty()
                || txttienThoi.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        // Create a new instance of the panel
        ChucNangThanhToanJPanel thanhToanPanel = new ChucNangThanhToanJPanel();

        // Set data to the panel using setter methods
        thanhToanPanel.setMaHoaDon(lblMaHoaDon.getText());
        thanhToanPanel.setTongTien(txtTongTien.getText());
        thanhToanPanel.setTienKhachTra(txttienKhachTra.getText());
        thanhToanPanel.setTienThoi(txttienThoi.getText());
        thanhToanPanel.setSoluong(txtsoluong.getText());
        thanhToanPanel.setBanso(txtBanso.getText());
        thanhToanPanel.setTrangthai(txttrangthai.getText());
        thanhToanPanel.setHoatdong(txthoatdong.getText());
        thanhToanPanel.setNgay(lblNgay.getText());
        thanhToanPanel.setHientennhanvien(txthientennhanvien.getText());
        thanhToanPanel.setBan(lblBan.getText());

        // Create a new frame to contain the panel
        JFrame frame = new JFrame("Chức Năng Thanh Toán");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(thanhToanPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setAlwaysOnTop(true); // Ensure the frame is always on top
        frame.setVisible(true);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void mnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHuyDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnHuyDonActionPerformed

    private void mnChuyenbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChuyenbanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnChuyenbanActionPerformed

    private void btnxoaHDchitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaHDchitietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxoaHDchitietActionPerformed

    private void mnXoaDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnXoaDanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnXoaDanActionPerformed

    private void mnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnSuaActionPerformed

    private void tblHoadonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoadonMousePressed

    private void mnNhomBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNhomBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnNhomBanActionPerformed

    private void mnTachDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTachDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnTachDonActionPerformed

    private void mnGopDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGopDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnGopDonActionPerformed

    private void mnGopBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGopBanActionPerformed

    }//GEN-LAST:event_mnGopBanActionPerformed

    private void btnINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnINActionPerformed
        // TODO add your handling code here:
        exportToExcel();
    }//GEN-LAST:event_btnINActionPerformed

    private void exportToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetHoadon = workbook.createSheet("Hoadon");
        Sheet sheetHoadonChitiet = workbook.createSheet("HoadonChitiet");

        exportTableToSheet(tblHoadon, sheetHoadon);

        exportTableToSheet(tblHoadonchitiet, sheetHoadonChitiet);

        try (FileOutputStream fileOut = new FileOutputStream("Hoadon_HoadonChitiet.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            javax.swing.JOptionPane.showMessageDialog(this, "Xuất dữ liệu ra Excel thành công.");
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất dữ liệu ra Excel.");
        }
    }

    private void exportTableToSheet(javax.swing.JTable table, Sheet sheet) {
        TableModel model = table.getModel();

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(model.getColumnName(i));
        }

        for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            for (int colIndex = 0; colIndex < model.getColumnCount(); colIndex++) {
                Cell cell = row.createCell(colIndex);
                Object value = model.getValueAt(rowIndex, colIndex);
                if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }
    }

    private void mnSuaSlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSuaSlActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_mnSuaSlActionPerformed

    private void mnSuaThongtinkhachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSuaThongtinkhachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnSuaThongtinkhachActionPerformed

    private boolean insertBan(String banSo, String trangThai, String hoatDong, int soLuongCho) {
        String sql = "INSERT INTO Ban (ID_Ban, Trangthai, Hoatdong, Soluongcho) VALUES (?, ?, ?, ?)";
        try {
            ConnectUtil.update(sql, banSo, trangThai, hoatDong, soLuongCho);
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm bàn mới thành công.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể thêm bàn mới.");
            return false;
        }
    }


    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        String banSo = txtBanso.getText().trim();
        String trangThai = txttrangthai.getText().trim();
        String hoatDong = txthoatdong.getText().trim();
        String soLuongCho = txtsoluong.getText().trim();

        // Validate input data
        if (banSo.isEmpty() || trangThai.isEmpty() || hoatDong.isEmpty() || soLuongCho.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            int soLuongChoInt = Integer.parseInt(soLuongCho);
            // Insert new Ban into the database
            boolean success = insertBan(banSo, trangThai, hoatDong, soLuongChoInt);

            // If insertion was successful, clear the text fields and refresh the table
            if (success) {
                txtBanso.setText("");
                txttrangthai.setText("");
                txthoatdong.setText("");
                txtsoluong.setText("");
                loadBanData();
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Số lượng chỗ phải là một số.");
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private boolean isBanExists(String banSo) {
        String sql = "SELECT COUNT(*) FROM Ban WHERE ID_Ban = ?";
        try (ResultSet rs = ConnectUtil.query(sql, banSo)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateBan(String banSo, String trangThai, String hoatDong, int soLuongCho) {
        // Check if ID_Ban exists
        if (!isBanExists(banSo)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Mã bàn không tồn tại.");
            return false;
        }

        String sql = "UPDATE Ban SET Trangthai = ?, Hoatdong = ?, Soluongcho = ? WHERE ID_Ban = ?";
        try {
            ConnectUtil.update(sql, trangThai, hoatDong, soLuongCho, banSo);
            javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể cập nhật bàn.");
            return false;
        }
    }


    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        String banSo = txtBanso.getText().trim();
        String trangThai = txttrangthai.getText().trim();
        String hoatDong = txthoatdong.getText().trim();
        String soLuongCho = txtsoluong.getText().trim();

        // Validate input data
        if (banSo.isEmpty() || trangThai.isEmpty() || hoatDong.isEmpty() || soLuongCho.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            int soLuongChoInt = Integer.parseInt(soLuongCho);
            // Update Ban in the database
            boolean success = updateBan(banSo, trangThai, hoatDong, soLuongChoInt);

            // If update was successful, clear the text fields and refresh the table
            if (success) {
                txtBanso.setText("");
                txttrangthai.setText("");
                txthoatdong.setText("");
                txtsoluong.setText("");
                loadBanData();
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Số lượng chỗ phải là một số.");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private boolean deleteBan(String banSo) {
        String sql = "DELETE FROM Ban WHERE ID_Ban = ?";
        try {
            ConnectUtil.update(sql, banSo);
            javax.swing.JOptionPane.showMessageDialog(this, "Xóa bàn thành công.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể xóa bàn.");
            return false;
        }
    }

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblBan.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần xóa.");
            return;
        }

        // Get the ID_Ban from the selected row
        Object value = tblBan.getValueAt(selectedRow, 0);
        String banSo = (value instanceof Integer) ? value.toString() : (String) value;

        // Confirm deletion
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bàn này?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            // Delete the Ban from the database
            boolean success = deleteBan(banSo);

            // If delete was successful, refresh the table
            if (success) {
                txtBanso.setText("");
                txttrangthai.setText("");
                txthoatdong.setText("");
                txtsoluong.setText("");
                loadBanData();
            }
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnDonViDouongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDonViDouongMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDonViDouongMouseClicked

    private void btnThemDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemDoMouseClicked

    private void btnThemDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemDoActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDonViDouong;
    private javax.swing.JButton btnHuydon;
    private javax.swing.JButton btnIN;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemDo;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JMenuItem btnxoaHDchitiet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblNgay;
    private javax.swing.JMenuItem mnChuyenban;
    private javax.swing.JMenuItem mnGopBan;
    private javax.swing.JMenuItem mnGopDon;
    private javax.swing.JMenuItem mnHuyDon;
    private javax.swing.JMenuItem mnNhomBan;
    private javax.swing.JMenuItem mnSua;
    private javax.swing.JMenuItem mnSuaSl;
    private javax.swing.JMenuItem mnSuaThongtinkhach;
    private javax.swing.JMenuItem mnTachDon;
    private javax.swing.JMenuItem mnXoaDan;
    private javax.swing.JPopupMenu pmnBan;
    private javax.swing.JPopupMenu pmnHoadonchitiet;
    private javax.swing.JPopupMenu pmnHoadonctt;
    private javax.swing.JPanel pnBan;
    private javax.swing.JPanel pnHoadon;
    private javax.swing.JPanel pnTaiQuay;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblBan;
    private javax.swing.JTable tblHoadon;
    private javax.swing.JTable tblHoadonchitiet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSize;
    private javax.swing.JTextField txtBanso;
    private javax.swing.JTextField txtTimkiem;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JLabel txthientennhanvien;
    private javax.swing.JTextField txthoatdong;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txttienKhachTra;
    private javax.swing.JTextField txttienThoi;
    private javax.swing.JTextField txttrangthai;
    // End of variables declaration//GEN-END:variables

}
