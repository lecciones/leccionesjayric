package Main;

import Config.Config;
import static Main.Main.viewProducts;
import java.util.Scanner;

/**
 *
 * @author Angie
 */
public class Manager {
    Scanner sr = new Scanner(System.in);
    Config con = new Config();

    public static void viewProducts() {
        String Product_query = "SELECT * FROM tbl_Products";
        String[] votersHeaders = {"Product_ID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        String[] votersColumns = {"ProductID", "UserID", "Category", "Name", "Description", "Size", "Price"};
        Config conf = new Config();
        conf.viewRecords(Product_query, votersHeaders, votersColumns);
    }

    public void Manager() {
        System.out.println("=== Product Management ===");
        System.out.println("1. Add Product");
        System.out.println("2. View Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Logout");

        int p_choice = 0;
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Enter choice: ");
            String input = sr.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("You didn't type anything. Enter a number (1 to 5)");
                attempts--;
                System.out.println("Attempts left: " + attempts);
                continue;
            }

            try {
                p_choice = Integer.parseInt(input);
                if (p_choice >= 1 && p_choice <= 5) {
                    break;
                } else {
                    System.out.println("Invalid choice. Enter 1 to 5 only.");
                    attempts--;
                    System.out.println("Attempts left: " + attempts);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter digits only.");
                attempts--;
                System.out.println("Attempts left: " + attempts);
            }

            if (attempts == 0) {
                System.out.println("Too many invalid attempts.");
                attempts--;
                System.out.println("Attempts left: " + attempts);
                break;
            }
        }

        switch (p_choice) {
            case 1:
                System.out.println("===========");
                System.out.println("1. Drinks");
                System.out.println("2. Desserts");
                System.out.println("3. Meals");
                System.out.println("===========");

                int c_choice = 0;
                attempts = 3;

                while (attempts > 0) {
                    System.out.print("Enter Category choice: ");
                    String input = sr.nextLine().trim();

                    if (input.isEmpty()) {
                        System.out.println("You didn't type anything. Enter a number (1 to 3)");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                        continue;
                    }

                    try {
                        c_choice = Integer.parseInt(input);
                        if (c_choice >= 1 && c_choice <= 3) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Enter 1 to 3 only.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter digits only.");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                    }

                    if (attempts == 0) {
                        System.out.println("Too many invalid attempts.");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                        break;
                    }
                }

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

                System.out.print("Enter Description(N/A if none): ");
                String description = sr.next();

                String size_choice = "N/A";

                if (p_Category.equals("Drinks")) {
                    System.out.println("---Drink Size---");
                    System.out.println("1. Small");
                    System.out.println("2. Medium");
                    System.out.println("3. Large");

                    int size = -1;
                    attempts = 3;

                    while (attempts > 0) {
                        System.out.print("Enter choice size: ");
                        String input = sr.nextLine().trim();

                        if (input.isEmpty()) {
                            System.out.println("You didn't type anything. Enter a number (1 to 3)");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        try {
                            size = Integer.parseInt(input);

                            if (size >= 1 && size <= 3) {
                                break; 
                            } else {
                                System.out.println("Invalid choice. Enter 1 to 3 only.");
                                attempts--;
                                System.out.println("Attempts left: " + attempts);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter digits only.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                        }
                    }

                    if (attempts == 0) {
                        System.out.println("Too many invalid attempts. Returning...");
                        return;
                    }

                    switch (size) {
                        case 1: size_choice = "Small"; break;
                        case 2: size_choice = "Medium"; break;
                        case 3: size_choice = "Large"; break;
                        default: size_choice = "N/A"; break;
                    }

                    System.out.println("Selected size: " + size_choice);
                }

                System.out.print("Enter Product Price: ");
                double p_price = sr.nextDouble();
                con.addRecord("INSERT INTO tbl_Products(Category, Name, Description, Size, Price) VALUES (?, ?, ?, ?, ?, ?)",
                        p_Category, p_name, description, size_choice, p_price);
                System.out.println(" Product added Successfully.");
                break;

            case 2:
                viewProducts();
                break;

            case 3:
                int id;
                double count = 0;
                while (true) {
                    viewProducts();
                    System.out.print("Enter Product ID to update (or 0 to cancel): ");
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

                c_choice = 0;
                attempts = 3;

                while (attempts > 0) {
                    System.out.print("Enter Category Choice: ");
                    String input = sr.nextLine().trim();

                    if (input.isEmpty()) {
                        System.out.println("You didn't type anything. Enter a number (1 to 3)");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                        continue;
                    }

                    try {
                        c_choice = Integer.parseInt(input);
                        if (c_choice >= 1 && c_choice <= 3) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Enter 1 to 3 only.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter digits only.");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                    }

                    if (attempts == 0) {
                        System.out.println("Too many invalid attempts.");
                        attempts--;
                        System.out.println("Attempts left: " + attempts);
                        break;
                    }
                }

                p_Category = "";
                p_menu = "";

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
                }

                System.out.println(p_menu);
                System.out.print("Enter Product Name: ");
                p_name = sr.nextLine();
                System.out.print("Enter Description: ");
                description = sr.nextLine();

                size_choice = "N/A";

                if (p_Category.equals("Drinks")) {
                    System.out.println("---Drink Size---");
                    System.out.println("1. Small");
                    System.out.println("2. Medium");
                    System.out.println("3. Large");

                    int size = -1;
                    attempts = 3;

                    while (attempts > 0) {
                        System.out.print("Enter choice size: ");
                        String input = sr.nextLine().trim();

                        if (input.isEmpty()) {
                            System.out.println("You didn't type anything. Enter a number (1 to 3)");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                            continue;
                        }

                        try {
                            size = Integer.parseInt(input);
                            if (size >= 1 && size <= 3) {
                                break;
                            } else {
                                System.out.println("Invalid choice. Enter 1 to 3 only.");
                                attempts--;
                                System.out.println("Attempts left: " + attempts);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter digits only.");
                            attempts--;
                            System.out.println("Attempts left: " + attempts);
                        }
                    }

                    if (attempts == 0) {
                        System.out.println("Too many invalid attempts. Returning...");
                        return;
                    }

                    switch (size) {
                        case 1: size_choice = "Small"; break;
                        case 2: size_choice = "Medium"; break;
                        case 3: size_choice = "Large"; break;
                        default: size_choice = "N/A"; break;
                    }

                    System.out.println("Selected size: " + size_choice);
                }

                System.out.print("Enter Product Price: ");
                p_price = sr.nextDouble();
                sr.nextLine();

                con.updateRecord(
                        "UPDATE tbl_Products SET Category = ?, Name = ?, Description = ?, Size = ?, Price = ? WHERE ProductID = ?",
                        p_Category, p_name, description, size_choice, p_price, id
                );

                System.out.println(" Product updated successfully!");
                break;

            case 4:
                viewProducts();
                System.out.print("Enter Product Id to Delete (or 0 to cancel): ");
                id = sr.nextInt();
                if (id == 0) {
                    System.out.println("Delete Cancelled.");
                    return;
                }
                con.deleteRecord("DELETE FROM tbl_Products WHERE ProductID = ?", id);
                System.out.println("Product Deleted Successfully.");
                viewProducts();
                break;

            case 5:
                return;
        }
    }
}
