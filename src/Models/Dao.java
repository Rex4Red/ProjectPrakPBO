/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author aliad
 */
public class Dao {

    Connection connection = Connector.connection();
    PreparedStatement prepStatement;
    Statement statement;
    ResultSet result;

    public boolean insertProduct(Product product) {
        String insertQuery = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        
        try {
            prepStatement = connection.prepareStatement(insertQuery);
            prepStatement.setString(1, product.getName());
            prepStatement.setDouble(2, product.getPrice());
            prepStatement.setBytes(3, product.getImage());

            return prepStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public void getAllProduct(JTable table) {
        String allProductQuery = "SELECT * FROM product ORDER BY id DESC";

        try {
            prepStatement = connection.prepareStatement(allProductQuery);
            result = prepStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            Object[] row;

            while (result.next()) {
                row = new Object[4];
                row[0] = result.getInt(1);
                row[1] = result.getString(2);
                row[2] = result.getDouble(3);
                row[3] = result.getBytes(4);

                model.addRow(row);
            }
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean update(Product product) {
        String updateQuery = "UPDATE product SET name = ?, price = ? WHERE id = ?";

        try {
            prepStatement = connection.prepareStatement(updateQuery);
            prepStatement.setString(1, product.getName());
            prepStatement.setDouble(2, product.getPrice());
            prepStatement.setInt(3, product.getId());

            return prepStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Product product) {
        String deleteQuery = "DELETE product WHERE id = ?";

        try {
            prepStatement = connection.prepareStatement(deleteQuery);
            prepStatement.setInt(1, product.getId());

            return prepStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getMaxRowAOrderTable() {
        String selectQuery = "SELECT MAX(cid) FROM cart";
        
        int row = 0;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(selectQuery);
            while (result.next()) {
                row = result.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    public boolean isProductExist(int cid, int pid){
        String searchProduct = "SELECT * FROM cart WHERE cid = ? AND pid = ?";
        
        try {
            prepStatement = connection.prepareStatement(searchProduct);
            prepStatement.setInt(1, cid);
            prepStatement.setInt(2, pid);
            result = prepStatement.executeQuery();
            
            if(result.next()){
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public boolean insertCart(Cart cart){
        String insertCartQuery = "INSERT INTO cart (cid, pid, pName, qty, price, total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            prepStatement = connection.prepareStatement(insertCartQuery);
            prepStatement.setInt(1, cart.getId());
            prepStatement.setInt(2, cart.getPid());
            prepStatement.setString(3, cart.getpName());
            prepStatement.setInt(4, cart.getQty());
            prepStatement.setDouble(5, cart.getPrice());
            prepStatement.setDouble(6, cart.getTotal());
            
            return prepStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
