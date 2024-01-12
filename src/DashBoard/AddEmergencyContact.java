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
    private DashTextField2 nameTextField;
    private DashTextField2 relationshipTextField;
    private DashTextField2 phoneNumTextField;
    private DashTextField2 emailTextField;

    public AddEmergencyContact(Connection con, ResultSet result){
        connection = con;
        resultSet = result;
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Add a new emergency contact");
        this.setBackground(Colors.pastelGreen);
        this.add(newContactPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("src/Defaults/IconImages/icon.png");
        this.setIconImage(img.getImage());
    }

    private JPanel newContactPanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel newContactPanel = new JPanel();
        newContactPanel.setBackground(Colors.mintGreen);
//        newContactPanel.setLayout(new GridLayout(6,1));
        newContactPanel.setLayout(new GridBagLayout());
        newContactPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        nameTextField = new DashTextField2();
        relationshipTextField = new DashTextField2();
        phoneNumTextField = new DashTextField2();
        emailTextField = new DashTextField2();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;

        JLabel titleLabel = new JLabel("New Contact:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        newContactPanel.add(titleLabel, gbc);

        gbc.gridy = -1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        newContactPanel.add(new DashBoardLabel("Name:"), gbc);
        gbc.gridy = -2;
        newContactPanel.add(new DashBoardLabel("Relationship:"), gbc);
        gbc.gridy = -3;
        newContactPanel.add(new DashBoardLabel("Phone Number"), gbc);
        gbc.gridy = -4;
        newContactPanel.add(new DashBoardLabel("Email address:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = -1;
        gbc.weightx = 1;

        newContactPanel.add(nameTextField, gbc);
        gbc.gridy = -2;
        newContactPanel.add(relationshipTextField, gbc);
        gbc.gridy = -3;
        newContactPanel.add(phoneNumTextField, gbc);
        gbc.gridy = -4;
        newContactPanel.add(emailTextField, gbc);

        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = -5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        newContactPanel.add(buttonPanel(), gbc);

/*
        JLabel titleLabel = new JLabel("New Contact:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));



        newContactPanel.add(titleLabel);
        newContactPanel.add(addInfoPanel("Name:                    ", nameTextField));
        newContactPanel.add(addInfoPanel("Relationship:        ", relationshipTextField));
        newContactPanel.add(addInfoPanel("Phone Number:    ", phoneNumTextField));
        newContactPanel.add(addInfoPanel("Email address:      ", emailTextField));
        newContactPanel.add(buttonPanel());*/

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
/*
    private JPanel addInfoPanel2(String labelName){
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
*/

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
