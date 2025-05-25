package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class update_patient_details extends JFrame  {

    update_patient_details () {
        setTitle("Update Patient Details");
        setSize(950, 500);
        setLocation(400, 250);
        setLayout(null);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(50, 50, 940, 490);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/updated.png"));
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(500, 60, 300, 300);
        panel.add(label);

        JLabel label1 = new JLabel("Update Patient Details");
        label1.setBounds(124, 11, 260, 25);
        label1.setFont(new Font("Tahoma", Font.BOLD, 20));
        label1.setForeground(Color.white);
        panel.add(label1);

        JLabel label2 = new JLabel("Name:");
        label2.setBounds(25, 88, 100, 14);
        label2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label2.setForeground(Color.white);
        panel.add(label2);

        Choice choice = new Choice();
        choice.setBounds(248, 85, 140, 25);
        panel.add(choice);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM Patient_Info");
            while (resultSet.next()) {
                choice.add(resultSet.getString("Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel label3 = new JLabel("Room Number:");
        label3.setBounds(25, 129, 100, 14);
        label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label3.setForeground(Color.white);
        panel.add(label3);

        JTextField textFieldR = new JTextField();
        textFieldR.setBounds(248, 129, 140, 20);
        panel.add(textFieldR);

        JLabel label4 = new JLabel("In-Time:");
        label4.setBounds(25, 174, 100, 14);
        label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label4.setForeground(Color.white);
        panel.add(label4);

        JTextField textFieldInTime = new JTextField();
        textFieldInTime.setBounds(248, 174, 140, 20);
        panel.add(textFieldInTime);

        JLabel label5 = new JLabel("Amount Paid (Rs):");
        label5.setBounds(25, 216, 150, 14);
        label5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label5.setForeground(Color.white);
        panel.add(label5);

        JTextField textFieldAmount = new JTextField();
        textFieldAmount.setBounds(248, 216, 140, 20);
        panel.add(textFieldAmount);

        JLabel label6 = new JLabel("Pending Amount (Rs):");
        label6.setBounds(25, 261, 150, 14);
        label6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label6.setForeground(Color.white);
        panel.add(label6);

        JTextField textFieldPending = new JTextField();
        textFieldPending.setBounds(248, 261, 140, 20);
        panel.add(textFieldPending);

        JButton update = new JButton("UPDATE");
        update.setBounds(168, 378, 89, 23);
        update.setBackground(Color.black);
        update.setForeground(Color.white);
        panel.add(update);

        JButton check = new JButton("CHECK");
        check.setBounds(281, 378, 89, 23);
        check.setBackground(Color.black);
        check.setForeground(Color.white);
        panel.add(check);

        JButton back = new JButton("BACK");
        back.setBounds(394, 378, 89, 23);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        panel.add(back);

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = choice.getSelectedItem();
                String q = "SELECT * FROM Patient_Info WHERE Name = ?";
                try {
                    conn c = new conn();
                    PreparedStatement pstmt = c.connection.prepareStatement(q);
                    pstmt.setString(1, id);
                    ResultSet resultSet = pstmt.executeQuery();

                    if (resultSet.next()) {
                        textFieldR.setText(resultSet.getString("Room_Number"));
                        textFieldInTime.setText(resultSet.getString("Time"));
                        textFieldAmount.setText(resultSet.getString("Deposit"));
                    }

                    String roomQuery = "SELECT * FROM room WHERE room_no = ?";
                    PreparedStatement roomStmt = c.connection.prepareStatement(roomQuery);
                    roomStmt.setString(1, textFieldR.getText());
                    ResultSet resultSet1 = roomStmt.executeQuery();

                    if (resultSet1.next()) {
                        String price = resultSet1.getString("Price");
                        int amountPaid = Integer.parseInt(price) - Integer.parseInt(textFieldAmount.getText());
                        textFieldPending.setText(String.valueOf(amountPaid));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientName = choice.getSelectedItem();
                String room = textFieldR.getText().trim();
                String time = textFieldInTime.getText().trim();
                String amount = textFieldAmount.getText().trim();

                // Validate input fields
                if (room.isEmpty() || time.isEmpty() || amount.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                String updateQuery = "UPDATE Patient_Info SET Room_Number = ?, Time = ?, Deposit = ? WHERE Name = ?";

                try {
                    conn c = new conn();
                    PreparedStatement pstmt = c.connection.prepareStatement(updateQuery);
                    pstmt.setString(1, room);
                    pstmt.setString(2, time);
                    pstmt.setString(3, amount);
                    pstmt.setString(4, patientName);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Updated Successfully");
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Update failed. Please check the patient name.");
                    }

                    // Close resources
                    pstmt.close();
                    c.connection.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while updating the patient details.");
                }
            }
        });


        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new update_patient_details();
    }
}
