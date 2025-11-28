 package Main;

import Config.Config;
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

    public static void viewOrders() {
        String Order_query = "SELECT * FROM tbl_order";
        String[] votersHeaders = {"OrderID", "Customer Name", "UserID", "ProductID", "Quantity",
                "TotalPrice", "OrderDate"};
        String[] votersColumns = {"OrderID", "CustomerName", "UserID", "ProductID", "Quantity",
                "TotalPrice", "OrderDate"};
        Config conf = new Config();
        conf.viewRecords(Order_query, votersHeaders, votersColumns);
    }

    public static void viewTopSales() {
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
        String resp;
        int attempts = 3;

        do {
            System.out.println("==================================");
            System.out.println("..... Welcome to Cafe System .....");
            System.out.println("==================================");
            attempts = 3;

            System.out.println("===============");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("===============");

            int option = -1;

            while (attempts > 0) {
                System.out.print("Choose Option: ");
                String input = sr.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("You didn't type anything. Enter a number from (1 to 3.");
                    attempts--;
                    System.out.println("Attempts left: " + attempts);
                    continue;
                }

                try {
                    option = Integer.parseInt(input);
                    if (option >= 1 && option <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid Choice. Enter 1 to 3 only.");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter digits only.");
                    attempts--;
                    System.out.println("Attempts left: " + attempts);
                }
            }

            if (attempts == 0) {
                System.out.println("Too many invalid attempts.");
                break;
            }

            switch (option) {

                case 1:
                    while (attempts > 0) {
                        System.out.println("===Registration Panel===");
                        System.out.print("Enter Name: ");
                        String u_name = sr.nextLine();
                        if (u_name.isEmpty()) {
                            System.out.println("You didn't type anything. Please Enter your Name.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        System.out.print("Enter Email: ");
                        String u_email = sr.nextLine();
                        if (u_email.isEmpty()) {
                            System.out.println("You didn't type anything. Please Enter your Email.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        while (true) {
                            String qry = "SELECT * FROM tbl_User WHERE Email = ?";
                            java.util.List<java.util.Map<String, Object>> result = con.fetchRecord(qry, u_email);
                            if (result.isEmpty()) {
                                break;
                            } else {
                                attempts--;
                                System.out.println("Attempts left: " + attempts);
                                System.out.print("Email Already exists, Enter other Email: ");
                                u_email = sr.next();
                                if (attempts == 0) break;
                            }
                        }

                        System.out.println("Enter Type:");
                        System.out.println("1. Admin");
                        System.out.println("2. Manager");
                        System.out.println("3. Cashier");
                        System.out.print("Enter Choice: ");
                        int type = Integer.parseInt(sr.nextLine());
                        if (type < 1 || type > 3) {
                            System.out.println("Invalid Type");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }
                        String tp = (type == 1) ? "Admin" : (type == 2) ? "Manager" : "Cashier";

                        System.out.print("Enter Password: ");
                        String password = sr.nextLine();
                        if (password.isEmpty()) {
                            System.out.println("You didn't type anything. Please Enter your Password.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        String HashedPassword = con.hashPassword(password);

                        String sql = "INSERT INTO tbl_User(Name, Email, Type, Status, Password) VALUES (?, ?, ?, ?, ?)";
                        con.addRecord(sql, u_name, u_email, tp, "Pending", HashedPassword);
                        System.out.println("Register Successfully.");
                        break;
                    }

                    if (attempts == 0) {
                        System.out.println("Too many failed attempts.");
                        System.out.println("Returning to main menu...");
                    }
                    break;

                case 2:
                    while (attempts > 0) {
                        System.out.println("-----LOG IN USER-----");
                        System.out.print("Enter Email: ");
                        String u_email = sr.nextLine();
                        if (u_email.isEmpty()) {
                            System.out.println("You didn't type anything. Please Enter your Email.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        System.out.print("Enter Password: ");
                        String password = sr.nextLine();
                        if (password.isEmpty()) {
                            System.out.println("You didn't type anything. Please Enter your password.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        String Hashpass = con.hashPassword(password);
                        String qry = "SELECT * FROM tbl_User WHERE Email = ? AND Password = ?";
                        java.util.List<java.util.Map<String, Object>> result = con.fetchRecord(qry, u_email, Hashpass);

                        if (result.isEmpty()) {
                            System.out.println("INVALID CREDENTIALS. Please try again.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        java.util.Map<String, Object> user = result.get(0);
                        String u_status = user.get("Status").toString();
                        String u_type = user.get("Type").toString();

                        if (u_status.equals("Pending")) {
                            System.out.println("Your account is Pending approval by the Admin.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                        } else if (u_status.equals("Approved")) {
                            System.out.println("Login successful!");
                            System.out.println("Welcome, " + user.get("Name") + ".");

                            if (u_type.equalsIgnoreCase("Admin")) {
                                Admin admin = new Admin();
                                admin.Admin();
                            } else if (u_type.equalsIgnoreCase("Manager")) {
                                Manager manager = new Manager();
                                manager.Manager();
                            } else if (u_type.equalsIgnoreCase("Cashier")) {
                                Cashier cashier = new Cashier();
                                cashier.Cashier();
                            }
                            break;
                        } else {
                            System.out.println("Account status is invalid or deactivated.");
                            attempts--;
                        }
                    }

                    if (attempts == 0) {
                        System.out.println("Too many failed attempts. Try again later.");
                    }
                    break;

                case 3:
                    System.exit(0);
                    System.out.println("Exit Menu...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

            System.out.print("Do you want to continue?(y/n): ");
            resp = sr.nextLine();

        } while (resp.equals("y"));
    }
}
