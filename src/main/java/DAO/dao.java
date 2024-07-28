/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ASUS
 */
import Model.NhanVien;
import java.sql.*;

public class dao {
    private static final String URL = "jdbc:sqlserver://localhost;databaseName=COFFEE";
    private static final String USER = "sa";
    private static final String PASSWORD = "20190400";
    
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public NhanVien selectByAccount(String username) {
        String sql = "SELECT * FROM NhanVien WHERE Username = ?";
        try (Connection conn = connect(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new NhanVien(
                        rs.getString("ID_Nhanvien"),
                        rs.getString("TenNV"),
                        rs.getBoolean("GioiTinh"),
                        rs.getDate("Ngaysinh"),
                        rs.getString("Diachi"),
                        rs.getString("Email"),
                        rs.getString("SDT"),
                        rs.getString("Username"),
                        rs.getString("Pass"),
                        rs.getBoolean("Vaitro"),
                        rs.getBoolean("Trangthai"),
                        rs.getString("Hinh")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (log it, rethrow it, or show a message to the user)
        }
        return null;
    }
}
