package DashBoard;

import Defaults.Colors;
import Main.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;

public class InformationPanel extends JPanel {
    private final Connection connection;
    private  ResultSet resultSet;
    private JFrame mainFrame;
    private JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    public InformationPanel(Connection con, ResultSet result, JFrame frame) {
        connection = con;
        resultSet = result;
        mainFrame = frame;
        setInformationPanel();
    }
    public JPanel getInformationPanel() {
        return informationPanel;
    }
    private void setInformationPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        informationPanel.setBackground(Colors.cream);
        informationPanel.setLayout(new GridBagLayout());
        informationPanel.setPreferredSize(new Dimension(500, 450));

        JLabel titleLabel = new JLabel("My Information:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;

        informationPanel.add(titleLabel, gbc);
        try {
            String username = resultSet.getString("username");
            JTextField passwordField = new DashBoardPassword(resultSet.getString("password"));
            JTextField firstNameField = new DashTextField(resultSet.getString("first_name"));
            JTextField middleNameField = new DashTextField(resultSet.getString("middle_name"));
            JTextField lastNameField = new DashTextField(resultSet.getString("last_name"));
            JTextField dobField = new DashTextField(resultSet.getString("date_of_birth"));
            JTextField emailField = new DashTextField(resultSet.getString("email"));
            JTextField addressField = new DashTextField(resultSet.getString("address"));
            JTextField phoneNumberField = new DashTextField(resultSet.getString("phone_number"));
            gbc.gridy = -1;
            gbc.gridwidth = 1;
            gbc.weightx = 0.05;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.PAGE_START;
            informationPanel.add(new DashBoardLabel("Account:"), gbc);
            gbc.gridy = -2;
            informationPanel.add(new DashBoardLabel("Password:"), gbc);
            gbc.gridy = -3;
            informationPanel.add(new DashBoardLabel("First Name:"), gbc);
            gbc.gridy = -4;
            informationPanel.add(new DashBoardLabel("Middle Name:"), gbc);
            gbc.gridy = -5;
            informationPanel.add(new DashBoardLabel("Last Name:"), gbc);
            gbc.gridy = -6;
            informationPanel.add(new DashBoardLabel("Date of Birth:"), gbc);
            gbc.gridy = -7;
            informationPanel.add(new DashBoardLabel("Email:"), gbc);
            gbc.gridy = -8;
            informationPanel.add(new DashBoardLabel("Address:"), gbc);
            gbc.gridy = -9;
            informationPanel.add(new DashBoardLabel("Phone Number:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = -1;
            gbc.weightx = 0.9;
            informationPanel.add(new DashBoardLabel(username), gbc);
            gbc.gridy = -2;
            informationPanel.add(passwordField, gbc);
            gbc.gridy = -3;
            informationPanel.add(firstNameField, gbc);
            gbc.gridy = -4;
            informationPanel.add(middleNameField, gbc);
            gbc.gridy = -5;
            informationPanel.add(lastNameField, gbc);
            gbc.gridy = -6;
            informationPanel.add(dobField, gbc);
            gbc.gridy = -7;
            informationPanel.add(emailField, gbc);
            gbc.gridy = -8;
            informationPanel.add(addressField, gbc);
            gbc.gridy = -9;
            informationPanel.add(phoneNumberField, gbc);

            gbc.anchor = GridBagConstraints.PAGE_END;
            gbc.gridx = 2;
            gbc.gridy = -1;
            gbc.weightx = 0.05;
            informationPanel.add(AccountPanel(username), gbc);
            gbc.gridy = -2;
            informationPanel.add(passwordPanel2(passwordField), gbc);
            gbc.gridy = -3;
            informationPanel.add(TextModifyPanel(firstNameField, "first_name"), gbc);
            gbc.gridy = -4;
            informationPanel.add(TextModifyPanel(middleNameField, "middle_name"), gbc);
            gbc.gridy = -5;
            informationPanel.add(TextModifyPanel(lastNameField, "last_name"), gbc);
            gbc.gridy = -6;
            informationPanel.add(dobRowPanel2(dobField), gbc);
            gbc.gridy = -7;
            informationPanel.add(TextModifyPanel(emailField, "email"), gbc);
            gbc.gridy = -8;
            informationPanel.add(TextModifyPanel(addressField, "address"), gbc);
            gbc.gridy = -9;
            informationPanel.add(TextModifyPanel(phoneNumberField, "phone_number"), gbc);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private JPanel AccountPanel(String username){
        JPanel subRowPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subRowPanel2.setBackground(Colors.cream);
        subRowPanel2.add(deleteAccountButton(username));
        return subRowPanel2;
    }
    private JPanel TextModifyPanel(JTextField textFieldName, String colName){
        JPanel subRowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subRowPanel.setBackground(Colors.cream);
        subRowPanel.add(editButton(textFieldName));
        subRowPanel.add(saveButton(textFieldName, colName));
        subRowPanel.add(cancelButton(textFieldName, colName));
        return subRowPanel;
    }
    static JPanel leftRowPanel(String labelName, JTextField textFieldName){
        JPanel subRowPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subRowPanel1.setBackground(Colors.cream);
        JLabel label = new JLabel(labelName);
        textFieldName.setPreferredSize(new Dimension(200, 30));
        textFieldName.setBackground(Colors.cream);
        textFieldName.setBorder(BorderFactory.createEmptyBorder());
        textFieldName.setEditable(false);
        subRowPanel1.add(label);
        subRowPanel1.add(textFieldName);
        return subRowPanel1;
    }
    private JButton deleteAccountButton (String username){
        JButton deleteAccountButton = iconButton("src/Defaults/IconImages/delete.png",15,15);
        deleteAccountButton.setToolTipText("Delete this account");
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int option = JOptionPane.showConfirmDialog(null,"Are you sure you want delete this account?", "Delete?",JOptionPane.YES_NO_OPTION);
               if (option == JOptionPane.YES_OPTION){
                   try {
                       Statement stmt = connection.createStatement();
                       String sql = "DELETE FROM customer WHERE username = '"+username+"'";
                       stmt.executeUpdate(sql);
                       JOptionPane.showMessageDialog(null, "This account is deleted");
                       mainFrame.setVisible(false);
                        new Login();
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }
               }
            }
        });
        return deleteAccountButton;
    }
    private JButton editButton(JTextField textFieldName) {
        JButton editButton = iconButton("src/Defaults/IconImages/editing.png",15,15);
        editButton.setToolTipText("Edit information");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldName.setEditable(true);
                textFieldName.setBorder(BorderFactory.createLineBorder(Colors.pastelPurple, 1));
            }
        });
        return editButton;
    }
    private JButton saveButton(JTextField textFieldName, String colName) {
        JButton saveButton = iconButton("src/Defaults/IconImages/save.png",13,13);
        saveButton.setToolTipText("Save and update new change");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newInfo = textFieldName.getText();
                try{
                    if (!newInfo.equals(resultSet.getString(colName))) {
                        JOptionPane.showMessageDialog(null, "It's updated");

                        String username = resultSet.getString("username");
                        Statement stmt = connection.createStatement();
                        String sql = "UPDATE customer SET " + colName + " = '" + newInfo + "' WHERE username = '" + resultSet.getString("username") + "'";
                        stmt.executeUpdate(sql);
                        sql = "SELECT * FROM customer WHERE username = '" + username + "'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                textFieldName.setBorder(BorderFactory.createEmptyBorder());
                textFieldName.setEditable(false);
            }
        });
        return saveButton;
    }
    private JButton cancelButton (JTextField textFieldName, String colName){
        JButton cancelButton = iconButton("src/Defaults/IconImages/cancel.png",15,15);
        cancelButton.setToolTipText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textFieldName.setText(resultSet.getString(colName));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                textFieldName.setEditable(false);
                textFieldName.setBorder(BorderFactory.createEmptyBorder());
            }
        });
        return cancelButton;
    }
    static JButton iconButton(String icon, int width, int height){
//        ImageIcon image = new ImageIcon(Objects.requireNonNull(InformationPanel.class.getResource(icon)));
        ImageIcon image = new ImageIcon(icon);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton iconButton = new JButton(new ImageIcon(newImg));
        iconButton.setPreferredSize(new Dimension(30, 30));
        iconButton.setBorder(BorderFactory.createEmptyBorder());
        iconButton.setBackground(Colors.cream);
        iconButton.setFocusable(false);
        return iconButton;
    }

    private JPanel passwordPanel2 (JTextField textFieldName){
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setLayout(new BorderLayout());
        passwordPanel.setBackground(Colors.cream);
        passwordPanel.setPreferredSize(new Dimension(500, 30));

        JButton changePasswordButton = new JButton("Change password");
        changePasswordButton.setBackground(Colors.cream);
        changePasswordButton.setBorder(BorderFactory.createEmptyBorder());
        changePasswordButton.setPreferredSize(new Dimension(120,30));
        changePasswordButton.setFocusable(false);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ChangePassword(resultSet, connection, textFieldName);
                    String username = resultSet.getString("username");
                    String sql = "SELECT * FROM customer WHERE username = '" + username + "'";
                    Statement stmt = connection.createStatement();
                    resultSet = stmt.executeQuery(sql);
                    resultSet.next();
                    textFieldName.setText(resultSet.getString("password"));
                }catch(SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        changePasswordButton.addMouseListener(new MouseAdapter() {
            Font original = null;
            @Override
            public void mouseEntered(MouseEvent e) {
                original = e.getComponent().getFont();
                Map attributes = original. getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                e.getComponent().setFont(original.deriveFont(attributes));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                Map attributes = original. getAttributes();
                attributes.put(TextAttribute.UNDERLINE, null);
                e.getComponent().setFont(original.deriveFont(attributes));
            }
        });

        JPanel subRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subRowPanel.setBackground(Colors.cream);
        subRowPanel.add(changePasswordButton);

//        passwordPanel.add(leftRowPanel(labelName,textFieldName), BorderLayout.WEST);
        passwordPanel.add(subRowPanel, BorderLayout.EAST);

        return passwordPanel;
    }
    private JPanel dobRowPanel2(JTextField textFieldName) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.setLayout(new BorderLayout());
        rowPanel.setBackground(Colors.cream);
        rowPanel.setPreferredSize(new Dimension(500, 35));

        JButton editDobButton = iconButton("src/Defaults/IconImages/editing.png",15,15);
        editDobButton.setToolTipText("Edit information");
        editDobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeDob( resultSet,connection, textFieldName);
            }
        });

        JPanel subRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subRowPanel.setBackground(Colors.cream);
        subRowPanel.add(editDobButton);
        rowPanel.add(subRowPanel, BorderLayout.EAST);
        return rowPanel;
    }
}
