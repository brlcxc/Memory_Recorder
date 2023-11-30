package ListToDo;


import Defaults.*;
import Diary.DiaryPanel;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;


public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;
    private DefaultListModel<String> toDoListModel;
    private JList<String> toDoList;
    private JTextField inputField;
    private JTextField searchField;
    private JTextField titleField;
    private final Map<String, String> toDoMap;
//    private final Map<String, JList> toDoMap;

    private ToDoButton addButton;
    private JButton saveIconButton;
    private JButton editIconButton;
    private JButton cancelIconButton;
    private String currentEntry;
    private int currentIndex;




    public ListToDoPanel(){
/*
       setLayout(new BorderLayout());
       setBackground(Colors.cream);*/
        toDoMap = new Hashtable<>();


        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setFixedCellHeight(30);
        itemList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        JScrollPane listScroller = new JScrollPane(itemList);


        inputField = new JTextField();
        inputField.addFocusListener(new ItemFocusListener());

        addButton = new ToDoButton("Add", Colors.pastelGreen, Colors.mintGreen);
        ToDoButton completeButton = new ToDoButton("Complete Item", Colors.pastelGreen, Colors.mintGreen);
        completeButton.addActionListener(new CompleteItemButtonListener());
        ToDoButton deleteButton = new ToDoButton("Remove Item", Colors.barbiePink, Colors.lessBarbiePink);


        addButton.addActionListener(new AddItemButtonListener());
        deleteButton.addActionListener(new DeleteItemButtonListener());


        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        inputField.docl
        inputField.setText("Add New List Item");
//        inputField.setText("");
//        inputField.setFocusable(false);


        // Top panel with input field and add button
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Colors.cream);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);


        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Colors.cream);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(completeButton);
        bottomPanel.add(deleteButton);




        //title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Colors.cream);


        //title field
        titleField = new DiaryTextField("Press \"New\" to create new list");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setPreferredSize(new Dimension(358, 30));

//        inputField.requestFocusInWindow();
//        titleField.requestFocusInWindow();

        saveIconButton = iconButton("save.png",13,13);
        saveIconButton.addActionListener(new SaveTitleButtonListener());


        editIconButton = iconButton("editing.png",15,15);
        editIconButton.addActionListener(new EditTitleButtonListener());


        cancelIconButton = iconButton("cancel.png",15,15);
        cancelIconButton.addActionListener(new CancelTitleButtonListener());


        titlePanel.add(titleField);
        titlePanel.add(saveIconButton);
        titlePanel.add(editIconButton);
        titlePanel.add(cancelIconButton);


        GridBagConstraints gbc = new GridBagConstraints();


        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();


        gbc.insets = new Insets(0,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titlePanel, gbc);


        gbc.insets = new Insets(0,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, gbc);


        gbc.insets = new Insets(8,8,8,8);
        gbc.gridy = -2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(listScroller, gbc);


        gbc.insets = new Insets(0,8,2,8);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -3;
        gbc.fill = GridBagConstraints.NONE;
        add(bottomPanel, gbc);
        HideButtons();
    }
    private void setSidePanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(Colors.cream);


        //entries label
        JLabel entriesLabel = new DiaryLabel("Available Lists", 18);


        //search field
        searchField = new DiarySearchField();
        searchField.getDocument().addDocumentListener(new SearchDocumentListener());
        searchField.addFocusListener(new SearchFocusListener());


        //search field label
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));


        //list and list scroller
        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        toDoList.setFixedCellHeight(30);
        toDoList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        toDoList.addMouseListener(new toDoMouseListener());
        JScrollPane listScroller = new JScrollPane(toDoList);


        //create new entry button
//        JPanel test = new JPanel();
        JPanel test = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));


        test.setBackground(Colors.cream);


        JButton createNewButton = new DiaryButton("New", Colors.pastelGreen, Colors.mintGreen);
        createNewButton.addActionListener(new CreateNewButtonListener());
        ToDoButton deleteButton = new ToDoButton("Remove", Colors.barbiePink, Colors.lessBarbiePink);
        deleteButton.addActionListener(new DeleteButtonListener());
        test.add(createNewButton);
        test.add(Box.createHorizontalStrut(5));
        test.add(deleteButton);


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
        sidePanel.add(test, gbc);
    }
    public JPanel getSidePanel(){
        return sidePanel;
    }
    private class DeleteItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                itemListModel.remove(selectedIndex);
            }
        }
    }
    private class AddItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!inputField.getText().trim().isEmpty() && !inputField.getText().trim().equals("Add New List Item")) {
                itemListModel.addElement("○ " + inputField.getText().trim());
                inputField.setText("Add New List Item");
            }
        }
    }
    private class CompleteItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                String itemText = itemListModel.getElementAt(selectedIndex);
                if(!itemText.contains("✅")) {
                    itemListModel.setElementAt(itemText + " ✅", selectedIndex);
                }
                else{
                    itemListModel.setElementAt(itemText.substring(0, itemText.length() - 2), selectedIndex);
                }
            }
        }
    }
    private JButton iconButton(String icon, int width, int height){
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(icon)));
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        JButton iconButton = new JButton(new ImageIcon(newImg));
        iconButton.setPreferredSize(new Dimension(30, 30));
        iconButton.setBorder(BorderFactory.createEmptyBorder());
        iconButton.setBackground(Colors.cream);
        iconButton.setFocusable(false);
        return iconButton;
    }
    private class EditTitleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            titleField.setEditable(true);
            titleField.setBorder(BorderFactory.createLineBorder(Colors.pastelPurple, 1));
        }
    }
    private class CancelTitleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            titleField.setEditable(false);
            titleField.setBorder(null);
            titleField.setText(currentEntry);
        }
    }
    private class SaveTitleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            titleField.setEditable(false);
            titleField.setBorder(null);


            if (!currentEntry.equals(titleField.getText().trim())) {
                //modified entry name changed at index
//                entryListModel.setElementAt(titleField.getText() + " (2/24/23)", currentIndex);
                toDoListModel.setElementAt(titleField.getText(), currentIndex);
                //text extracted from old entry
                String entryText = toDoMap.get(currentEntry);
                //old entry removed from map
                toDoMap.remove(currentEntry);
                //global current entry changed
                currentEntry = titleField.getText();
                //New entry name added to map
                toDoMap.put(currentEntry, entryText);
            }
        }
    }
    private class toDoMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
                //global entry is changed to the selected entry
                currentEntry = theList.getModel().getElementAt(index).toString();
                currentIndex = index;
                //text updated to represent selected entry
                titleField.setText(currentEntry);
//                textArea.setText(toDoMap.get(currentEntry));


                //title editing disabled
                titleField.setEditable(false);
                titleField.setBorder(null);
                titleField.setPreferredSize(new Dimension(358, 30));


                //The text area is now active
//                textArea.setEditable(true);
            }
        }
    }
    private class entryMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
//            JList theList = (JList) mouseEvent.getSource();
//            int index = theList.locationToIndex(mouseEvent.getPoint());
//            if (index >= 0) {
//                //global entry is changed to the selected entry
//                currentEntry = theList.getModel().getElementAt(index).toString();
//                currentIndex = index;
//                //text updated to represent selected entry
//                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
//
//                //title editing disabled
//                titleField.setEditable(false);
//                titleField.setBorder(null);
//                titleField.setPreferredSize(new Dimension(340, 24));
//
//                //The text area is now active
//                textArea.setEditable(true);
        }
    }
    private class SearchDocumentListener implements DocumentListener {
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
            //text from the search field is used to filter items within the list
            String filter = searchField.getText();
            //this statement prevents the list from being filtered when focus is lost
            if(!searchField.getText().equals("Search")){
                filterModel((DefaultListModel<String>) toDoList.getModel(), filter);
            }
        }
    }
    private void filterModel(DefaultListModel<String> model, String filter) {
        //elements are being removed from or added to the list, but they map keys remains unaffected
        for (String dictionaryKey : toDoMap.keySet()) {
            //elements not containing the filter text are removed from the list
            if (!dictionaryKey.contains(filter)) {
                if (model.contains(dictionaryKey)) {
                    model.removeElement(dictionaryKey);
                }
            }
            //elements containing the filter text are added to the list
            else {
                if (!model.contains(dictionaryKey)) {
//                    model.addElement(dictionaryKey + " (2/24/23)");
                    model.addElement(dictionaryKey);
                }
            }
        }
    }
    private static class SearchFocusListener implements FocusListener {
        //focus gained refers to when the text area either has text or is selected
        public void focusGained(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            //The "Search" text is removed when the text area is clicked on
            if (src.getText().equals("Search")) {
                src.setText("");
            }
        }
        //focus lost refers to when an empty text area is not currently selected
        public void focusLost(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            if (src.getText().equals("")){
                src.setText("Search");
            }
        }
    }
    private static class ItemFocusListener implements FocusListener {
        //focus gained refers to when the text area either has text or is selected
        public void focusGained(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            //The "Search" text is removed when the text area is clicked on
            if (src.getText().equals("Add New List Item")) {
                src.setText("");
            }
        }
        //focus lost refers to when an empty text area is not currently selected
        public void focusLost(java.awt.event.FocusEvent focusEvent) {
            JTextField src = (JTextField)focusEvent.getSource();
            if (src.getText().equals("")){
                src.setText("Add New List Item");
            }
        }
    }
    private class CreateNewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(titleField.getWidth());
            System.out.println(titleField.getHeight());
            String newEntry;
            if(!toDoListModel.contains("New List")){
                newEntry = "New List";
            }
            else{
                int i = 0;
                while(true) {
                    if(!toDoListModel.contains("New List (" + i + ")")) {
                        newEntry = "New List (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            //element added to list
//            entryListModel.addElement(newEntry + " (2/24/23)");
            toDoListModel.addElement(newEntry);
            //element added to map
            toDoMap.put(newEntry, "");
            //global current entry changed
            currentEntry = newEntry;
            currentIndex = toDoListModel.size() - 1;


            //text field and text area properly updated
            titleField.setText(currentEntry);
//            textArea.setText(diaryEntryMap.get(currentEntry));
            titleField.setPreferredSize(new Dimension(358, 30));




            //modification buttons visible
            ShowButtons();


//            dateTextArea.setVisible(true);
//            dateTextArea.setText("Last Edit: 2/24/23  11:32");


            int[] g = {currentIndex};
            toDoList.setSelectedIndices(g);


            //text area active
            addButton.setEnabled(true);
            titleField.setEditable(false);
            titleField.setBorder(null);
        }
    }
    public void ShowButtons(){
        saveIconButton.setVisible(true);
        editIconButton.setVisible(true);
        cancelIconButton.setVisible(true);
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = toDoList.getSelectedIndex();
            if (selectedIndex != -1) {
                //The entry is both removed from the map and the list
                toDoMap.remove(toDoList.getModel().getElementAt(selectedIndex));
                toDoListModel.remove(selectedIndex);
            }
            //statement called if selected index is not the first
            if(selectedIndex > 0) {
                //the list item one index prior is selected
                currentIndex = selectedIndex - 1;
                int[] g = {currentIndex};
                toDoList.setSelectedIndices(g);
                //global entry updated
                currentEntry = toDoList.getModel().getElementAt(selectedIndex - 1);
                //text field and text area properly updated
                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
            }
            //statement called if the index is 0 and there is at least one element left
            else if (toDoListModel.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                toDoList.setSelectedIndices(g);
                //global entry updated
                currentEntry = toDoList.getModel().getElementAt(0);
                //text field and text area properly updated
                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
            }
            //statement called if there are no elements remaining after deletion
            else{
                //text set to default state
                currentIndex = -1;
                titleField.setText("Press \"New\" to create new list");
                titleField.setPreferredSize(new Dimension(358, 30));
//                textArea.setText("");
//                textArea.setEditable(false);
//                dateTextArea.setVisible(false);
                HideButtons();
            }
            titleField.setEditable(false);
            titleField.setBorder(null);
        }
    }
    public void HideButtons(){
        saveIconButton.setVisible(false);
        editIconButton.setVisible(false);
        cancelIconButton.setVisible(false);
    }
}
