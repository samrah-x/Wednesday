import GMS.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GymManagementSystem {
    final private static Font mainFont = new Font("Arial", Font.BOLD, 20);
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
            
            // Member member = new Member(connection, scanner);
            // Trainer trainer = new Trainer(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            Equipment equipment = new Equipment(connection, scanner);
            // Booking booking = new Booking(connection, scanner);
            
            while(true) {
                // Welcome Page
                JFrame frame = new JFrame("Gym Management System");
                
                // Create welcome label
                JLabel welcomeLabel = new JLabel("WELCOME TO GYM MANAGEMENT SYSTEM");
                welcomeLabel.setFont(mainFont);
                welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                JButton loginButton = new JButton("Login");
                JButton signupButton = new JButton("Signup");
                
                // Add action listeners to buttons
                loginButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Add your login logic here
                        JOptionPane.showMessageDialog(frame, "Redirecting to Login Page");
                        accounts.login();
                    }
                });
                
                signupButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frame, "Redirecting to Signup Page");
                        accounts.signup();
                    }
                });
                
                // Create panel for buttons
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
                buttonPanel.add(loginButton);
                buttonPanel.add(signupButton);
                
                // Create main panel for frame
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(welcomeLabel, BorderLayout.CENTER);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);
                
                // Add main panel to frame
                frame.add(mainPanel);
                
                // Set frame properties
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 200);
                frame.setLocationRelativeTo(null); // Center the frame on screen
                frame.setVisible(true);
            
                
                
                if(accountType == "Member") {
                    // display fields
                    // button for get booking
                    booking.addBooking();
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
}
