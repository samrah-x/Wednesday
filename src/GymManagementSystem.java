import GMS.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import GMS.Accounts;
import GMS.Equipment;
import GMS.Member;
import GMS.Trainer;

public class GymManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/gym";
    private static final String username = "root";
    private static final String password = "MYpassw0rd!";
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            
            Member member = new Member(connection, scanner);
            Trainer trainer = new Trainer(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            Equipment equipment = new Equipment(connection, scanner);
            
            while(true) {
                // Welcome Page 
                // options for login and signup 
                // page with entry for username and password
                if (option == "Login") {
                    accounts.login();
                } else {
                    accounts.signup();
                    if (accountType == "Member") {
                        // alter the add member for gui
                        member.addMember();
                    } 
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
}
