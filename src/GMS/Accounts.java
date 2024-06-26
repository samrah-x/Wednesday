package GMS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    Member member = new Member(connection, scanner);
    Trainer trainer = new Trainer(connection, scanner);

    // in main add addmember function
    public void signup() {
        JFrame frame;
        JRadioButton memberRadioButton;
        JRadioButton trainerRadioButton;
    
        frame = new JFrame("Signup");
        
        // Create labels and fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        // Create radio buttons
        memberRadioButton = new JRadioButton("Member");
        trainerRadioButton = new JRadioButton("Trainer");
        
        // Group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(memberRadioButton);
        group.add(trainerRadioButton);
        
        // Create signup button
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String memberType = (memberRadioButton.isSelected()) ? "Member" : "Trainer";
                
                addAccount(username, password);

                if(memberType == "Member") {
                    member.addMember();
                }
                else {
                    trainer.addTrainer();
                }
            }
        });
        
        // Create panel for signup components
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(4, 2, 10, 10));
        signupPanel.add(usernameLabel);
        signupPanel.add(usernameField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);
        signupPanel.add(memberRadioButton);
        signupPanel.add(trainerRadioButton);
        signupPanel.add(new JLabel()); // Empty label for spacing
        signupPanel.add(signupButton);
        
        // Add signup panel to frame
        frame.add(signupPanel);
        
        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to welcome page
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }


    public void login () {
        JFrame frame = new JFrame("Login");
        JRadioButton memberRadioButton;
        JRadioButton trainerRadioButton;
        
        // Create labels and fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        // Create radio buttons
        memberRadioButton = new JRadioButton("Member");
        trainerRadioButton = new JRadioButton("Trainer");

        // Group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(memberRadioButton);
        group.add(trainerRadioButton);
        
        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String memberType = (memberRadioButton.isSelected()) ? "Member" : "Trainer";
                
                
                if (checkAccountByUsername(username, password)) {
                    System.out.println("Login Success!");
                    if(memberType == "Member") {
                        // open member mainpage
                        member.openMemberMenu();
                    } else {
                        // open trainer mainpage
                        trainer.openTrainerMenu();
                    }
                }
                else {
                    System.out.println("Login Unsuccessful!");
                }
                
            }
        });
        
        // Create panel for login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(loginButton);
        
        // Add login panel to frame
        frame.add(loginPanel);
        
        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to welcome page
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
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
