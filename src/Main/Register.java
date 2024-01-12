package Main;

import Defaults.Colors;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JFrame {
    private static final int FRAME_WIDTH = 480;
    private static final int FRAME_HEIGHT = 500;
    private Connection connection;

    private JTextField userName = new JTextField();
    private JPasswordField passWord = new JPasswordField();
    private JTextField firstName = new JTextField();
    private JTextField lastName = new JTextField();
    private JTextField middleName = new JTextField();
    private JComboBox monthBox = new JComboBox();
    private JComboBox dateBox  = new JComboBox();
    private JComboBox yearBox = new JComboBox();
    private JTextField email = new JTextField();
    private JTextField address = new JTextField();
    private JTextField phoneNumber = new JTextField();
    public Register(Connection con){
        connection = con;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Register an account");
        this.setBackground(Colors.pastelGreen);
        this.add(registerPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("src/Defaults/IconImages/icon.png");
        this.setIconImage(img.getImage());
    }

    private JPanel registerPanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(Colors.mintGreen);
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JLabel titleLabel = new JLabel("Your Information:");
        titleLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;

        registerPanel.add(titleLabel, gbc);

        gbc.gridy = -1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;

        registerPanel.add(new RegisterLabel("Username:"), gbc);
        gbc.gridy = -2;
        registerPanel.add(new RegisterLabel("Password:"), gbc);
        gbc.gridy = -3;
        registerPanel.add(new RegisterLabel("First Name:"), gbc);
        gbc.gridy = -4;
        registerPanel.add(new RegisterLabel("Middle Name:"), gbc);
        gbc.gridy = -5;
        registerPanel.add(new RegisterLabel("Last Name:"), gbc);
        gbc.gridy = -6;
        registerPanel.add(new RegisterLabel("Date of birth:"), gbc);
        gbc.gridy = -7;
        registerPanel.add(new RegisterLabel("Email address:"), gbc);
        gbc.gridy = -8;
        registerPanel.add(new RegisterLabel("Address:"), gbc);
        gbc.gridy = -9;
        registerPanel.add(new RegisterLabel("Phone Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.BOTH;


        registerPanel.add(userName, gbc);
        gbc.gridy = -2;
        registerPanel.add(passWord, gbc);
        gbc.gridy = -3;
        registerPanel.add(firstName, gbc);
        gbc.gridy = -4;
        registerPanel.add(middleName, gbc);
        gbc.gridy = -5;
        registerPanel.add(lastName, gbc);
        gbc.gridy = -6;
        registerPanel.add(datePanel(), gbc);
        gbc.gridy = -7;
        registerPanel.add(email, gbc);
        gbc.gridy = -8;
        registerPanel.add(address, gbc);
        gbc.gridy = -9;
        registerPanel.add(phoneNumber, gbc);


        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = -5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        registerPanel.add(submitPanel(), gbc);

        return registerPanel;
    }
    private JPanel addSubPanel(String labelName,JTextField textFieldName){
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        subPanel.setBackground(Colors.mintGreen);
        subPanel.setAlignmentX(0);
        JLabel label = new JLabel(labelName);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        textFieldName.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textFieldName.setPreferredSize(new Dimension(260,30));
        subPanel.add(label);
        subPanel.add(textFieldName);
        return subPanel;
    }
    private JPanel datePanel(){
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        subPanel.setBackground(Colors.mintGreen);
        subPanel.setAlignmentX(0);
        JLabel monthLabel = new JLabel("Month");
        String[] monthList = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        monthBox = new JComboBox(monthList);

        JLabel dateLabel = new JLabel("Date");
        Integer[] dateList = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        dateBox = new JComboBox(dateList);

        JLabel yearLabel = new JLabel("Year");
        Integer[] yearList = new Integer[94];
        yearList[0]=1900;
        for (int i =1; i<93; i++ ){
            yearList[i]=1930+i;
        }
        yearBox = new JComboBox(yearList);

        monthBox.setBackground(Colors.mintGreen);
        dateBox.setBackground(Colors.mintGreen);
        yearBox.setBackground(Colors.mintGreen);

        subPanel.add(monthLabel);
        subPanel.add(monthBox);
        subPanel.add(dateLabel);
        subPanel.add(dateBox);
        subPanel.add(yearLabel);
        subPanel.add(yearBox);
        return subPanel;
    }
/*    private JPanel datePanel(String labelName){
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        subPanel.setBackground(Colors.mintGreen);
        subPanel.setAlignmentX(0);
        JLabel label = new JLabel(labelName);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel monthLabel = new JLabel("Month");
        String[] monthList = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        monthBox = new JComboBox(monthList);

        JLabel dateLabel = new JLabel("Date");
        Integer[] dateList = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        dateBox = new JComboBox(dateList);

        JLabel yearLabel = new JLabel("Year");
        Integer[] yearList = new Integer[94];
        yearList[0]=1900;
        for (int i =1; i<93; i++ ){
            yearList[i]=1930+i;
        }
        yearBox = new JComboBox(yearList);

        monthBox.setBackground(Colors.mintGreen);
        dateBox.setBackground(Colors.mintGreen);
        yearBox.setBackground(Colors.mintGreen);

        subPanel.add(label);
        subPanel.add(monthLabel);
        subPanel.add(monthBox);
        subPanel.add(dateLabel);
        subPanel.add(dateBox);
        subPanel.add(yearLabel);
        subPanel.add(yearBox);
        return subPanel;
    }*/

    private JPanel submitPanel (){
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(Colors.mintGreen);

        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");

        submit.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancel.setFont(new Font("SansSerif", Font.BOLD, 16));

        submit.setBackground(Colors.textColor);
        cancel .setBackground(Colors.textColor);

        submit.setForeground(Colors.pastelGreen);
        cancel.setForeground(Colors.pastelGreen);

        submit.addActionListener(new submitListener());
        cancel .addActionListener(new resetListener());

        submitPanel.add(submit);
        submitPanel.add(Box.createHorizontalStrut(15));
        submitPanel.add(cancel);

        return submitPanel;
    }

    public class submitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userName.getText();
            char[] pass =  passWord.getPassword();
            String password = new String(pass);
            String firstname = firstName.getText();
            String middlename = middleName.getText();
            String lastname = lastName.getText();
            String emailAddress = email.getText();
            String physicalAddress = address.getText();
            String phone = phoneNumber.getText();
            Integer birthYear = (Integer) yearBox.getSelectedItem();
            Integer birthDate = (Integer) dateBox.getSelectedItem();
            int birthMonth = monthBox.getSelectedIndex();
            Date dob = new Date(birthYear-1900,birthMonth-1, birthDate);
            if(username.isEmpty() || password.isEmpty())
                JOptionPane.showMessageDialog(null, "Please enter username and password");
            else if (doesUsernameExist(username))
                JOptionPane.showMessageDialog(null, "This username already existed");
            else if (birthYear==0 || birthDate== 0|| birthMonth==0)
                JOptionPane.showMessageDialog(null, "Please choose a valid date of birth");
            else {
                try {
                    Statement stm = connection.createStatement();
                    String sql = "Insert into customer (username, password,email, date_of_birth, address, phone_number, first_name, middle_name, last_name) " +
                            "Values ('" + username + "','" + password + "','"+emailAddress+"','"+dob+"','"+physicalAddress+ "','"+phone+"','"+firstname+"','"+middlename+"','"+lastname+"')";
                    stm.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "The account successfully created");
                    hideFrame();
                    new Login();
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }
    }
    private void hideFrame(){
        this.setVisible(false);
    }
    private Boolean doesUsernameExist(String username){
            boolean flag = false;
            try {
            Statement stm = connection.createStatement();
            String sql = "Select * From customer Where username = '" + username + "'";
            ResultSet result = stm.executeQuery(sql);
            if (result.next())
                flag = true;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
            return flag;
    }
    public class resetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideFrame();
            new Login();
        }
    }

}
