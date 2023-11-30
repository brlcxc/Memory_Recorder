package Main;

import Defaults.Colors;
import Logic.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private static final int FRAME_WIDTH = 280;
    private static final int FRAME_HEIGHT = 280;
    private DatabaseConnection connection2;

    private final String URL ="jdbc:postgresql://memorydatabase.ct5cqswjns9i.us-east-1.rds.amazonaws.com:5432/memoryRecorder";
    private Connection connection;
    private JButton login = new JButton("Login");
    private JButton register = new JButton ("Register");
    private JButton forgetPassword = new  JButton ("Forget password?");
    private JTextField userName = new JTextField();
    private JPasswordField passWord = new JPasswordField();

    public Login(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Login");
        this.setBackground(Colors.textColor);
        this.add(loginPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void connectDatabase(){
        try {
            connection = DriverManager.getConnection(URL,"postgres","memoryRecorder");
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private JPanel loginPanel() {
//        connection2.connectDatabase();
        connectDatabase();

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Colors.textColor);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel subLoginPanel = new JPanel();
        JPanel subLoginPanel1 = new JPanel();
        JPanel subLoginPanel2 = new JPanel();
        JPanel subLoginPanel3 = new JPanel();
        JPanel subLoginPanel4 = new JPanel();

        subLoginPanel.setBackground(Colors.textColor);
        subLoginPanel1.setBackground(Colors.textColor);
        subLoginPanel2.setBackground(Colors.textColor);
        subLoginPanel3.setBackground(Colors.textColor);
        subLoginPanel4.setBackground(Colors.textColor);

        JLabel title = new JLabel ("MEMORY RECORDER");
        title.setPreferredSize(new Dimension(200, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBackground(Colors.textColor);
        title.setForeground(Colors.pastelGreen);

        JLabel name = new JLabel ("user name: ");
        JLabel pass = new JLabel ("password:  ");

        name.setForeground(Colors.pastelGreen);
        pass.setForeground(Colors.pastelGreen);

        userName.setPreferredSize(new Dimension (150,30));
        passWord.setPreferredSize(new Dimension (150,30));

        userName.setBorder(BorderFactory.createLineBorder(Colors.mintGreen,2));
        passWord.setBorder(BorderFactory.createLineBorder(Colors.mintGreen,2));

        login.setBackground(Colors.pastelGreen);
        register.setBackground(Colors.textColor);
        forgetPassword.setBackground(Colors.textColor);

        login.setForeground(Colors.textColor);
        register.setForeground(Colors.pastelGreen);
        forgetPassword.setForeground(Colors.pastelGreen);

        login.setFocusable(false);
        register.setFocusable(false);
        forgetPassword.setFocusable(false);

        login.addActionListener(new loginListener());
        register.addActionListener(new registerListener());
        forgetPassword.addActionListener((new forgetPasswordListener()));

        register.setBorder(BorderFactory.createEmptyBorder());
        forgetPassword.setBorder(BorderFactory.createEmptyBorder());

        subLoginPanel.add(title);

        subLoginPanel1.add(name);
        subLoginPanel1.add(userName);

        subLoginPanel2.add(pass);
        subLoginPanel2.add(passWord);

        subLoginPanel3.add(login);

        subLoginPanel4.add(register);
        subLoginPanel4.add(Box.createHorizontalStrut(20));
        subLoginPanel4.add(forgetPassword);

        loginPanel.add(subLoginPanel);
        loginPanel.add(subLoginPanel1);
        loginPanel.add(subLoginPanel2);
        loginPanel.add(subLoginPanel3);
        loginPanel.add(subLoginPanel4);

        return loginPanel;
    }

    public class loginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = userName.getText();
            String password = new String(passWord.getPassword());
//            Boolean connectionEstablish = connection2.login(username, password);

//            if(connectionEstablish){
//                hideFrame();
//                new GUI(connection, result);
//            }
            try {
                Statement stm = connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sql = "Select * From customer Where username = '" + username + "' and password = '" + password + "'";
                ResultSet result = stm.executeQuery(sql);

                if(result.next()){
                    hideFrame();
                    new GUI(connection, result);
                }
                else JOptionPane.showMessageDialog(null, "Username/ Password is incorrect");
            }catch (SQLException e1){
                JOptionPane.showMessageDialog(null, "System Error");
            }
        }
    }

    public class registerListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            hideFrame();
            new Register(connection);
        }
    }
    public class forgetPasswordListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

        }
    }
    private void hideFrame(){
        this.setVisible(false);
    }

    public static void main(String[] args) {
        new Login();
    }

}
