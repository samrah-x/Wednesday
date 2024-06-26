package GMS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



// RENAME AS MEMBER
public class Member {
    
    private Connection connection;
    private Scanner scanner;

    Booking booking = new Booking(connection, scanner);

    private double weight;
    private double height;

    public Member(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addMember() {
        JFrame frame = new JFrame("Add New Member");

        // Create labels and fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);

        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightField = new JTextField(20);

        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField(20);

        // Create add member button
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());
                    String gender = (String) genderComboBox.getSelectedItem();
                    double weight = Double.parseDouble(weightField.getText().trim());
                    double height = Double.parseDouble(heightField.getText().trim());
                    double BMI = calculateBMI(weight, height);

                    String query = "INSERT INTO members(name, age, gender, weight, height, BMI) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, age);
                    preparedStatement.setString(3, gender);
                    preparedStatement.setDouble(4, weight);
                    preparedStatement.setDouble(5, height);
                    preparedStatement.setDouble(6, BMI);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(frame, "Member added successfully!");
                        frame.dispose(); // Close the popup
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add member.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for age, weight, and height.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create panel for member components
        JPanel memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayout(6, 2, 10, 10));
        memberPanel.add(nameLabel);
        memberPanel.add(nameField);
        memberPanel.add(ageLabel);
        memberPanel.add(ageField);
        memberPanel.add(genderLabel);
        memberPanel.add(genderComboBox);
        memberPanel.add(weightLabel);
        memberPanel.add(weightField);
        memberPanel.add(heightLabel);
        memberPanel.add(heightField);
        memberPanel.add(new JLabel()); // Empty label for spacing
        memberPanel.add(addMemberButton);

        // Add member panel to frame
        frame.add(memberPanel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to main application
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }
 
    
    public void viewMembers() {
        JFrame frame = new JFrame("View Members");

        // Define column names
        String[] columnNames = {"ID", "Name", "Age", "Gender", "Weight", "Height", "BMI"};

        // Fetch data from the database
        String query = "SELECT id, name, age, gender, weight, height, BMI FROM members";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get data from the result set and store in a list
            java.util.List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[7];
                row[0] = String.valueOf(resultSet.getInt("id"));
                row[1] = resultSet.getString("name");
                row[2] = String.valueOf(resultSet.getInt("age"));
                row[3] = resultSet.getString("gender");
                row[4] = String.valueOf(resultSet.getDouble("weight"));
                row[5] = String.valueOf(resultSet.getDouble("height"));
                row[6] = String.valueOf(resultSet.getDouble("BMI"));
                data.add(row);
            }

            // Convert list to array
            String[][] dataArray = data.toArray(new String[0][]);

            // Create JTable with fetched data
            JTable table = new JTable(dataArray, columnNames);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFillsViewportHeight(true);

            // Add JTable to JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);

            // Set frame properties
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null); // Center the frame on screen
            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openMemberMenu() {
        JFrame frame = new JFrame("Member Menu");

        // Create labels and fields
        JLabel nameLabel = new JLabel("Enter Member Name:");
        JTextField nameField = new JTextField(20);

        // Create button to get member data
        JButton getMemberButton = new JButton("Get Member Data");
        getMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    displayMemberData(name);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a member name.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create button to add booking
        JButton addBookingButton = new JButton("Get Booking");
        addBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                booking.addBooking();
            }
        });

        // Create panel for member menu components
        JPanel memberPanel = new JPanel();
        memberPanel.setLayout(new GridLayout(3, 2, 10, 10));
        memberPanel.add(nameLabel);
        memberPanel.add(nameField);
        memberPanel.add(getMemberButton);
        memberPanel.add(addBookingButton);

        // Add member panel to frame
        frame.add(memberPanel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to main application
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    private void displayMemberData(String name) {
        JFrame dataFrame = new JFrame("Member Data");

        // Fetch data from the database
        String query = "SELECT * FROM members WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String[] columnNames = {"ID", "Name", "Age", "Gender", "Weight", "Height", "BMI"};
                String[][] data = new String[1][7];
                data[0][0] = String.valueOf(resultSet.getInt("id"));
                data[0][1] = resultSet.getString("name");
                data[0][2] = String.valueOf(resultSet.getInt("age"));
                data[0][3] = resultSet.getString("gender");
                data[0][4] = String.valueOf(resultSet.getDouble("weight"));
                data[0][5] = String.valueOf(resultSet.getDouble("height"));
                data[0][6] = String.valueOf(resultSet.getDouble("BMI"));

                JTable table = new JTable(data, columnNames);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setFillsViewportHeight(true);

                JScrollPane scrollPane = new JScrollPane(table);
                dataFrame.add(scrollPane);

                // Set frame properties
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(800, 400);
                dataFrame.setLocationRelativeTo(null); // Center the frame on screen
                dataFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dataFrame, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
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

    public static double calculateBMI(double weight, double height) {
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
