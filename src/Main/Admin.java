package Main;

import Config.Config;
import static Main.Main.viewOrders;
import static Main.Main.viewProducts;
import static Main.Main.viewTopSales;
import java.util.Scanner;

public class Admin {
    Scanner sr = new Scanner(System.in);
    Config con = new Config();

    public static void viewUsers() {
        String votersQuery = "SELECT * FROM tbl_User";
        String[] votersHeaders = {"ID", "Name","Email", "Status", "Type"};
        String[] votersColumns = {"UserID", "Name", "Email", "Status", "Type"};
        Config conf = new Config(); 
        conf.viewRecords(votersQuery, votersHeaders, votersColumns);
    } 

    public static void viewProducts() {
        String Product_query = "SELECT * FROM tbl_Products";
        String[] votersHeaders = {"Product_ID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        String[] votersColumns = {"ProductID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        Config conf = new Config();
        conf.viewRecords(Product_query, votersHeaders, votersColumns);
    }

    public static void viewOrders() {
        String Order_query = "SELECT * FROM tbl_order";
        String[] votersHeaders = {"OrderID", "Customer Name", "UserID", "ProductID", "Quantity",
                                  "TotalPrice", "OrderDate"};
        String[] votersColumns = {"OrderID", "CustomerName", "UserID", "ProductID", "Quantity",
                                  "TotalPrice", "OrderDate"};
        Config conf = new Config();
        conf.viewRecords(Order_query, votersHeaders, votersColumns);
    }

    public void Admin() {
        while(true){
        System.out.println("=== ADMIN DASHBOARD ===");
        System.out.println("1. Approve Account");
        System.out.println("2. Manage Accounts");
        System.out.println("3. Manage Product");
        System.out.println("4. Manage Order");
        System.out.println("5. Sales Track");
        System.out.println("6. Logout");

        int respp = 0;
        int attempts = 3;

        while(attempts > 0) {
            System.out.print("Enter choice: ");    
            String input = sr.nextLine().trim();

            if(input.isEmpty()){
                System.out.println("You didn't type anything. Enter a number (1 to 6)");
                attempts--;
                System.out.println("Attempts left: " + attempts);
                continue;
            }

            try {
                respp = Integer.parseInt(input);
                if(respp >= 1 && respp <= 6){
                    break;
                } else {
                    System.out.println("Invalid choice. Enter 1 to 6 only.");
                    attempts--;
                    System.out.println("Attempts left: " + attempts);
                }
            } catch(NumberFormatException e){ 
                System.out.println("Invalid input. Please enter digits only.");
                attempts--;
                System.out.println("Attempts left: " + attempts);
            }
        }

        switch (respp) {
            // APPROVE ACCOUNT
            case 1:
                viewUsers();                    
                System.out.print("Enter ID to Approve (or 0 to cancel): ");
                int ids = sr.nextInt();
                sr.nextLine(); // FIX

                if(ids == 0){
                    System.out.println("Approve Account cancelled!");
                    break;
                }

                String sql = "UPDATE tbl_user SET Status = ? WHERE UserID = ?";
                con.updateRecord(sql, "Approved", ids);
                System.out.println("Account approved successfully!");
                break; 

            // MANAGE ACCOUNTS
            case 2:
                while(true){
                    System.out.println("===================");  
                    System.out.println("1. View Account");
                    System.out.println("2. Delete Account");
                    System.out.println("3. Back");
                    System.out.println("===================");

                    int c_ac = 0;
                    attempts = 3;

                    while(attempts > 0){
                        System.out.print("Enter Choice: ");
                        String input = sr.nextLine().trim();

                        if(input.isEmpty()){
                            System.out.println("You didn't type anything. Enter a number (1 to 3) only.");
                            attempts--;
                            continue;
                        }

                        try {
                            c_ac = Integer.parseInt(input);
                            if(c_ac >= 1 && c_ac <= 3){
                                break;
                            } else {
                                System.out.println("Invalid choice. Enter 1 to 3 only.");
                                attempts--;
                            }
                        } catch(NumberFormatException e){ 
                            System.out.println("Invalid input. Please enter digits only.");
                            attempts--;
                        }
                    }

                    switch(c_ac){
                        case 1:
                            viewUsers();
                            break;  
                        case 2:
                            viewUsers();
                            System.out.println("Enter Id to Delete (or 0 to cancel): ");
                            int a_id = sr.nextInt();
                            sr.nextLine(); // FIX

                            if(a_id == 0){
                                System.out.println("Delete Cancelled.");
                                break;
                            }        

                            String sqlDelete = "DELETE FROM tbl_User WHERE UserID = ?";
                            con.deleteRecord(sqlDelete, a_id);
                            System.out.println("Deleted Successfully.");
                            viewUsers();
                            break;
                        case 3:
                            // back to admin menu
                            break;
                    }

                    if(c_ac == 3) break;
                }
                break;

            // MANAGE PRODUCT
            case 3:
                while(true){
                    System.out.println("===================");  
                    System.out.println("1. View Product");
                    System.out.println("2. Delete Product");
                    System.out.println("3. Back");
                    System.out.println("===================");

                    int c_pr = 0;
                    attempts = 3;

                    while(attempts > 0){
                        System.out.print("Enter choice: ");    
                        String input = sr.nextLine().trim();

                        if(input.isEmpty()){
                            System.out.println("You didn't type anything. Enter a number (1 to 3)");
                            attempts--;
                            continue;
                        }

                        try {
                            c_pr = Integer.parseInt(input);
                            if(c_pr >= 1 && c_pr <= 3){
                                break;
                            } else {
                                System.out.println("Invalid choice.");
                                attempts--;
                            }
                        } catch(NumberFormatException e){ 
                            System.out.println("Invalid input.");
                            attempts--;
                        }
                    }

                    switch(c_pr){
                        case 1:
                            viewProducts();
                            break;
                        case 2:
                            viewProducts();
                            System.out.print("Enter Product Id to Delete (or 0 to cancel): ");
                            int id = sr.nextInt();
                            sr.nextLine(); // FIX

                            if(id == 0){
                                System.out.println("Delete Cancelled.");
                                break;   
                            }                    
                            con.deleteRecord("DELETE FROM tbl_Products WHERE ProductID = ?", id);
                            System.out.println("Product Deleted Successfully.");
                            viewProducts();
                            break;
                        case 3:
                            break;
                    }

                    if(c_pr == 3) break;
                }
                break;

            // MANAGE ORDER
            case 4:
                while(true){
                    System.out.println("=================");
                    System.out.println("1. View Order");
                    System.out.println("2. Delete Order");
                    System.out.println("3. Back");
                    System.out.println("=================");

                    int c_or = 0;
                    attempts = 3;

                    while(attempts > 0){
                        System.out.print("Enter choice: ");    
                        String input = sr.nextLine().trim();

                        if(input.isEmpty()){
                            System.out.println("You didn't type anything.");
                            attempts--;
                            continue;
                        }

                        try {
                            c_or = Integer.parseInt(input);
                            if(c_or >= 1 && c_or <= 3){
                                break;
                            } else {
                                System.out.println("Invalid choice.");
                                attempts--;
                            }
                        } catch(NumberFormatException e){ 
                            System.out.println("Invalid input.");
                            attempts--;
                        }
                    }

                    switch(c_or){
                        case 1:
                            viewOrders();
                            break;
                        case 2:
                            viewOrders();
                            System.out.print("Enter Order Id to delete (or 0 to cancel): ");
                            int o_id = sr.nextInt();
                            sr.nextLine(); // FIX

                            if(o_id == 0){
                                System.out.println("Delete Cancelled.");
                                break;   
                            }                            
                            con.deleteRecord("DELETE FROM tbl_Order WHERE OrderID = ?", o_id);
                            System.out.println("Order Deleted Successfully.");
                            viewOrders();
                            break;
                        case 3:
                            break;    
                    }

                    if(c_or == 3) break;
                }
                break;

            // SALES TRACK
            case 5:
                while(true){
                    System.out.println("1. Daily Sales");
                    System.out.println("2. Sales by Date Range");
                    System.out.println("3. Top Selling Products");
                    System.out.println("4. Back");

                    int admin_choice = 0;
                    attempts = 3;

                    while(attempts > 0){
                        System.out.print("Enter choice: ");    
                        String input = sr.nextLine().trim();

                        if(input.isEmpty()){
                            System.out.println("You didn't type anything.");
                            attempts--;
                            continue;
                        }

                        try {
                            admin_choice = Integer.parseInt(input);
                            if(admin_choice >= 1 && admin_choice <= 4){
                                break;
                            } else {
                                System.out.println("Invalid choice.");
                                attempts--;
                            }
                        } catch(NumberFormatException e){ 
                            System.out.println("Invalid input.");
                            attempts--;
                        }    
                    }

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
                            sr.nextLine(); // FIX

                            String dateRange_qry = "SELECT SUM(TotalPrice) FROM tbl_Order WHERE OrderDate BETWEEN ? AND ?";
                            double rangesales = con.getSingleValue(dateRange_qry);
                            System.out.println("-----------------------------------------------------");
                            System.out.println("Total Sales from " + startDate + " to " + endDate);
                            System.out.println("Range Sales: "+ rangesales);
                            System.out.println("-----------------------------------------------------");
                            break;

                        case 3:
                            viewTopSales();
                            break;

                        case 4:
                            break;
                    }

                    if(admin_choice == 4) break;
                }
                break;

            case 6:
                return;
        }
    }
}
}
