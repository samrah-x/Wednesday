package GMS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // in main add addmember function
    public void signup() {
        // creates an account regardless of pre-existence and type of acc (member/ trainer)
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        addAccount(username, password);
    }

    public void login () {
        // try ccatch 
        // login does not have condition for type of acc although functions in main after this will have if black for type of acc
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (checkAccountByUsername(username, password)) {
            System.out.println("Login Success!");
        }
        else {
            System.out.println("Login Unsuccessful!");
        }
    }

    public boolean checkAccountByUsername(String username, String password) {
        String query = "SELECT * from accounts WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return false;
    }

    public void addAccount(String username, String password) {
        try {
            String query = "INSERT into accounts(username, password) VALUES (?, ?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, username);
            preparedstatement.setString(2, password);
            int affectedRows = preparedstatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Success!");
            }
            else {
                System.out.println("Failed.");
            }

        } catch (SQLException e) {
           e.printStackTrace(); 
        }
    }
}
