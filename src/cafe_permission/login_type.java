    
package cafe_permission;
import Config.Config;
import Login_config.user_login;
import java.sql.*;

public class login_type {

    Config con;
    
   public login_type(Config con){
       this.con = con;
   }
   
   public user_login loginUser(String u_email, String password) throws SQLException {
       String sql = "SELECT UserID, Name, Type FROM tbl_User WHERE Email = ? AND Password = ?";
       try(Connection connec = con.openConnection();
        PreparedStatement pst = connec.prepareStatement(sql)) {
         
           pst.setString(1, u_email);
           pst.setString(2, password);
           
           try (ResultSet rs = pst.executeQuery()){
               if (rs.next()){
                   return new user_login(
                      rs.getInt("UserID"),
                      rs.getString("Name"),
                      rs.getString("Type")     
                   );
               }
           }
           
       } catch (SQLException e) {
           System.out.println("Login Error: "+e.getMessage());
       }
        return null;
   }
   
   public boolean ManageProducts(user_login user) {
       return user.getType().equals("Admin") || user.getType().equals("Manager");
   }
   public boolean ManageOrders(user_login user){
       return user.getType().equals("Manager") || user.getType().equals("Cashier");
               
   }   
   
   
   }
   

