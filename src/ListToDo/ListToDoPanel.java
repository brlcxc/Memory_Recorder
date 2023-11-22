package ListToDo;

import Defaults.Colors;
import Defaults.ToDoButton;
import Defaults.ToDoLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> listModel2;
    private JList<String> list1;
    private JList<String> list2;
    private JTextField inputField;
    public ListToDoPanel(){
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        list1 = new JList<>(listModel);
        list1.setFixedCellHeight(30);
        list1.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(list1);

        inputField = new JTextField();
        ToDoButton addButton = new ToDoButton("Add", Colors.pastelGreen, Colors.mintGreen);
        ToDoButton deleteButton = new ToDoButton("Delete", Colors.barbiePink, Colors.lessBarbiePink);

        addButton.addActionListener(new AddButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());

        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        // Top panel with input field and add button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        setSidePanel();
    }
    private void setSidePanel(){
        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        ToDoButton addButton = new ToDoButton("Create New", Colors.pastelGreen, Colors.mintGreen);
        ToDoLabel test = new ToDoLabel("Date");
//        sidePanel.setBackground(Color.GREEN);
        sidePanel.add(new JLabel("help"));
        listModel2 = new DefaultListModel<>();
        list2 = new JList<>(listModel);
        list2.setFixedCellHeight(30);
        list2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(list2);
        sidePanel.add(test, BorderLayout.NORTH);
        sidePanel.add(listScroller, BorderLayout.CENTER);
        sidePanel.add(addButton, BorderLayout.SOUTH);


    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = list1.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        }
    }
    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!inputField.getText().trim().isEmpty()) {
                listModel.addElement("• " + inputField.getText().trim());
                inputField.setText("");
            }
        }
    }
}
