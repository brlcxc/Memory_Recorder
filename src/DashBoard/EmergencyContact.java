package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class EmergencyContact extends JPanel {
    private final Connection connection;
    private ResultSet resultSet;
    private JPanel emergencyContactPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public EmergencyContact(Connection conn, ResultSet result){
        connection=conn;
        resultSet=result;
        setEmergencyContact();
    }
    private void setEmergencyContact(){
        emergencyContactPanel.setBackground(Colors.cream);
        emergencyContactPanel.setLayout(new GridLayout(10, 1));
        emergencyContactPanel.setPreferredSize(new Dimension(500, 450));

        JLabel titleLabel = new JLabel("Emergency Contact:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        emergencyContactPanel.add(titleLabel);
//        try {
//            String username =resultSet.getString("username");
//            JPasswordField passwordField = new JPasswordField(resultSet.getString("password"));
//            JTextField firstNameField = new JTextField(resultSet.getString("first_name"));
//            JTextField middleNameField = new JTextField(resultSet.getString("middle_name"));
//            JTextField lastNameField = new JTextField(resultSet.getString("last_name"));
//            JTextField dobField = new JTextField(resultSet.getString("date_of_birth"));
//            JTextField emailField = new JTextField(resultSet.getString("email"));
//            JTextField addressField = new JTextField(resultSet.getString("address"));
//            JTextField phoneNumberField = new JTextField(resultSet.getString("phone_number"));
//            informationPanel.add(accountPanel(username));
//            informationPanel.add(passwordPanel("Password:          ", passwordField));
//            informationPanel.add(rowPanel("First name:         ", firstNameField, "first_name"));
//            informationPanel.add(rowPanel("Middle Name:    ", middleNameField, "middle_name"));
//            informationPanel.add(rowPanel("Last Name:         ", lastNameField, "last_name"));
//            informationPanel.add(dobRowPanel("Date of birth:     ", dobField));
//            informationPanel.add(rowPanel("Email:                  ", emailField, "email"));
//            informationPanel.add(rowPanel("Address:             ", addressField,"address"));
//            informationPanel.add(rowPanel("Phone number: ", phoneNumberField,"phone_number"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

    }
    public JPanel getEmergencyContactPanel(){
        return emergencyContactPanel;
    }
}
