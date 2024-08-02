package DAO;

import Model.NhanVien;
import Helper.ConnectUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class NhanVienDAO implements InterfaceNhanVien {

    private String INSERT_SQL = "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    private String UPDATE_SQL = "UPDATE NhanVien SET TenNV = ?, GioiTinh = ?, Ngaysinh = ?, Diachi = ?, Email = ?, SDT = ?, Username = ?, Pass = ?, Vaitro = ?, Trangthai = ?, Hinh = ? WHERE ID_Nhanvien = ?";
    private String DELETE_SQL = "UPDATE NhanVien SET Trangthai = 0 WHERE ID_Nhanvien = ?";
    private String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    private String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE ID_Nhanvien = ?";
    private String SELECT_BY_ACCOUNT_SQL = "SELECT * FROM NhanVien WHERE Username = ?";
    private String SELECT_TRANG_THAI_1 = "SELECT * FROM NhanVien WHERE Trangthai = 0";
    private String SELECT_TRANG_THAI_2 = "SELECT * FROM NhanVien WHERE Trangthai = 1";
    private String UPDATE_PASS_SQL = "UPDATE NhanVien SET Pass = ? WHERE Email = ?";

    public void insert(NhanVien entity) {
        ConnectUtil.update(INSERT_SQL,
                entity.getId_Nhanvien(),
                entity.getTenNV(),
                entity.isGender(),
                entity.getNgaysinh(),
                entity.getDiaChi(),
                entity.getEmail(),
                entity.getSDT(),
                entity.getUserName(),
                entity.getPass(),
                entity.isVaiTro(),
                entity.isTrangThai(),
                entity.getHinh());
    }

    public void update(NhanVien entity) {
        ConnectUtil.update(UPDATE_SQL,
                entity.getTenNV(),
                entity.isGender(),
                entity.getNgaysinh(),
                entity.getDiaChi(),
                entity.getEmail(),
                entity.getSDT(),
                entity.getUserName(),
                entity.getPass(),
                entity.isVaiTro(),
                entity.isTrangThai(),
                entity.getHinh(),
                entity.getId_Nhanvien());
    }

    public void delete(String id) {
        ConnectUtil.update(DELETE_SQL, id);
    }

    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NhanVien selectByAccount(String username) {
        List<NhanVien> list = selectBySql(SELECT_BY_ACCOUNT_SQL, username);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectUtil.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setId_Nhanvien(rs.getString("ID_Nhanvien"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setGender(rs.getBoolean("GioiTinh"));
                entity.setNgaysinh(rs.getDate("Ngaysinh"));
                entity.setDiaChi(rs.getString("Diachi"));
                entity.setEmail(rs.getString("Email"));
                entity.setSDT(rs.getString("SDT"));
                entity.setUserName(rs.getString("Username"));
                entity.setPass(rs.getString("Pass"));
                entity.setVaiTro(rs.getBoolean("Vaitro"));
                entity.setTrangThai(rs.getBoolean("Trangthai"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException("Error executing SQL query: " + sql, e);
        }
        return list;
    }

    public void updatePassword(String code, String email) {
        ConnectUtil.update(UPDATE_PASS_SQL, code, email);
    }

    public List<NhanVien> selectByTrangThai(int trangThai) {
        String sql = trangThai == 1 ? SELECT_TRANG_THAI_2 : SELECT_TRANG_THAI_1;
        return selectBySql(sql);
    }

    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ? AND Trangthai = 1";
        return selectBySql(sql, "%" + keyword + "%");
    }

    public int selectMaxId() {
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(ID_Nhanvien, 3, LEN(ID_Nhanvien)) AS INT)) FROM NhanVien";
            ResultSet rs = ConnectUtil.query(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String selectNameStaff() {
        try {
            String sql = "SELECT TOP 1 ID_Nhanvien FROM NhanVien";
            ResultSet rs = ConnectUtil.query(sql);
            if (rs.next()) {
                return rs.getString("ID_Nhanvien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectByEmail(String email) {
        try {
            String sql = "SELECT Email FROM NhanVien WHERE Email = ?";
            ResultSet rs = ConnectUtil.query(sql, email);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> selectName() {
        List<NhanVien> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM NhanVien WHERE TrangThai = '1' ORDER BY TenNV;";
            ResultSet rs = ConnectUtil.query(sql);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId_Nhanvien(rs.getString("iD_Nhanvien")); // Sửa lại tên cột phù hợp với cơ sở dữ liệu của bạn
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGender(rs.getBoolean("GioiTinh"));
                nv.setNgaysinh(rs.getDate("Ngaysinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setEmail(rs.getString("Email"));
                nv.setVaiTro(rs.getBoolean("Vaitro"));
                nv.setHinh(rs.getString("Hinh"));
                nv.setTrangThai(rs.getBoolean("Trangthai"));
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
