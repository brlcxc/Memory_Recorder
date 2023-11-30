package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmergencyContact extends JPanel {
    private static Connection connection = null;
    private static ResultSet resultSet = null;
    public static JPanel emergencyContactPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    public static JPanel allContactPanel = new JPanel();

    public EmergencyContact(Connection conn, ResultSet result){
        connection=conn;
        resultSet=result;
        setEmergencyContact();
    }
    public void setEmergencyContact(){

        emergencyContactPanel.setBackground(Colors.cream);
        emergencyContactPanel.setLayout(new BorderLayout());
        emergencyContactPanel.setPreferredSize(new Dimension(470, 450));
        emergencyContactPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel titleLabel = new JLabel("Emergency Contact:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        emergencyContactPanel.add(titleLabel, BorderLayout.NORTH);
        allContactPanel.removeAll();
        allContactPanel();
        emergencyContactPanel.add(allContactPanel, BorderLayout.CENTER);
        emergencyContactPanel.add(addContactButton(), BorderLayout.SOUTH);
    }
    public static void allContactPanel(){
        allContactPanel.setBackground(Colors.cream);
        allContactPanel.setLayout(new GridLayout(0,1,0,5));
        allContactPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try {
            String username =resultSet.getString("username");
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM emergency_contact WHERE username= '"+username +"'";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                JTextField nameTextField = new JTextField(result.getString("name"));
                JTextField emailTextField = new JTextField(result.getString("email"));
                String relationship= result.getString("relationship");
                JTextField phoneNumberTextField = new JTextField(result.getString("phone_number"));
                allContactPanel.add(displayContactPanel(relationship, nameTextField, emailTextField, phoneNumberTextField));
            }
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    public JButton addContactButton(){
        JButton addContact = new JButton("Add a new emergency contact");
        addContact.addActionListener(new addNewContactListener());
        addContact.setPreferredSize(new Dimension(100,30));
        addContact.setBackground(Colors.pastelPurple);
        addContact.setBorder(BorderFactory.createLineBorder(Colors.pastelPurple));
        addContact.setFocusable(false);
        addContact.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(Colors.mintGreen);
            }
            public void mouseExited(MouseEvent e) {
                setBackground(Colors.pastelPurple);
            }
        });
        return  addContact;
    }
    public JPanel getEmergencyContactPanel(){
        setEmergencyContact();
        return emergencyContactPanel;
    }

    public static class addNewContactListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new AddEmergencyContact(connection, resultSet);
        }
    }
    private static JPanel displayContactPanel(String relationship, JTextField nameTextField, JTextField emailTextField, JTextField phoneNumberTextField){
        JPanel displayContactPanel = new JPanel();
        displayContactPanel.setLayout(new GridLayout(4,1));
        displayContactPanel.setBackground(Colors.cream);
        displayContactPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Colors.textColor,1),relationship));
        displayContactPanel.add(InformationPanel.leftRowPanel("Name:               ", nameTextField ));
        displayContactPanel.add(InformationPanel.leftRowPanel("Email:                ", emailTextField));
        displayContactPanel.add(InformationPanel.leftRowPanel("Phone number: ", phoneNumberTextField));
        displayContactPanel.add(buttonPanel(emailTextField));
        return displayContactPanel;
    }
    private static JPanel buttonPanel(JTextField emailField){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.cream);
        JButton deleteButton = InformationPanel.iconButton("delete.png",15,15);
        deleteButton.setToolTipText("delete this relationship");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null,"Are you sure you want delete this contact?", "Delete?",JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION){
                    try {
                        String username =resultSet.getString("username");
                        String email = emailField.getText();
                        Statement stmt = connection.createStatement();
                        String sql = "DELETE FROM emergency_contact WHERE username = '"+username+"' and email ='"+email+"'";
                        stmt.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null, "The contact is deleted");
                        allContactPanel.setVisible(false);
                        allContactPanel.removeAll();
                        allContactPanel();
                        allContactPanel.setVisible(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }
}
