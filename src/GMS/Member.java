package GMS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// RENAME AS MEMBER
public class Member {
    
    private Connection connection;
    private Scanner scanner;

    private double weight;
    private double height;

    public Member(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addMember() {
        System.out.print("Enter Member Name: ");
        String name = scanner.next();
        System.out.print("Enter Member Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Member Gender: ");
        // checkbox
        String gender = scanner.next();
        System.out.print("Enter Member Weight: ");
        weight = scanner.nextDouble();
        System.out.print("Enter Member Height: ");
        height = scanner.nextDouble();
        // static method calculateBMI
        double BMI = calculateBMI(weight, height);
        try {
            String query = "INSERT into members(name, age, gender, weight, height, BMI) values (?,?,?,?,?,?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, name);
            preparedstatement.setInt(2, age);
            preparedstatement.setString(3, gender);
            preparedstatement.setDouble(4, weight);
            preparedstatement.setDouble(5, height);
            preparedstatement.setDouble(6, BMI);
            int affectedRows = preparedstatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member Added successfully!");
            }
            else {
                System.out.println("Failed to add Member.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewMember() {
        // read operation 
        String query = "select * from members";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+-----+----------------------+-------+----------+--------+--------+-------+");
            System.out.println("| ID  | Name                 | Age   | Gender   | Weight | Height | BMI   |");
            System.out.println("+-----+----------------------+-------+----------+--------+--------+-------+");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                double weight = resultSet.getDouble("weight");
                double height = resultSet.getDouble("height");
                double BMI = resultSet.getDouble("BMI");
                System.out.printf("|%-5d|%-22s|%-7d|%-10s|%-8.1f|%-8.1f|%-7.1f|\n", id, name, age, gender, weight, height, BMI);
                System.out.println("+-----+----------------------+-------+----------+--------+--------+-------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMemberByName(String name) {
        String query = "SELECT * from members WHERE name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, name);
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

    private double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    public double getBMI() {
        return calculateBMI(weight, height);
    }

    private String determineBMIstatus (int age, double BMI) {
        if (age >= 20) {
            // Adults
            if (BMI < 18.5) {
                return "Underweight";
            } 
            else if (BMI < 25) {
                return "Normal weight";
            } 
            else if (BMI < 30) {
                return "Overweight";
            } 
            else {
                return "Obesity";
            }
        } 
        else {
            // Children and teenagers: use CDC growth charts for accurate categorization
            // Placeholder logic for demonstration (actual implementation requires percentile data)
            if (BMI < 5) {
                return "Underweight";
            } 
            else if (BMI < 85) {
                return "Normal weight";
            } 
            else if (BMI < 95) {
                return "Overweight";
            } 
            else {
                return "Obesity";
            }
        }
    }
}
