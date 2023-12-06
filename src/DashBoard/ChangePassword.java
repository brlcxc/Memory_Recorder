package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePassword extends JFrame {
    private static final int FRAME_WIDTH = 380;
    private static final int FRAME_HEIGHT = 250;
    private final JPasswordField currentPasswordField = new JPasswordField();
    private final JPasswordField newPasswordField = new JPasswordField();
    private ResultSet resultSet;
    private final Connection connection;
    private final JTextField passwordTextField;
    public ChangePassword (ResultSet result, Connection con, JTextField textField){
        resultSet = result;
        connection = con;
        passwordTextField = textField;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Change password");
        this.setBackground(Colors.textColor);
        this.add(changePasswordPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    private JPanel changePasswordPanel(){
        JPanel changePasswordPanel = new JPanel();
        changePasswordPanel.setBackground(Colors.textColor);
        changePasswordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel();
        JPanel currentPasswordPanel = new JPanel();
        JPanel newPasswordPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setBackground(Colors.textColor);
        currentPasswordPanel.setBackground(Colors.textColor);
        newPasswordPanel.setBackground(Colors.textColor);
        buttonPanel.setBackground(Colors.textColor);

        JLabel title = new JLabel ("Change Password");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setPreferredSize(new Dimension(200, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBackground(Colors.textColor);
        title.setForeground(Colors.pastelGreen);

        JLabel currentPassword = new JLabel ("Current Password: ");
        JLabel newPassword = new JLabel ("New Password:      ");

        currentPassword.setFont(new Font("SansSerif", Font.BOLD, 14));
        newPassword.setFont(new Font("SansSerif", Font.BOLD, 14));

        currentPassword.setForeground(Colors.pastelGreen);
        newPassword.setForeground(Colors.pastelGreen);

        currentPasswordField.setPreferredSize(new Dimension (150,30));
        newPasswordField.setPreferredSize(new Dimension (150,30));

        currentPasswordField.setBorder(BorderFactory.createLineBorder(Colors.mintGreen,2));
        newPasswordField.setBorder(BorderFactory.createLineBorder(Colors.mintGreen,2));

        JButton cancelButton = new JButton("Cancel");
        JButton changeButton = new JButton("Change");

        cancelButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        changeButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        cancelButton.setBackground(Colors.pastelGreen);
        changeButton.setBackground(Colors.pastelGreen);

        cancelButton.setForeground(Colors.textColor);
        changeButton.setForeground(Colors.textColor);

        cancelButton.setFocusable(false);
        changeButton.setFocusable(false);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideFrame();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPass = new String (currentPasswordField.getPassword());
                String newPass = new String (newPasswordField.getPassword());

                try {
                    String username = resultSet.getString("username");
                    Statement stmt = connection.createStatement();
                    String sql = "Select password From customer Where username = '" + username + "'";
                    ResultSet newResult = stmt.executeQuery(sql);
                    if(newResult.next()){
                        if(newResult.getString("password").equals(currentPass)){
                            if (newPass.isEmpty())
                                JOptionPane.showMessageDialog(null, "Please enter your new password");
                            else {
                                sql = "UPDATE customer SET password = '" + newPass +"' WHERE username = '"+username+"'";
                                stmt.executeUpdate(sql);

                                sql = "SELECT * FROM customer WHERE username = '" + username + "'";
                                resultSet = stmt.executeQuery(sql);
                                resultSet.next();
                                passwordTextField.setText(resultSet.getString("password"));

                                JOptionPane.showMessageDialog(null, "Your password is changed");
                                hideFrame();
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Your current password is incorrect");
                    }
                    else JOptionPane.showMessageDialog(null, "Username/ Password is incorrect");
                }catch (SQLException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }

            }
        });

        titlePanel.add(title);

        currentPasswordPanel.add(currentPassword);
        currentPasswordPanel.add(currentPasswordField);

        newPasswordPanel.add(newPassword);
        newPasswordPanel.add(newPasswordField);

        buttonPanel.add(cancelButton);
        buttonPanel.add(changeButton);

        changePasswordPanel.add(titlePanel);
        changePasswordPanel.add(currentPasswordPanel);
        changePasswordPanel.add(newPasswordPanel);
        changePasswordPanel.add(buttonPanel);

        return changePasswordPanel;

    }
    private void hideFrame(){
        this.setVisible(false);
    }
}

