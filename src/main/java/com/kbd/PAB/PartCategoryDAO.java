package com.kbd.PAB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PartCategoryDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public PartCategoryDAO() {
        try {
            String dbURL = "jdbc:mysql://125.180.23.159:3306/FP";
            String dbID = "root";
            String dbPassword = "root";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCartegory(){
        String SQL = "select * from pab";
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
