import GMS.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
    final private Font mainFont = new Font("Arial", Font.BOLD, 20);

    public void welcomePage(){
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
    }

    public void LoginPage() {
        JFrame frame = new JFrame("Login");
        
        // Create labels and fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                if (checkAccountByUsername(username, password)) {
                    System.out.println("Login Success!");
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
        
    public void SignupPage() {
        JFrame frame = new JFrame("Signup");
        
        // Create labels and fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        // Create signup button
        JButton signupButton = new JButton("Signup");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                
                
            }
        });
        
        // Create panel for signup components
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridLayout(4, 2, 10, 10));
        signupPanel.add(usernameLabel);
        signupPanel.add(usernameField);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordField);
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

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
        myFrame.initialize();
    }
}
