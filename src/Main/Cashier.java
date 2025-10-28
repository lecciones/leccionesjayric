
package Main;

import Config.Config;
import static Main.Main.viewOrders;
import java.sql.SQLException;
import java.util.Scanner;


public class Cashier {
    Scanner sr = new Scanner(System.in);
    Config con = new Config();
      public static void viewOrders(){
        String Order_query = "SELECT * FROM tbl_order";
         String[] votersHeaders = {"OrderID", "Customer Name", "UserID", "ProductID", "Quantity",
             "TotalPrice", "OrderDate"};
        String[] votersColumns = {"OrderID", "CustomerName", "UserID", "ProductID", "Quantity",
             "TotalPrice", "OrderDate"};
        Config conf = new Config();
        conf.viewRecords(Order_query, votersHeaders, votersColumns);
    }
    public void Cashier() throws SQLException {
        System.out.println("=== Order Management ===");
        System.out.println("1. Add Order");
        System.out.println("2. View Order");
        System.out.println("3. Update Order");
        System.out.println("4. Delete Order");
        System.out.println("5. Logout");
        int o_c = 0;
       int attempts = 3;
       while(attempts > 0){
     System.out.print("Enter choice: ");    
    String input = sr.nextLine().trim();
    if(input.isEmpty()){
        System.out.println("You didn't type anything. Enter a number (1 to 5)");
        attempts--;
        System.out.println("Attempts left: " + attempts);
        continue;
    }
    try {
        attempts = Integer.parseInt(input);
        if(attempts >= 1 && attempts <= 5){
            break;
        }else{
            System.out.println("Invalid choice. Enter 1 to 5 only.");
           attempts--;
           System.out.println("Attempts left: " + attempts);
        }
    }catch(NumberFormatException e){ 
         System.out.println("Invalid input. Please enter digits only.");
         attempts--;
         System.out.println("Attempts left: " + attempts);
    }
        if(attempts == 0){
         System.out.println("Too many invalid attempts.");
         attempts--;
         System.out.println("Attempts left: " + attempts);
         break;
        }
       }
        switch(o_c){
            case 1:
                 System.out.print("Enter Customer Name: ");
                      String customername = sr.next();
                      
                      String latest_Id = "SELECT ProductID FROM tbl_Products ORDER BY ProductID DESC LIMIT 1";
                      double productId = con.getSingleValue(latest_Id);
                      
                      double p_price = con.getProductPrice((int)productId);
                      
                      if(p_price == 0){
                          System.out.println("Product not found. Try again.");
                      break;
                      } 
                      System.out.print("Enter Quantity: ");
                      int o_quantity = sr.nextInt();
                      
                      System.out.print("Enter Order Date (YYYY-MM-DD): ");
                      String o_date =  sr.next();
                     
                      sr.nextLine();
                       double Totalprice = p_price * o_quantity;
                       
                      System.out.println("===================");
                      System.out.println("----ORDER SUMMARY----");
                      System.out.println("Customer: "+ customername);
                      System.out.println("Product ID: "+ (int) productId);  
                      System.out.println("Quantity: "+ o_quantity);
                      System.out.println("Total: "+Totalprice);
                      System.out.println("Order Date: "+o_date);
                      System.out.println("===================");
                      con.addRecord(
                              "INSERT INTO tbl_Order(ProductID, CustomerName, Quantity, TotalPrice, OrderDate) VALUES(?, ?, ?, ?, ?, ?)",
                              (int )productId, customername, o_quantity, Totalprice, o_date
                      );
                      System.out.println("Order Save Successfully.");
                break;
            case 2:
                viewOrders();
                break;
            case 3:
                  viewOrders();
                        while(true){
                             System.out.print("Enter Order ID to Update(or 0 to cancel):");
                             int o_id = sr.nextInt();
                         
                         if(o_id == 0){
                             System.out.println("Update Order Cancelled");
                             return;
                         }
                         String checko_Id = "SELECT COUNT(*) FROM tbl_Order WHERE OrderID = ?";
                         double count = con.getSingleValue(checko_Id, o_id);
                          
                         if(count > 0){
                            break;
                         }else{
                             System.out.println("Order ID does not exist. Enter Existing ID ");
                         }
                          
                        }
                       
                        System.out.print("Enter Customet Name: ");
                          customername = sr.next();
                         
                           latest_Id = "SELECT ProductID FROM tbl_Products ORDER BY ProductID DESC LIMIT 1";
                       productId = con.getSingleValue(latest_Id);
                
                        p_price = con.getProductPrice((int)productId);
                      
                      if(p_price == 0){
                          System.out.println("Product not found. Try again.");
                      break;
                      } 
                      System.out.print("Enter Quantity: ");
                       o_quantity = sr.nextInt();
                      
                      System.out.print("Enter Order Date (YYYY-MM-DD): ");
                       o_date =  sr.nextLine();
                     
                        Totalprice = p_price * o_quantity;
                       
                      System.out.println("===================");
                      System.out.println("----ORDER SUMMARY----");
                      System.out.println("Customer: "+ customername);
                      System.out.println("Product ID: "+ (int) productId);  
                      System.out.println("Quantity: "+ o_quantity);
                      System.out.println("Total: "+Totalprice);
                      System.out.println("Order Date: "+o_date);
                      System.out.println("===================");
                      con.updateRecord("UPDATE tbl_Order SET CustomerName = ?, ProductID = ?,"
                              + " Quantity = ?, TotalPrice = ?, OrderDate = ? WHERE OrderID = ?", 
                            (int) productId,   customername, o_quantity, Totalprice, o_date
                      );
                      System.out.println("Order Updated Successfully.");
                        viewOrders();
                        
                break;
            case 4:
                viewOrders();
                System.out.print("Enter Order Id to delete (or 0 to cancel): ");
                    int o_id = sr.nextInt();
                   if(o_id == 0){
                System.out.println("Delete Cancelled.");
               return;   
             }              
                  con.deleteRecord("DELETE FROM tbl_Order WHERE OrderID = ?", o_id);
                   System.out.println("Order Deleted Successfully.");
                      viewOrders();
                break;
            case 5:
                return;
        }
    }
}
