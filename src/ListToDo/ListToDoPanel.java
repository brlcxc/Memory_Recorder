package ListToDo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private JTextField inputField;
    public ListToDoPanel(){
        setBackground(Color.YELLOW);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setFixedCellHeight(30);
        list.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(list);

        inputField = new JTextField();
        JButton addButton = createButton("Add", new Color(50, 150, 50));
        JButton deleteButton = createButton("Delete", new Color(200, 75, 75));

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
        sidePanel.setBackground(Color.GREEN);
        sidePanel.add(new JLabel("help"));
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = list.getSelectedIndex();
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
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return button;
    }
}
