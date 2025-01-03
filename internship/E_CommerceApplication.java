// Required Imports
import java.sql.*;
import java.util.*;

// Database Connection Utility Class
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// User Management Class
class User {
    public void register(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

// Product Management Class
class Product {
    public void addProduct(String name, double price) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewProducts() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Price: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Shopping Cart Management Class
class ShoppingCart {
    private final List<Integer> cart = new ArrayList<>();

    public void addToCart(int productId) {
        cart.add(productId);
        System.out.println("Product added to cart.");
    }

    public void viewCart() {
        System.out.println("Cart Items:");
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?")) {
            for (int productId : cart) {
                stmt.setInt(1, productId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Price: " + rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Main Application
public class E_CommerceApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        Product product = new Product();
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Add Product\n4. View Products\n5. Add to Cart\n6. View Cart\n7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    user.register(username, password);
                }
                case 2 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (user.login(username, password)) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    product.addProduct(name, price);
                }
                case 4 -> product.viewProducts();
                case 5 -> {
                    System.out.print("Enter product ID to add to cart: ");
                    int productId = scanner.nextInt();
                    cart.addToCart(productId);
                }
                case 6 -> cart.viewCart();
                case 7 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
