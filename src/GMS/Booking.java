package GMS;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Booking {
    private Connection connection;
    private Scanner scanner;

    public Booking(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addBooking() {
        JFrame frame = new JFrame("Add Booking");

        // Create labels and fields
        JLabel trainerNameLabel = new JLabel("Enter Trainer Name:");
        JTextField trainerNameField = new JTextField(20);
        JLabel memberNameLabel = new JLabel("Enter Member Name:");
        JTextField memberNameField = new JTextField(20);
        JLabel bookingDateLabel = new JLabel("Enter Booking Date (YYYY-MM-DD):");
        JTextField bookingDateField = new JTextField(20);

        // Create button to save booking
        JButton saveButton = new JButton("Save Booking");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String trainerName = trainerNameField.getText().trim();
                String memberName = memberNameField.getText().trim();
                String bookingDate = bookingDateField.getText().trim();

                if (!trainerName.isEmpty() && !memberName.isEmpty() && !bookingDate.isEmpty()) {
                    try {
                        String query = "INSERT INTO bookings(trainerName, memberName, bookingDate) VALUES (?,?,?)";
                        PreparedStatement preparedstatement = connection.prepareStatement(query);
                        preparedstatement.setString(1, trainerName);
                        preparedstatement.setString(2, memberName);
                        preparedstatement.setString(3, bookingDate);
                        int affectedRows = preparedstatement.executeUpdate();
                        if (affectedRows > 0) {
                            JOptionPane.showMessageDialog(frame, "Booking Added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to add Booking.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error occurred while adding booking.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create panel for booking form components
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new GridLayout(4, 2, 10, 10));
        bookingPanel.add(trainerNameLabel);
        bookingPanel.add(trainerNameField);
        bookingPanel.add(memberNameLabel);
        bookingPanel.add(memberNameField);
        bookingPanel.add(bookingDateLabel);
        bookingPanel.add(bookingDateField);
        bookingPanel.add(new JLabel());
        bookingPanel.add(saveButton);

        // Add booking panel to frame
        frame.add(bookingPanel);

        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to main application
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }


    public void viewBookings() {
        JFrame frame = new JFrame("View Bookings");

        // Define column names
        String[] columnNames = {"ID", "Trainer Name", "Member Name", "Booking Date"};

        // Fetch data from the database
        String query = "SELECT id, trainerName, memberName, bookingDate FROM bookings";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get data from the result set and store in a list
            java.util.List<String[]> data = new ArrayList<>();
            while (resultSet.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(resultSet.getInt("id"));
                row[1] = resultSet.getString("trainerName");
                row[2] = resultSet.getString("memberName");
                row[3] = resultSet.getString("bookingDate");
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
}