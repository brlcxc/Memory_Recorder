package DashBoard;

import Defaults.Colors;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddEmergencyContact extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private final ResultSet resultSet;
    private final Connection connection;
    private final JTextField nameTextField = new JTextField();
    private final JTextField relationshipTextField = new JTextField();
    private final JTextField phoneNumTextField = new JTextField();
    private final JTextField emailTextField =  new JTextField();

    public AddEmergencyContact(Connection con, ResultSet result){
        connection = con;
        resultSet = result;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Add a new emergency contact");
        this.setBackground(Colors.pastelGreen);
        this.add(newContactPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private JPanel newContactPanel(){
        JPanel newContactPanel = new JPanel();
        newContactPanel.setBackground(Colors.mintGreen);
        newContactPanel.setLayout(new GridLayout(6,1));
        newContactPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JLabel titleLabel = new JLabel("New Contact:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        newContactPanel.add(titleLabel);
        newContactPanel.add(addInfoPanel("Name:                    ", nameTextField));
        newContactPanel.add(addInfoPanel("Relationship:        ", relationshipTextField));
        newContactPanel.add(addInfoPanel("Phone Number:    ", phoneNumTextField));
        newContactPanel.add(addInfoPanel("Email address:      ", emailTextField));
        newContactPanel.add(buttonPanel());

        return newContactPanel;
    }

    private JPanel addInfoPanel(String labelName,JTextField textFieldName){
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        infoPanel.setBackground(Colors.mintGreen);
        infoPanel.setAlignmentX(0);
        JLabel label = new JLabel(labelName);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        textFieldName.setPreferredSize(new Dimension(200,30));
        infoPanel.add(label);
        infoPanel.add(textFieldName);
        return infoPanel;
    }

    private JPanel buttonPanel (){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.mintGreen);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.setBackground(Colors.textColor);
        cancel .setBackground(Colors.textColor);

        save.setForeground(Colors.pastelGreen);
        cancel.setForeground(Colors.pastelGreen);

        save.addActionListener(new saveListener());
        cancel.addActionListener(new cancelListener());

        buttonPanel.add(cancel);
        buttonPanel.add(Box.createHorizontalStrut(15));
        buttonPanel.add(save);

        return buttonPanel;
    }
    public class saveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameTextField.getText();
            String relationship = relationshipTextField.getText();
            String phoneNumber = phoneNumTextField.getText();
            String email = emailTextField.getText();
            if (!name.isEmpty() && !relationship.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
                try {
                    String username = resultSet.getString("username");
                    Statement stm = connection.createStatement();
                    String sql = "Insert into emergency_contact (username,relationship, name, phone_number, email) " +
                            "Values ('" + username + "','" + relationship + "','" + name + "','" + phoneNumber + "','" + email + "')";
                    stm.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "The contact is added");
                    hideFrame();
                    EmergencyContact.allContactPanel.setVisible(false);
                    EmergencyContact.allContactPanel.removeAll();
                    EmergencyContact.allContactPanel();
                    EmergencyContact.allContactPanel.setVisible(true);
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill out all the information");
            }
        }
    }
    public class cancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideFrame();
        }
    }
    private void hideFrame(){
        this.setVisible(false);
    }
}
