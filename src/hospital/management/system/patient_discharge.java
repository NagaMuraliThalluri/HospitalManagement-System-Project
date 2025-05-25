
package hospital.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import java.text.SimpleDateFormat;
public class patient_discharge extends JFrame {
    patient_discharge() {
        getContentPane().setBackground(new Color(176, 224, 230)); // Light blue background
        JPanel panel = new JPanel();
        panel.setBounds(50, 50, 400, 300); // Adjusted panel position and size
        panel.setBackground(Color.WHITE);
        panel.setLayout(null); // Use null layout for precise positioning
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a black border
        add(panel);

        JLabel label = new JLabel("CHECK-OUT");
        label.setBounds(150, 20, 100, 25); // Adjusted label position and size
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);
        JLabel label2 = new JLabel("Customer ID:");
        label2.setBounds(30, 70, 100, 20);
        label2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label2.setForeground(Color.BLACK);
        panel.add(label2);
        Choice choice = new Choice();
        choice.setBounds(150, 70, 150, 25);
        panel.add(choice);
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("select * from Patient_Info");
            while (resultSet.next()) {
                choice.add(resultSet.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JLabel label3 = new JLabel("Room Number:");
        label3.setBounds(30, 110, 100, 20);
        label3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label3.setForeground(Color.BLACK);
        panel.add(label3);
        JLabel RNo = new JLabel();
        RNo.setBounds(150, 110, 150, 20);
        RNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        RNo.setForeground(Color.BLACK);
        panel.add(RNo);
        JLabel label4 = new JLabel("In Time:");
        label4.setBounds(30, 150, 100, 20);
        label4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label4.setForeground(Color.BLACK);
        panel.add(label4);
        JLabel INTime = new JLabel();
        INTime.setBounds(150, 150, 200, 20);
        INTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        INTime.setForeground(Color.BLACK);
        panel.add(INTime);
        JLabel label5 = new JLabel("Out Time:");
        label5.setBounds(30, 190, 100, 20);
        label5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label5.setForeground(Color.BLACK);
        panel.add(label5);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        JLabel OUTTime = new JLabel(formattedDate);
        OUTTime.setBounds(150, 190, 200, 20);
        OUTTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
        OUTTime.setForeground(Color.BLACK);
        panel.add(OUTTime);
        JButton discharge = new JButton("Discharge");
        discharge.setBounds(30, 240, 100, 25);
        discharge.setBackground(Color.WHITE);
        discharge.setForeground(Color.BLACK);
        discharge.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(discharge);
        discharge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    c.statement.executeUpdate("delete from Patient_Info where number = '" + choice.getSelectedItem() + "'");
                    c.statement.executeUpdate("update room set Availability = 'Available' where room_no = '" + RNo.getText() + "'");
                    JOptionPane.showMessageDialog(null, "Patient Discharged Successfully");
                    setVisible(false);
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
        JButton Check = new JButton("Check");
        Check.setBounds(150, 240, 100, 25);
        Check.setBackground(Color.WHITE);
        Check.setForeground(Color.BLACK);
        Check.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(Check);
        Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conn c = new conn();
                try {
                    ResultSet resultSet = c.statement.executeQuery("select * from Patient_Info where number ='" + choice.getSelectedItem() + "'");
                    while (resultSet.next()) {
                        RNo.setText(resultSet.getString("Room_Number"));
                        INTime.setText(resultSet.getString("Time"));
                    }
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });
        JButton Back = new JButton("Back");
        Back.setBounds(270, 240, 100, 25);
        Back.setBackground(Color.WHITE);
        Back.setForeground(Color.BLACK);
        Back.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(Back);
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        setUndecorated(true);
        setSize(500, 400); // Adjusted JFrame size
        setLayout(null);
        setLocationRelativeTo(null); // Center the JFrame
        setVisible(true);
    }
    public static void main(String[] args) {
        new patient_discharge();
    }
}