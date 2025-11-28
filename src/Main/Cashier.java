package Main;

import Config.Config;
import static Main.Main.viewOrders;
import java.sql.SQLException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Cashier {
    Scanner sr = new Scanner(System.in);
    Config con = new Config();

    public static void viewOrders() {
        String Order_query = "SELECT * FROM tbl_order";
        String[] headers = {"OrderID", "UserID", "ProductID", "Quantity", "TotalPrice", "OrderDate"};
        String[] columns = {"OrderID", "UserID", "ProductID", "Quantity", "TotalPrice", "OrderDate"};
        Config conf = new Config();
        conf.viewRecords(Order_query, headers, columns);
    }

    public void Cashier() throws SQLException {
        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. Add Order");
            System.out.println("2. View Orders");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Logout");

            int choice = -1;
            int attempts = 3;

            // Menu choice validation
            while (attempts > 0) {
                System.out.print("Enter choice: ");
                String input = sr.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("You didn't type anything.");
                    attempts--;
                    continue;
                }

                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 5) break;
                    System.out.println("Invalid choice. Enter 1–5.");
                    attempts--;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Numbers only.");
                    attempts--;
                }

                if (attempts == 0) {
                    System.out.println("Too many invalid attempts!");
                    return;
                }
            }

            switch (choice) {

                case 1: // Add Order
                    // Enter UserID
                    int userId = 0;
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter User ID: ");
                        String uInput = sr.nextLine().trim();
                        try {
                            userId = Integer.parseInt(uInput);
                            if (userId > 0) break;
                            System.out.println("User ID must be greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Numbers only.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (userId <= 0) break;

                    // Get latest product
                    String latest_Id = "SELECT ProductID FROM tbl_Products ORDER BY ProductID DESC LIMIT 1";
                    double productId = con.getSingleValue(latest_Id);
                    double p_price = con.getProductPrice((int) productId);
                    if (p_price == 0) {
                        System.out.println("Product not found.");
                        break;
                    }

                    // Quantity
                    int o_quantity = 0;
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter Quantity: ");
                        String qInput = sr.nextLine().trim();
                        try {
                            o_quantity = Integer.parseInt(qInput);
                            if (o_quantity > 0) break;
                            System.out.println("Quantity must be greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Numbers only.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (o_quantity <= 0) break;

                    // Order Date
                    String o_date = "";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter Order Date (YYYY-MM-DD): ");
                        String dateInput = sr.nextLine().trim();
                        try {
                            sdf.parse(dateInput); // validate
                            o_date = dateInput;
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (o_date.isEmpty()) break;

                    double Totalprice = p_price * o_quantity;

                    System.out.println("\n===== ORDER SUMMARY =====");
                    System.out.println("User ID    : " + userId);
                    System.out.println("Product ID : " + (int) productId);
                    System.out.println("Quantity   : " + o_quantity);
                    System.out.println("Total      : " + Totalprice);
                    System.out.println("Order Date : " + o_date);
                    System.out.println("==========================");

                    con.addRecord(
                        "INSERT INTO tbl_Order(UserID, ProductID, Quantity, TotalPrice, OrderDate) VALUES(?, ?, ?, ?, ?)",
                        userId, (int) productId, o_quantity, Totalprice, o_date
                    );

                    System.out.println("Order Saved Successfully.");
                    break;

                case 2: // View Orders
                    viewOrders();
                    break;

                case 3: // Update Order
                    viewOrders();
                    int updateId = 0;
                    while (true) {
                        System.out.print("Enter Order ID to Update (0 to cancel): ");
                        updateId = sr.nextInt();
                        sr.nextLine();
                        if (updateId == 0) {
                            System.out.println("Update Cancelled.");
                            return;
                        }
                        double exists = con.getSingleValue("SELECT COUNT(*) FROM tbl_Order WHERE OrderID = ?", updateId);
                        if (exists > 0) break;
                        System.out.println("Order ID does not exist.");
                    }

                    // Enter new UserID
                    int updateUserId = 0;
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter new User ID: ");
                        String uInput = sr.nextLine().trim();
                        try {
                            updateUserId = Integer.parseInt(uInput);
                            if (updateUserId > 0) break;
                            System.out.println("User ID must be greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Numbers only.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (updateUserId <= 0) break;

                    // Latest product
                    latest_Id = "SELECT ProductID FROM tbl_Products ORDER BY ProductID DESC LIMIT 1";
                    productId = con.getSingleValue(latest_Id);
                    p_price = con.getProductPrice((int) productId);
                    if (p_price == 0) {
                        System.out.println("Product not found.");
                        break;
                    }

                    // Quantity
                    o_quantity = 0;
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter Quantity: ");
                        String qInput = sr.nextLine().trim();
                        try {
                            o_quantity = Integer.parseInt(qInput);
                            if (o_quantity > 0) break;
                            System.out.println("Quantity must be greater than 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Numbers only.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (o_quantity <= 0) break;

                    // Order Date
                    o_date = "";
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter Order Date (YYYY-MM-DD): ");
                        String dateInput = sr.nextLine().trim();
                        try {
                            sdf.parse(dateInput);
                            o_date = dateInput;
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (o_date.isEmpty()) break;

                    Totalprice = p_price * o_quantity;

                    con.updateRecord(
                        "UPDATE tbl_Order SET UserID=?, ProductID=?, Quantity=?, TotalPrice=?, OrderDate=? WHERE OrderID=?",
                        updateUserId, (int) productId, o_quantity, Totalprice, o_date, updateId
                    );

                    System.out.println("Order Updated Successfully.");
                    viewOrders();
                    break;

                case 4: // Delete Order
                    viewOrders();
                    int delId = -1;
                    attempts = 3;
                    while (attempts > 0) {
                        System.out.print("Enter Order ID to delete (0 to cancel): ");
                        String dInput = sr.nextLine().trim();
                        try {
                            delId = Integer.parseInt(dInput);
                            if (delId == 0) {
                                System.out.println("Delete Cancelled.");
                                break;
                            }
                            double exists = con.getSingleValue("SELECT COUNT(*) FROM tbl_Order WHERE OrderID = ?", delId);
                            if (exists > 0) break;
                            System.out.println("Order ID does not exist.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Numbers only.");
                        }
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many invalid attempts. Returning...");
                            break;
                        }
                    }
                    if (delId == 0) break;

                    con.deleteRecord("DELETE FROM tbl_Order WHERE OrderID = ?", delId);
                    System.out.println("Order Deleted Successfully.");
                    viewOrders();
                    break;

                case 5:
                    return;
            }
        }
    }
}
  