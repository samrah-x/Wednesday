package GMS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Equipment {
    
    private Connection connection;
    private Scanner scanner;

    public Equipment(Connection connection, Scanner scanner) {
        this.connection = connection;
        // this.scanner = scanner;
    }

    public void viewEquipment() {
        // read operation 
        String query = "select * from equipment";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+---------------+-------------------------+--------------------+");
            System.out.println("| Equipment ID  | Name                    | Company            |");
            System.out.println("+---------------+-------------------------+--------------------+");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int company = resultSet.getInt("company");
                System.out.printf("|%-15d|%-25s|%-20s|\n", id, name, company);
                System.out.println("+---------------+-------------------------+--------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkEquipmentByName(String name) {
        String query = "SELECT * from trainers WHERE name = ?";

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
}
