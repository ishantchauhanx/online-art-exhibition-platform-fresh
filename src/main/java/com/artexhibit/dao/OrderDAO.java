package com.artexhibit.dao;

import com.artexhibit.config.DBConnection;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void placeOrder(int buyerId, int artworkId, BigDecimal amount) throws DatabaseException {
        String sql = "INSERT INTO orders (buyer_id, artwork_id, amount, payment_status) VALUES (?, ?, ?, 'SUCCESS')";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            ps.setInt(2, artworkId);
            ps.setBigDecimal(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error placing order.", e);
        }
    }

    public List<Order> getOrdersByBuyer(int buyerId) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE buyer_id = ? ORDER BY order_date DESC";
        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, buyerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching buyer orders.", e);
        }
        return orders;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setBuyerId(rs.getInt("buyer_id"));
        order.setArtworkId(rs.getInt("artwork_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setAmount(rs.getBigDecimal("amount"));
        order.setPaymentStatus(rs.getString("payment_status"));
        return order;
    }
}
