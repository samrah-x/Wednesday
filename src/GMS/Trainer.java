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

import java.util.ArrayList;
import java.util.Calendar;

public class Trainer {
    
    private Connection connection;
    private Scanner scanner;

    public Trainer(Connection connection, Scanner scanner) {
        this.connection = connection;
        // this.scanner = scanner;
    }

    public void addTrainer() {
        JFrame frame = new JFrame("Add New Trainer");

        // Create labels and fields
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        // Create add trainer button
        JButton addTrainerButton = new JButton("Add Trainer");
        addTrainerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);

                    String query = "INSERT INTO trainers(name, sinceWhen) VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, year);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(frame, "Trainer added successfully!");
                        frame.dispose(); // Close the popup
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add trainer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create panel for trainer components
        JPanel trainerPanel = new JPanel();
        trainerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        trainerPanel.add(nameLabel);
        trainerPanel.add(nameField);
        trainerPanel.add(new JLabel()); // Empty label for spacing
        trainerPanel.add(addTrainerButton);

        // Add trainer panel to frame
        frame.add(trainerPanel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to main application
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    public void viewTrainers() {
        JFrame frame = new JFrame("View Trainers");

        // Define column names
        String[] columnNames = {"Trainer ID", "Name", "Hired Since"};

        // Fetch data from the database
        String query = "SELECT id, name, hiredSince FROM trainers";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get data from the result set and store in a list
            java.util.List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[3];
                row[0] = String.valueOf(resultSet.getInt("id"));
                row[1] = resultSet.getString("name");
                row[2] = String.valueOf(resultSet.getInt("hiredSince"));
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
