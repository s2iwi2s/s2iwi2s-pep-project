package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDao {

    public Account getAccountByUsername(String username) throws SQLException {
        Account account = null;
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "select * from account where username = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                account = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return account;
    }

    public Account createAccount(Account account) throws SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            int ret = pstmt.executeUpdate();
            System.out.println("Account created: " + ret);
        } catch (SQLException e) {
            throw e;
        }
        
        return this.getAccountByUsername(account.getUsername());
    }

}
