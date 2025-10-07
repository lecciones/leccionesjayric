 package Main;

import Config.Config;
import Login_config.user_login;
import cafe_permission.login_type;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    
    public static void viewProducts() {
        String Product_query = "SELECT * FROM tbl_Products";
        String[] votersHeaders = {"Product_ID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        String[] votersColumns = {"ProductID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        Config conf = new Config();
        conf.viewRecords(Product_query, votersHeaders, votersColumns);
    }
    
    public static void viewOrders(){
        String Order_query = "SELECT * FROM tbl_order";
         String[] votersHeaders = {"OrderID", "Customer Name", "UserID", "ProductID", "Quantity",
             "TotalPrice", "OrderDate"};
        String[] votersColumns = {"OrderID", "CustomerName", "UserID", "ProductID", "Quantity",
             "TotalPrice", "OrderDate"};
        Config conf = new Config();
        conf.viewRecords(Order_query, votersHeaders, votersColumns);
    }
    
    
    public static void viewTopSales(){
        String topsales_qry = 
                    "SELECT tbl_Products.Name, SUM(tbl_Order.Quantity) AS TotalSold " +
                    "FROM tbl_Order " +
                    "JOIN tbl_Products ON tbl_Order.ProductID = tbl_Products.ProductID " +
                    "GROUP BY tbl_Products.Name " +
                    "ORDER BY TotalSold DESC " +
                    "LIMIT 5";

                 String[] votersHeaders = {"Product", "TotalSold"};
                 String[] votersColumns = {"Name", "TotalSold"};
                Config conf = new Config();
                 conf.viewRecords(topsales_qry, votersHeaders, votersColumns);
    }
    public static void main(String[] args) throws SQLException {
        Scanner sr = new Scanner(System.in);
        Config con = new Config();
        con.connectDB();
        login_type log = new login_type(con);

        user_login currentUser = null;          
        String resp;
      do {
       System.out.println("==================================");
       System.out.println(".....☕ Welcome to Cafe System ☕.....");
       System.out.println("==================================");

        
        
        System.out.println("1. Manage User");
        System.out.println("2. Manage Product");
        System.out.println("3. Manage Order");
        System.out.println("4. Sales Track ");
        
        System.out.print("Choose Option: ");
        int option = sr.nextInt();
        sr.nextLine(); 

        switch(option) {
            
        case 1:
            System.out.println("---User Management---");
            System.out.println("Admin, Manager, Cashier Only.");
              System.out.println("1. Login User");
              System.out.println("2. Log out");
              int u_choice;
              while(true){
              System.out.print("Enter Choice: ");
              if(sr.hasNextInt()){
               u_choice = sr.nextInt();
              sr.nextLine();
              if(u_choice == 1 || u_choice == 2){
                  break;
              }else{
                  System.out.println("Invalid choice. Enter 1 and 2 only.");
              }
              }else{
               System.out.println("Invalid input. Enter a number (1 or 2).");
               sr.nextLine();
              }
              } 
              
              switch(u_choice){
                  case 1:
        int attempts = 5;
        while(attempts > 0) {
        System.out.println("-----LOG IN USER-----");
        System.out.print("Enter Username: ");
        String username = sr.nextLine();
        System.out.print("Enter Password: ");
        String password = sr.nextLine();

        user_login login = log.loginUser(username, password);

        if(login == null) {
        attempts--;
        System.out.println(" Login Failed. Attempts left: " + attempts);
        if(attempts == 0) {
        System.out.println("Too many failed attempts. Exiting...");
        return;
           }
        } else {
        currentUser = login; 
        System.out.println(" Welcome, " + login.getName() + " (" + login.getType() + ")");
        break;
           }
          }
                      break;
                  case 2:
                if(currentUser != null){
                    System.out.println("---LOGOUT USER---");
                    System.out.print("Do you want to Logout?(y/n):");
                    String confirm = sr.next();
                    if(confirm.equalsIgnoreCase("y")){
                        System.out.println("Logging out "+ currentUser.getName() +"...");
                        currentUser = null;
                    }else{
                        System.out.println("Logout cancelled.");
                    }
                }else{
                    System.out.println("No user is currently Logged in.");
                }
                
              break;
              }
           break;
        case 2:
        System.out.println("---Product Management---");
        System.out.println("1. Add Product");
        System.out.println("2. View Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
                
        System.out.print("Enter Choice: ");
        int p_choice = sr.nextInt();
        sr.nextLine();
        switch(p_choice){
        case 1:
            if(currentUser == null){
           System.out.println("You must log in first before adding a product (admin/cashier).");
         }else{
         if(log.ManageProducts(currentUser)){   
        System.out.println("===========");
        System.out.println("1. Drinks");
        System.out.println("2. Desserts");
        System.out.println("3. Meals");
        System.out.println("===========");
        System.out.print("Enter Category Choice: ");
        int c_choice = sr.nextInt();
        sr.nextLine();
        
        String p_Category = "";
        String p_menu = "";
         switch(c_choice){
            case 1:
            p_Category = "Drinks";
            p_menu = 
             "------- DRINKS -------\n" +
                "1. Espresso - ₱80.00\n" +
                "2. Cappuccino - ₱120.00\n" +
                "3. Latte - ₱110.00\n" +
                "4. Iced Coffee - ₱90.00\n" +
                "5. Hot Chocolate - ₱100.00\n";
            break;
            case 2:
                p_Category = "Desserts";
                p_menu =
                "------- DESSERTS -------\n" +
                "6. Cheesecake - ₱150.00\n" +
                "7. Brownie - ₱90.00\n" +
                "8. Muffin - ₱70.00\n" +
                "9. Croissant - ₱60.00\n";        
            break;
            case 3:
              p_Category = "Meals";
              p_menu = 
                        "------- MEALS -------\n" +
                "10. Club Sandwich - ₱140.00\n" +
                "11. Chicken Burger - ₱160.00\n" +
                "12. Spaghetti - ₱180.00\n" +
                "13. Caesar Salad - ₱130.00\n";
           break;
            default:
                System.out.println("Invalid Category Choice.");
                return;
         }
           
              System.out.println(p_menu);
              System.out.print("Enter Product Name: ");
              String p_name = sr.nextLine();
              
              System.out.print("Enter Description(N/A if none): ");
              String description = sr.next();
              
               String size_choice = "N/A";
              if(p_Category.equals("Drinks")){
              System.out.println("---Drink Size---");
              System.out.println("1. Small");
              System.out.println("2. Medium");
              System.out.println("3. Large");
              System.out.println("Enter Size: ");
              int size = sr.nextInt();  
              sr.nextLine();
              switch(size){
                  case 1:
                      size_choice = "Small";
                      break;
                  case 2:
                      size_choice = "Medium";
                      break;
                  case 3:
                      size_choice = "Large";
                      break;
                  default:
                      System.out.println("Invalid Size Choice.");
              }
              }
              System.out.print("Enter Product Price: ");
               double p_price = sr.nextDouble();
              con.addRecord("INSERT INTO tbl_Products(UserID, Category, Name, Description, Size, Price) VALUES (?, ?, ?, ?, ?, ?)",
                    currentUser.getId(), p_Category, p_name, description, size_choice, p_price);
              System.out.println(" Product added Successfully.");
                    } else {
              System.out.println(" You do not have permission to add products.");
                    }
                }
             
              break;
                    case 2:
                        if(currentUser == null){
                           System.out.println(" You must log in first before viewing Product (Admin/Manager).");  
                        }else{
                        if(log.ManageProducts(currentUser)){
                             viewProducts();
                        }else{
                            System.out.println("You don't have permission.");
                        }
                        }
                        break;
                            case 3:
                        int id;
                       double count = 0;
 
          if (currentUser == null) {
              System.out.println(" You must log in first before updating Product (Admin/Manager)."); 
          } else {
              if (log.ManageProducts(currentUser)) {

               
                  while (true) {
                      viewProducts();
                      System.out.print("Enter Product ID (or 0 to cancel): ");
                      id = sr.nextInt();
                      sr.nextLine(); 

                      if (id == 0) {
                          System.out.println("Update Cancelled");
                          return;
                      }

                      String checkp_Id = "SELECT COUNT(*) FROM tbl_Products WHERE ProductID = ?";
                      count = con.getSingleValue(checkp_Id, id);

                      if (count > 0) break;
                      else System.out.println("Product ID does not exist. Try again.");
                  }

                 
                  System.out.println("1. Drinks");
                  System.out.println("2. Desserts");
                  System.out.println("3. Meals");
                  System.out.print("Enter Category Choice: ");
                  int c_choice = sr.nextInt();
                  sr.nextLine();

                  String p_Category = "";
                  String p_menu = "";

                  switch (c_choice) {
                      case 1:
                          p_Category = "Drinks";
                          p_menu =
                              "------- DRINKS -------\n" +
                              "1. Espresso - ₱80.00\n" +
                              "2. Cappuccino - ₱120.00\n" +
                              "3. Latte - ₱110.00\n" +
                              "4. Iced Coffee - ₱90.00\n" +
                              "5. Hot Chocolate - ₱100.00\n";
                          break;
                      case 2:
                          p_Category = "Desserts";
                          p_menu =
                              "------- DESSERTS -------\n" +
                              "6. Cheesecake - ₱150.00\n" +
                              "7. Brownie - ₱90.00\n" +
                              "8. Muffin - ₱70.00\n" +
                              "9. Croissant - ₱60.00\n";
                          break;
                      case 3:
                          p_Category = "Meals";
                          p_menu =
                              "------- MEALS -------\n" +
                              "10. Club Sandwich - ₱140.00\n" +
                              "11. Chicken Burger - ₱160.00\n" +
                              "12. Spaghetti - ₱180.00\n" +
                              "13. Caesar Salad - ₱130.00\n";
                          break;
                      default:
                          System.out.println("Invalid Category Choice.");
                          return;
                  }

              
                  System.out.println(p_menu);

               
                  System.out.print("Enter Product Name: ");
                  String p_name = sr.nextLine();

                  System.out.print("Enter Description: ");
                  String description = sr.nextLine();

                  String size_choice = "N/A";
                  if (p_Category.equals("Drinks")) {
                      System.out.println("--- Drink Size ---");
                      System.out.println("1. Small");
                      System.out.println("2. Medium");
                      System.out.println("3. Large");
                      System.out.print("Enter Size: ");
                      int size = sr.nextInt();
                      sr.nextLine();

                      switch (size) {
                          case 1: size_choice = "Small"; break;
                          case 2: size_choice = "Medium"; break;
                          case 3: size_choice = "Large"; break;
                          default: System.out.println("Invalid Size Choice.");
                      }
                  }

                  System.out.print("Enter Product Price: ");
                  double p_price = sr.nextDouble();
                  sr.nextLine();

                  
                  con.updateRecord(
                      "UPDATE tbl_Products SET Category = ?, Name = ?, Description = ?, Size = ?, Price = ? WHERE ProductID = ?",
                      p_Category, p_name, description, size_choice, p_price, id
                  );

                  System.out.println("✅ Product updated successfully!");

              } else {
                  System.out.println(" You do not have permission to update products.");
                }
          }

                        break;
                    case 4:
                        if(currentUser == null){
                          System.out.println(" You must log in first before deleting Product (Admin/Manager)."); 
                        }else{
                        if(log.ManageProducts(currentUser)){
                         viewProducts();
                          System.out.print("Enter Product Id to Delete: ");
                          id = sr.nextInt();
                        
                         con.deleteRecord("DELETE FROM tbl_Products WHERE ProductID = ?", id);
                         System.out.println("Product Deleted Successfully.");
                         viewProducts();
                        }else{
                            System.out.println("You don't have permission.)");
                        }
                        }
                        break;
                }
                break;

            case 3:
                System.out.println("1. Add Order");
                System.out.println("2. View Order");
                System.out.println("3. Update Order");
                System.out.println("4. Delete Order");
                
                System.out.print("Enter Choice: ");
                int o_choice = sr.nextInt();
                sr.nextLine();
                
                switch(o_choice){
                    case 1:
                          if(currentUser == null){
                  System.out.println("You must log in first before adding Order(Cashier).");
              }else{
                  if(log.ManageOrders(currentUser)) {
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
                      System.out.println("Handled By: "+currentUser.getId());
                      System.out.println("===================");
                      con.addRecord(
                              "INSERT INTO tbl_Order(CustomerName, UserID, ProductID, Quantity, TotalPrice, OrderDate) VALUES(?, ?, ?, ?, ?, ?)",
                              customername,currentUser.getId(),(int )productId, o_quantity, Totalprice, o_date
                      );
                      System.out.println("Order Save Successfully.");
                  }else{
                      System.out.println("You do not have permission to add orders.");
                  }
              }
                        break;
                    case 2:
                        if(currentUser == null){
                            System.out.println(" You must log in first (Cashier)."); 
                        }else{
                            if(log.ManageOrders(currentUser)){
                        viewOrders();
                            }else{
                                System.out.println("You don't have permission");
                            }
                        }
                        break;
                    case 3:
                        int o_id;
                        double count = 0;
                        if(currentUser == null){
                            System.out.println("You must log in first (Cashier) before Updating Order.");
                        }else{
                            if(log.ManageOrders(currentUser)){
                         viewOrders();
                        while(true){
                             System.out.print("Enter Order ID to Update(or 0 to cancel):");
                         o_id = sr.nextInt();
                         
                         if(o_id == 0){
                             System.out.println("Update Order Cancelled");
                             return;
                         }
                         String checko_Id = "SELECT COUNT(*) FROM tbl_Order WHERE OrderID = ?";
                         count = con.getSingleValue(checko_Id, o_id);
                          
                         if(count > 0){
                            break;
                         }else{
                             System.out.println("Order ID does not exist. Enter Existing ID ");
                         }
                          
                        }
                       
                        System.out.print("Enter Customet Name: ");
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
                      String o_date =  sr.nextLine();
                     
                       double Totalprice = p_price * o_quantity;
                       
                      System.out.println("===================");
                      System.out.println("----ORDER SUMMARY----");
                      System.out.println("Customer: "+ customername);
                      System.out.println("Product ID: "+ (int) productId);  
                      System.out.println("Quantity: "+ o_quantity);
                      System.out.println("Total: "+Totalprice);
                      System.out.println("Order Date: "+o_date);
                      System.out.println("Handled By: "+currentUser.getId());
                      System.out.println("===================");
                      con.updateRecord("UPDATE tbl_Order SET CustomerName = ?, ProductID = ?,"
                              + " Quantity = ?, TotalPrice = ?, OrderDate = ? WHERE OrderID = ?", 
                              customername, currentUser.getId(), (int) productId, o_quantity, Totalprice, o_date
                      );
                      System.out.println("Order Updated Successfully.");
                        viewOrders();
                            }
                        }
                        break;
                    case 4:
                        if(currentUser == null){
                            System.out.println("You must log in first before Deleting Order(Cashier)");
                        }else{
                            if(log.ManageOrders(currentUser)){
                        viewOrders();
                        System.out.print("Enter Order Id: ");
                         o_id = sr.nextInt();
                        
                         con.deleteRecord("DELETE FROM tbl_Order WHERE OrderID = ?", o_id);
                         System.out.println("Order Deleted Successfully.");
                        viewOrders();
                            }
                        }
                        break;
                }
            
                break;
                
            case 4:
               if(currentUser == null){
                   System.out.println("You must Log in first (Admin only).");
               }else{
                   if(currentUser.getType().equalsIgnoreCase("Admin")){
                   System.out.println("1. Daily Sales");
                   System.out.println("2. Sales by Date Range");
                   System.out.println("3. Top Selling Products");
                   System.out.print("Enter Choice: ");
                   int admin_choice = sr.nextInt();
                   
                   sr.nextLine();
                   
                   switch(admin_choice){
                       case 1:
                           String dailySale_qry = "SELECT SUM(TotalPrice) FROM tbl_Order WHERE date"
                            + "(OrderDate) = date('now', 'localtime')";
                           double dailySales = con.getSingleValue(dailySale_qry);
                           System.out.println("-----------------------");
                           System.out.println("Total Sales for Today: " + dailySales);
                           System.out.println("-----------------------");
                           
                           break;
                       case 2:
                           System.out.print("Enter Start Date (YYYY-MM-DD): ");
                           String startDate = sr.next();
                           
                           System.out.print("Enter End Date (YYYY-MM-DD): ");
                           String endDate = sr.next();
                           
                           sr.nextLine();
                            
                           String dateRange_qry = "SELECT SUM(TotalPrice) FROM tbl_Order WHERE OrderDate BETWEEN ? AND ?";
                           double rangesales = con.getSingleValue(dateRange_qry);
                           System.out.println("-----------------------------------------------------");
                           System.out.println("Total Sales from " +startDate + " to " + endDate);
                           System.out.println("Range Sales: "+rangesales);
                           System.out.println("-----------------------------------------------------");
                           
                           break;
                    case 3:
                         
                          viewTopSales();
                           break;
                    default:
                        System.out.println("Invalid Choice.");
                   }
                   }else{
                       System.out.println("You don't have permission. Admin only");
                   }
                   }
                break;
             default:
                System.out.println("Invalid option. Try again.");
                
              }
        
                System.out.print("Do you want to continue?(y/n): ");
        
                resp = sr.nextLine();
                } while(resp.equals("y"));
        
      }
      
}

 System.out.println("==============================================================");
        System.out.println("                      MENU ITEMS                              ");
        System.out.println("==============================================================");
        System.out.printf("%-25s %-25s %-25s%n", "------- DRINKS -------", "------- DESSERTS -------", "------- MEALS -------");

     String[][] menu = {
    {"1. Espresso - ₱80.00",       "6. Cheesecake - ₱150.00",     "10. Club Sandwich - ₱140.00"},
    {"2. Cappuccino - ₱120.00",    "7. Brownie - ₱90.00",          "11. Chicken Burger - ₱160.00"},
    {"3. Latte - ₱110.00",         "8. Muffin - ₱70.00",           "12. Spaghetti - ₱180.00"},
    {"4. Iced Coffee - ₱90.00",    "9. Croissant - ₱60.00",        "13. Caesar Salad - ₱130.00"},
    {"5. Hot Chocolate - ₱100.00", "",                              ""}
      };

   for (int i = 0; i < menu.length; i++) {
      System.out.printf("%-25s %-25s %-25s%n", menu[i][0], menu[i][1], menu[i][2]);
    }

System.out.println("==============================================================");


      
              
      

