package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDao {

    private static MessageDao messageDao = null;

    private MessageDao() {

    }

    public static MessageDao getInstance() {
        if(messageDao == null) {
            messageDao = new MessageDao();
        }

        return messageDao;
    }

    public Message createMessage(Message message) throws SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values(?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());
            int rows = pstmt.executeUpdate();
            System.out.println("Message created: " + rows + " row/s");
        } catch (SQLException e) {
            throw e;
        }
        Message ret = getMessageByMessageText(message.getMessage_text());
        return ret;
    }

    private Message getMessageByMessageText(String messageText) throws SQLException {
        Message ret = null;
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "select * from message where message_text=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, messageText);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public List<Message> getAllMessage() throws Exception {
        List<Message> list = new ArrayList<>();
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "select * from message";
            Statement pstmt = con.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                    list.add(msg);
            }
        } catch (Exception e) {
            throw e;
        }
        return list;
    }

    public Message getMessageById(int id) throws SQLException {
        Message msg = null;
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "select * from message where message_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            throw e;
        }

        return msg;
    }

    public Message patchMessage(int id, Message message) throws SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, message.getMessage_text());
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Message updated: " + rows + " row/s");
        } catch (SQLException e) {
            throw e;
        }
        return this.getMessageById(id);
    }

    public void deleteMessageById(int id) throws SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "delete message where message_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Message updated: " + rows + " row/s");
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Message> getAllMessagesFromUser(int accountId) throws SQLException {
        List<Message> list = new ArrayList<>();
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "select * from message where posted_by = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                    list.add(msg);
            }
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }

    

}
