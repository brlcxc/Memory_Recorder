package ListToDo;

import Defaults.*;
import Diary.DiaryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> listModel2;
    private JList<String> list1;
    private JList<String> list2;
    private JTextField inputField;
    private JTextField searchField;
    private DefaultListModel<String> entryListModel;
    private JList<String> entryList;
    public ListToDoPanel(){
/*
        setLayout(new BorderLayout());
        setBackground(Colors.cream);*/

        listModel = new DefaultListModel<>();
        list1 = new JList<>(listModel);
        list1.setFixedCellHeight(30);
        list1.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(list1);

        inputField = new JTextField();
        ToDoButton addButton = new ToDoButton("Add", Colors.pastelGreen, Colors.mintGreen);
        ToDoButton deleteButton = new ToDoButton("Remove Item", Colors.barbiePink, Colors.lessBarbiePink);

        addButton.addActionListener(new AddButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());

        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        inputField.setText("Add new list item");

        // Top panel with input field and add button
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Colors.cream);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Colors.cream);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(deleteButton);

/*        add(inputPanel, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        setSidePanel();*/
        GridBagConstraints gbc = new GridBagConstraints();
//        diaryEntryMap = new Hashtable<>();

        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

/*
        //title field
        titleField = new DiaryTextField("Press \"Create New Entry\" to begin writing");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);

        //diary text area
        textArea = new JTextArea(16, 40);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane textAreaScroll = new JScrollPane(textArea);

        //save button
        JButton saveButton = new DiaryButton("Save", Colors.pastelGreen, Colors.mintGreen);
        saveButton.addActionListener(new DiaryPanel.SaveButtonListener());

        //delete button
        JButton deleteButton = new DiaryButton("Delete", Colors.barbiePink, Colors.lessBarbiePink);
        deleteButton.addActionListener(new DiaryPanel.DeleteButtonListener());

        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.cream);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(deleteButton);
*/

        gbc.insets = new Insets(8,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, gbc);

        gbc.insets = new Insets(8,8,8,8);
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(listScroller, gbc);

        gbc.insets = new Insets(0,8,2,8);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -1;
        gbc.fill = GridBagConstraints.NONE;
        add(bottomPanel, gbc);
    }
    private void setSidePanel(){
/*        sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());
        ToDoButton addButton = new ToDoButton("Create New", Colors.pastelGreen, Colors.mintGreen);
        ToDoLabel test = new ToDoLabel("Date");
        sidePanel.setBackground(Colors.cream);
        sidePanel.add(new JLabel("help"));
        listModel2 = new DefaultListModel<>();
        list2 = new JList<>(listModel);
        list2.setFixedCellHeight(30);
        list2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(list2);
        sidePanel.add(test, BorderLayout.NORTH);
        sidePanel.add(listScroller, BorderLayout.CENTER);
        sidePanel.add(addButton, BorderLayout.SOUTH);*/

        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(Colors.cream);

        //entries label
        JLabel entriesLabel = new DiaryLabel("Available Lists", 18);

        //search field
        searchField = new DiarySearchField();
/*        searchField.getDocument().addDocumentListener(new DiaryPanel.SearchDocumentListener());
        searchField.addFocusListener(new DiaryPanel.SearchFocusListener());*/

        //search field label
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        //list and list scroller
        entryListModel = new DefaultListModel<>();
        entryList = new JList<>(entryListModel);
        entryList.setFixedCellHeight(30);
        entryList.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        entryList.addMouseListener(new DiaryPanel.mouseListener());
        JScrollPane listScroller = new JScrollPane(entryList);

        //create new entry button
        JButton createNewButton = new DiaryButton("Create New List", Colors.pastelGreen, Colors.mintGreen);
//        createNewButton.addActionListener(new DiaryPanel.CreateNewButtonListener());

        gbc.insets = new Insets(8,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        sidePanel.add(entriesLabel, gbc);

        gbc.insets = new Insets(8,8,0,0);
        gbc.weightx = 1;
        gbc.gridy = -1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        sidePanel.add(searchField, gbc);

        gbc.insets = new Insets(0,8,8,0);
        gbc.gridy = -2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        sidePanel.add(listScroller, gbc);

        gbc.insets = new Insets(4,8,8,8);
        gbc.gridy = -3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        sidePanel.add(createNewButton, gbc);
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
