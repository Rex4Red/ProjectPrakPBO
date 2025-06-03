package Models;

import Views.ForgotPasswordView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author putra
 */
public class modeltabeldata {

    // Gunakan koneksi dari class Connector (yang terhubung ke XAMPP)
    Connection con = Connector.connection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public int getMaxRowAdminTable() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT MAX(id) FROM admin");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public boolean isAdminNameExist(String username) {
        try {
            ps = con.prepareStatement("SELECT * FROM admin WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insert(AdminModel admin) {
        String sql = "INSERT INTO admin (id, username, password, s_ques, ans) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, admin.getId());
            ps.setString(2, admin.getUsername());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getsQues());
            ps.setString(5, admin.getAns());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            ps = con.prepareStatement("SELECT * FROM admin WHERE username = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean getSecurity(String username) {
        try {
            ps = con.prepareStatement("SELECT * FROM admin WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                ForgotPasswordView.jTextField2.setText(rs.getString(4));
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean getAns(String username, String newAns) {
        try {
            ps = con.prepareStatement("SELECT * FROM admin WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(5);
                if(newAns.equals(oldAns)){
                    return true;
                }                
            }
        } catch (Exception ex) {
            Logger.getLogger(modeltabeldata.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    public boolean  setPassword(String username, String password){
        String sql = "update admin set password =? where username =?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
           return false;
        }
    }
}
