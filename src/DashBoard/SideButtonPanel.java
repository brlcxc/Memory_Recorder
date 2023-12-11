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
import java.util.Map;

public class SideButtonPanel extends JPanel {

    private final JPanel buttonPanel = new JPanel();
    private final JPanel dashBoardPanel = new JPanel();
    private Connection connection;
    private ResultSet resultSet;
    private JFrame mainFrame;
    public SideButtonPanel(){
        setSideButtonPanel();
    }

    public SideButtonPanel(JPanel dashBoard, Connection con, ResultSet result, JFrame frame){
        connection = con;
        resultSet=result;
        mainFrame = frame;
        setSideButtonPanel();
        setDashBoardPanel();
        dashBoard.add(getDashBoardPanel());
    }
    public void setSideButtonPanel (){
        buttonPanel.setPreferredSize(new Dimension(150,200));
        buttonPanel.setBackground(Colors.pastelPurple);
        buttonPanel.add(subPanel());
    }
    public JPanel getSideButtonPanel(){
        return buttonPanel;
    }

    private void setDashBoardPanel(){
        InformationPanel1 informationPanel = new InformationPanel1(connection, resultSet, mainFrame);
        dashBoardPanel.setBackground(Colors.cream);
        dashBoardPanel.add(informationPanel.getInformationPanel());
    }
    private JPanel getDashBoardPanel(){
        return dashBoardPanel;
    }
    private JPanel subPanel(){
        JPanel subPanel = new JPanel();
        subPanel.setBackground(Colors.pastelPurple);
        subPanel.setPreferredSize(new Dimension(150,130));
        subPanel.setLayout(new GridLayout(3,1));
        subPanel.add(accountButton());
        subPanel.add(emergencyButton());
        subPanel.add(logoutButton());
        subPanel.setBorder(BorderFactory.createEmptyBorder());
        return subPanel;
    }

    private JButton accountButton(){
        JButton accountButton = new JButton("Account");
        accountButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        accountButton.setBackground(Colors.pastelPurple);
        accountButton.setForeground(Color.BLACK);
        accountButton.setBorder(BorderFactory.createEmptyBorder());
        accountButton.setPreferredSize(new Dimension(40,30));
        accountButton.setFocusable(false);
        mouseListener(accountButton);
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformationPanel1 informationPanel = new InformationPanel1(connection, resultSet, mainFrame);
                dashBoardPanel.setVisible(false);
                dashBoardPanel.removeAll();
                dashBoardPanel.add(informationPanel.getInformationPanel());
                dashBoardPanel.setVisible(true);
            }
        });
        return accountButton;
    }
    private JButton emergencyButton(){
        JButton emergencyContactButton = new JButton("Emergency Contact");
        emergencyContactButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        emergencyContactButton .setBackground(Colors.pastelPurple);
        emergencyContactButton .setForeground(Color.BLACK);
        emergencyContactButton .setBorder(BorderFactory.createEmptyBorder());
        emergencyContactButton .setPreferredSize(new Dimension(40,30));
        emergencyContactButton .setFocusable(false);
        mouseListener(emergencyContactButton);
        emergencyContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmergencyContact emergencyContact = new EmergencyContact(connection, resultSet);
                dashBoardPanel.setVisible(false);
                dashBoardPanel.removeAll();
                dashBoardPanel.add(emergencyContact.getEmergencyContactPanel());
                dashBoardPanel.setVisible(true);
            }
        });
        return emergencyContactButton;
    }
    private JButton logoutButton (){
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutButton.setBackground(Colors.pastelPurple);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBorder(BorderFactory.createEmptyBorder());
        logoutButton.setPreferredSize(new Dimension(40,30));
        logoutButton.setFocusable(false);
        mouseListener(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?","Log out?",JOptionPane.YES_NO_OPTION);
                if (option==JOptionPane.YES_OPTION) {
                    mainFrame.setVisible(false);
                    new Login();
                }

            }
        });
        return logoutButton;
    }
    private void mouseListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {
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
    }
}
