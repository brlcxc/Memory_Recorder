package DashBoard;

import Defaults.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangeDob extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;
    private ResultSet resultSet;
    private Connection connection;
    private JTextField dobTextField;
    private JComboBox monthBox;
    private JComboBox dateBox;
    private JComboBox yearBox;


    public ChangeDob (ResultSet result, Connection con, JTextField textField){
        resultSet = result;
        connection = con;
        dobTextField = textField;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Change date of birth");
        this.setBackground(Colors.textColor);
        this.add(changeDobPanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private JPanel changeDobPanel(){
        JPanel changeDobPanel = new JPanel();
        changeDobPanel.setBackground(Colors.textColor);
        changeDobPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel();
        JPanel dobPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setBackground(Colors.textColor);
        dobPanel.setBackground(Colors.textColor);
        buttonPanel.setBackground(Colors.textColor);

        JLabel title = new JLabel ("Your date of birth: ");
        title.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        title.setPreferredSize(new Dimension(200, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBackground(Colors.textColor);
        title.setForeground(Colors.pastelGreen);

        JButton cancelButton = new JButton("Cancel");
        JButton changeButton = new JButton("Change");

        cancelButton.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        changeButton.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

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
                Integer birthYear = (Integer) yearBox.getSelectedItem();
                Integer birthDate = (Integer) dateBox.getSelectedItem();
                int birthMonth = monthBox.getSelectedIndex();
                Date dob = new Date(birthYear-1900,birthMonth-1, birthDate);
                if (birthYear==0 || birthDate== 0|| birthMonth==0)
                    JOptionPane.showMessageDialog(null, "Please choose a valid date of birth");
                else {
                    try {
                        String username = resultSet.getString("username");
                        Statement stmt = connection.createStatement();
                        String sql ="UPDATE customer SET date_of_birth = '" + dob+"' where username = '" + username+"'";
                        stmt.executeUpdate(sql);
                        sql = "SELECT * FROM customer WHERE username = '" + username + "'";
                        resultSet = stmt.executeQuery(sql);
                        resultSet.next();
                        dobTextField.setText(resultSet.getString("date_of_birth"));
                        JOptionPane.showMessageDialog(null, "The account successfully created");
                        hideFrame();
                    } catch (SQLException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            }
        });

        titlePanel.add(title);
        dobPanel.add(getDobPanel());

        buttonPanel.add(cancelButton);
        buttonPanel.add(changeButton);

        changeDobPanel.add(titlePanel);
        changeDobPanel.add(dobPanel);
        changeDobPanel.add(buttonPanel);
        return changeDobPanel;
    }
    private JPanel getDobPanel(){
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.setBackground(Colors.textColor);

        dobPanel.setAlignmentX(0);

        JLabel monthLabel = new JLabel("Month");
        monthLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        monthLabel.setForeground(Colors.pastelGreen);
        String[] monthList = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        monthBox = new JComboBox(monthList);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        dateLabel.setForeground(Colors.pastelGreen);
        Integer[] dateList = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        dateBox = new JComboBox(dateList);

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        yearLabel.setForeground(Colors.pastelGreen);
        Integer[] yearList = new Integer[94];
        yearList[0]=1900;
        for (int i =1; i<93; i++ ){
            yearList[i]=1930+i;
        }
        yearBox = new JComboBox(yearList);

        monthBox.setBackground(Colors.cream);
        dateBox.setBackground(Colors.cream);
        yearBox.setBackground(Colors.cream);

        dobPanel.add(monthLabel);
        dobPanel.add(monthBox);
        dobPanel.add(dateLabel);
        dobPanel.add(dateBox);
        dobPanel.add(yearLabel);
        dobPanel.add(yearBox);
        return dobPanel;
    }

    private void hideFrame(){
        this.setVisible(false);
    }
}
