package Diary;
import Defaults.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class DiaryPanel extends JPanel {
    //this needs to default to the most recent entry
    //maybe delte button in top right
    //when new entry is created edit feild is automatically set to editable
    private JPanel sidePanel;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private JTextArea textArea;
    private JScrollPane textAreaScroll;
    private JTextField titleField;
    private JTextField searchField;

    private Map<String, String> dict;
    private String currentEntry;
    //maybe have code to crreat a new entry if one does not exit?
    public DiaryPanel(){
        dict = new Hashtable<>();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();
        JButton saveButton = new DiaryButton("Save", Colors.pastelGreen, Colors.mintGreen);
        JButton deleteButton = new DiaryButton("Delete", Colors.barbiePink, Colors.lessBarbiePink);
        JPanel buttonPanel = new JPanel();
        JPanel test2 = new JPanel();
        buttonPanel.setBackground(Colors.cream);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(deleteButton);
        textArea = new JTextArea(16, 40);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
//        textArea.addFocusListener(new focusListener2());
        textAreaScroll = new JScrollPane(textArea);
        test2.setBackground(Colors.cream);
        ///title near the top - created and last edited near the bottom
        JLabel label1 = new DiaryLabel("Created: ", 18);
        JLabel label2 = new DiaryLabel("Last Edited: ", 18);
        titleField = new DiaryTextField("Press \"Create New Entry\" to begin writing");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        //maybe I can change background and border if I click some sort of edit button?
        //also I might want buttons to enlarge font at some point
        //search bar and dates still seem fun

        deleteButton.addActionListener(new DeleteButtonListener());
        saveButton.addActionListener(new SaveButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(8,8,0,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titleField, gbc);

        gbc.insets = new Insets(8,8,8,8);
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(textAreaScroll, gbc);

        gbc.insets = new Insets(0,8,2,8);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -1;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);
    }
    private void setSidePanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(Colors.cream);
        listModel = new DefaultListModel<>();
        searchField = new DiarySearchField();
        searchField.getDocument().addDocumentListener(new documentListener());
//        TextPrompt tp3 = new TextPrompt("First Name", tf3, TextPrompt.Show.FOCUS_LOST);
        searchField.addFocusListener(new focusListener());
        list = new JList<>(listModel);
        list.setFixedCellHeight(30);
        list.setFont(new Font("SansSerif", Font.PLAIN, 18));
        list.addMouseListener(new mouseListener());
        JScrollPane listScroller = new JScrollPane(list);
        JButton createNewButton = new DiaryButton("Create New Entry", Colors.pastelGreen, Colors.mintGreen);
        JLabel label = new DiaryLabel("Diary Entries", 18);
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JPanel test = new JPanel();
        test.setBackground(Colors.cream);
/*        test.add(searchLabel);
        test.add(searchField);*/

        createNewButton.addActionListener(new CreateNewButtonListener());

        gbc.insets = new Insets(8,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        sidePanel.add(label, gbc);

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
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -3;
        gbc.fill = GridBagConstraints.NONE;
        sidePanel.add(createNewButton, gbc);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
    private class CreateNewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newEntry;
            if(!listModel.contains("<<New Entry>>")){
                newEntry = "<<New Entry>>";
            }
            else{
                int i = 0;
                while(true) {
                    if(!listModel.contains("<<New Entry>> (" + i + ")")) {
                        newEntry = "<<New Entry>> (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            listModel.addElement(newEntry);
            dict.put(newEntry, "");

            currentEntry = newEntry;
            titleField.setText(currentEntry);
            textArea.setText(dict.get(currentEntry));
            textArea.setEditable(true);
        }
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                dict.remove(list.getModel().getElementAt(selectedIndex));
                listModel.remove(selectedIndex);
//                dict.remove(list.getModel().getElementAt(selectedIndex));
                //not removing properly
            }
            if(selectedIndex > 0) {
                int[] g = {selectedIndex - 1};
                list.setSelectedIndices(g);
                currentEntry = list.getModel().getElementAt(selectedIndex - 1);
                titleField.setText(currentEntry);
                textArea.setText(dict.get(currentEntry));
            } else if (listModel.size() > 0) {
                int[] g = {0};
                list.setSelectedIndices(g);
                currentEntry = list.getModel().getElementAt(0);
                titleField.setText(currentEntry);
                textArea.setText(dict.get(currentEntry));
            }
            else{
                titleField.setText("Press \"Create New Entry\" to begin writing");
                textArea.setText("");
                textArea.setEditable(false);
            }
        }
    }
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(listModel.size() > 0) {
                if (!currentEntry.equals(titleField.getText().trim())) {
                    listModel.addElement(titleField.getText());
                    dict.remove(currentEntry);
                    listModel.remove(listModel.indexOf(currentEntry));
                    currentEntry = titleField.getText();
                }
                dict.put(currentEntry, textArea.getText());
            }
        }
    }
    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
                currentEntry = theList.getModel().getElementAt(index).toString();
                titleField.setText(currentEntry);
                textArea.setText(dict.get(currentEntry));
                textArea.setEditable(true);
            }
        }
    }
    private class documentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filter();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            filter();
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        private void filter() {
            String filter = searchField.getText();
            if(!searchField.getText().equals("Search")){
                filterModel((DefaultListModel<String>) list.getModel(), filter);
            }
        }
    }
    private void filterModel(DefaultListModel<String> model, String filter) {
        for (String s : dict.keySet()) {
            if (!s.startsWith(filter)) {
                if (model.contains(s)) {
                    model.removeElement(s);
                }
            } else {
                if (!model.contains(s)) {
                    model.addElement(s);
                }
            }
        }
    }
    private class focusListener implements FocusListener{
        public void focusGained(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            if (src.getText().equals("Search")) {
                src.setText("");
            }
        }
        public void focusLost(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            if (src.getText().equals("")){
                src.setText("Search");
            }
        }
    }
    private class focusListener2 implements FocusListener{
        public void focusGained(java.awt.event.FocusEvent focusEvent) {
            JTextArea src = (JTextArea)focusEvent.getSource();
            if (src.getText().equals("Search")) {
                src.setText("");
            }
        }
        public void focusLost(java.awt.event.FocusEvent focusEvent) {
            JTextArea src = (JTextArea)focusEvent.getSource();
            if (src.getText().equals("")){
                src.setText("Search");
            }
        }
    }
    // Instantiate a FocusListener ONCE
/*    java.awt.event.FocusListener myFocusListener = new java.awt.event.FocusListener() {
        public void focusGained(java.awt.event.FocusEvent focusEvent) {
            try {
                JTextField src = (JTextField)focusEvent.getSource();
                if (src.getText().equals("Text here!") {
                    src.setText("");
                }
            } catch (ClassCastException ignored) {
                *//* I only listen to JTextFields *//*
            }
        }

        public void focusLost(java.awt.event.FocusEvent focusEvent) {
            try {
                JTextField src = (JTextField)focusEvent.getSource();
                if (src.getText().equals("") {
                    src.setText("Text here!");
                }
            } catch (ClassCastException ignored) {
                *//* I only listen to JTextFields *//*
            }
        }
    };*/
  /*  private JTextField createTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = field.getText();
                filterModel((DefaultListModel<String>)jList.getModel(), filter);
            }
        });
        return field;
    }

    private JList createJList() {
        JList list = new JList(createDefaultListModel());
        list.setVisibleRowCount(6);
        return list;
    }

    private ListModel<String> createDefaultListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : defaultValues) {
            model.addElement(s);
        }
        return model;
    }

    public void filterModel(DefaultListModel<String> model, String filter) {
        for (String s : defaultValues) {
            if (!s.startsWith(filter)) {
                if (model.contains(s)) {
                    model.removeElement(s);
                }
            } else {
                if (!model.contains(s)) {
                    model.addElement(s);
                }
            }
        }
    }*/
}