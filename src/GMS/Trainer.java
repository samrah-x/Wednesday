package GMS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Calendar;

public class Trainer {
    
    private Connection connection;
    private Scanner scanner;

    public Trainer(Connection connection, Scanner scanner) {
        this.connection = connection;
        // this.scanner = scanner;
    }

    public void addTrainer() {
        System.out.print("Enter Trainer Name: ");
        String name = scanner.next();
        // System.out.print("Since when: ");
        // set curremt year as since when
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        
        try {
            String query = "INSERT into trainers(name, sinceWhen) values (?,?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, name);
            preparedstatement.setInt(2, year);
            int affectedRows = preparedstatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Trainer Added successfully!");
            }
            else {
                System.out.println("Failed to add Trainer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewTrainers() {
        // read operation 
        String query = "select * from trainers";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+-------------+----------------------+---------------+");
            System.out.println("| Trainer ID  | Name                 | Hired Since   |");
            System.out.println("+-------------+----------------------+---------------+");
            while(resultSet.next()) {
                int trainerId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int hiredSince = resultSet.getInt("hiredSince");
                System.out.printf("|%-13d|%-22s|%-15d|\n", trainerId, name, hiredSince);
                System.out.println("+-------------+-----------------------+----------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTrainerByName(String name) {
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

    public void addEquipment() {
        System.out.print("Enter Equipment Name: ");
        String name = scanner.next();
        System.out.print("Enter Equipment Company: ");
        String company = scanner.next();

        try {
            String query = "INSERT into equipment(name, company) values (?,?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, name);
            preparedstatement.setString(2, company);
            int affectedRows = preparedstatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Equipment Added successfully!");
            }
            else {
                System.out.println("Failed to add Equipment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
