package Notes;
import Defaults.*;
import ListToDo.ListToDoPanel;

import java.sql.Timestamp;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class NotesPanel extends JPanel {
    private JPanel sidePanel;
    private final JTextArea textArea;
    private final JTextArea dateTextArea;
    private  ResultSet resultSet;
    private final JTextField titleField;
    private JTextField searchField;
    private final Map<Timestamp, DiaryObject> diaryEntryMap;
    private DefaultListModel<DiaryObject> entryListModel;

    private JList<DiaryObject> entryList;

    private String currentEntry;
    private JPanel titlePanel;
    private JButton saveIconButton;
    private JButton editIconButton;
    private JButton cancelIconButton;
    private int currentIndex;
    private Connection connection;
    private String username;
    public NotesPanel(Connection con, ResultSet resultSet){
        this.connection = con;
        this.resultSet = resultSet;
        SetUsername();
        GridBagConstraints gbc = new GridBagConstraints();
        diaryEntryMap = new Hashtable<>();

        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

        //title panel
        titlePanel = new JPanel();
        titlePanel.setBackground(Colors.cream);

        //title field
        titleField = new TitleTextField("Press \"New Notebook\" to begin writing");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setPreferredSize(new Dimension(358, 24));

        saveIconButton = iconButton("src/Defaults/IconImages/save.png");
        saveIconButton.addActionListener(new SaveTitleButtonListener());

        editIconButton = iconButton("src/Defaults/IconImages/editing.png");
        editIconButton.addActionListener(new EditTitleButtonListener());

        cancelIconButton = iconButton("src/Defaults/IconImages/cancel.png");
        cancelIconButton.addActionListener(new CancelTitleButtonListener());

        titlePanel.add(titleField);
        titlePanel.add(saveIconButton);
        titlePanel.add(editIconButton);
        titlePanel.add(cancelIconButton);
        HideButtons();

        //diary text area
        textArea = new JTextArea(16, 40);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane textAreaScroll = new JScrollPane(textArea);

        //date text area
        dateTextArea = new JTextArea(1, 40);
        dateTextArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        dateTextArea.setEditable(false);
        dateTextArea.setVisible(false);
        dateTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        dateTextArea.setBorder(MetalBorders.getTextFieldBorder());

        //save button
        JButton saveButton = new StandardButton("Save Entry", Colors.pastelGreen, Colors.mintGreen);
        saveButton.addActionListener(new SaveButtonListener());

        //delete button
        JButton deleteButton = new StandardButton("Delete Entry", Colors.barbiePink, Colors.lessBarbiePink);
        deleteButton.addActionListener(new DeleteButtonListener());

        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Colors.cream);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(deleteButton);

        gbc.insets = new Insets(8,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titlePanel, gbc);

        gbc.insets = new Insets(0,8,8,8);
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(textAreaScroll, gbc);

        gbc.insets = new Insets(0,8,8,8);
        gbc.weighty = 0;
        gbc.gridy = -2;
        add(dateTextArea, gbc);

        gbc.insets = new Insets(0,8,2,8);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -3;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);

        LoadContent();
    }
    private void setSidePanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(Colors.cream);

        //entries label
        JLabel entriesLabel = new SidePanelTitleLabel("Notebooks", 18);

        //search field
        searchField = new SearchTextField();
        searchField.getDocument().addDocumentListener(new SearchDocumentListener());
        searchField.addFocusListener(new SearchFocusListener());

        //search field label
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        entryListModel = new DefaultListModel<>();
        entryList = new JList<>(entryListModel);
        entryList.setFixedCellHeight(30);
        entryList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        entryList.addMouseListener(new mouseListener());;
        JScrollPane listScroller = new JScrollPane(entryList);

        //create new entry button
        JButton createNewButton = new StandardButton("New Notebook", Colors.pastelGreen, Colors.mintGreen);
        createNewButton.addActionListener(new CreateNewButtonListener());

        gbc.insets = new Insets(10,8,0,8);
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
    private class CreateNewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newEntry;
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            ArrayList<String> titleList = new ArrayList<String>();
            for (DiaryObject v : diaryEntryMap.values()){
                titleList.add(v.titleName);
            }

            if(!titleList.contains("New Notebook")){
                newEntry = "New Notebook";
            }
            else{
                int i = 0;
                while(true) {
                    if(!titleList.contains("New Notebook (" + i + ")")) {
                        newEntry = "New Notebook (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            DiaryObject object = new DiaryObject(sqlTimestamp, sqlTimestamp, newEntry, "");
            NewEntry(sqlTimestamp, sqlTimestamp, newEntry, "");
            entryListModel.addElement(object);
            diaryEntryMap.put(sqlTimestamp, object);
            currentEntry = newEntry;
            currentIndex = entryListModel.size() - 1;

            titleField.setText(currentEntry);
            textArea.setText("");

            titleField.setPreferredSize(new Dimension(358, 24));

            ShowButtons();
            dateTextArea.setVisible(true);
            dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());


            int[] g = {currentIndex};
            entryList.setSelectedIndices(g);

            //text area active
            textArea.setEditable(true);
            titleField.setEditable(false);
            titleField.setBorder(null);
            sidePanel.setPreferredSize(new Dimension(267, 511));
        }
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = entryList.getSelectedIndex();
            if (selectedIndex != -1) {
                DiaryObject object = entryList.getModel().getElementAt(selectedIndex);
                DeleteEntry(object.dateCreated);
                diaryEntryMap.remove(object.dateCreated);
                entryListModel.remove(selectedIndex);
            }
            //statement called if selected index is not the first
            if(selectedIndex > 0) {
                //the list item one index prior is selected
                currentIndex = selectedIndex - 1;
                int[] g = {currentIndex};
                entryList.setSelectedIndices(g);
                //global entry updated
                currentEntry = entryList.getModel().getElementAt(currentIndex).titleName;
                titleField.setText(currentEntry);
                textArea.setText(entryList.getModel().getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());

            }
            //statement called if the index is 0 and there is at least one element left
            else if (entryListModel.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                entryList.setSelectedIndices(g);
                //global entry updated

                String textInput = entryList.getModel().getElementAt(0).titleName;
                currentEntry = textInput;
                titleField.setText(currentEntry);
                textArea.setText(entryList.getModel().getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());

            }
            //statement called if there are no elements remaining after deletion
            else{
                //text set to default state
                currentIndex = -1;
                titleField.setText("Press \"New Notebook\" to begin writing");
                titleField.setPreferredSize(new Dimension(358, 24));
                textArea.setText("");
                textArea.setEditable(false);
                dateTextArea.setVisible(false);
                HideButtons();
            }
            titleField.setEditable(false);
            titleField.setBorder(null);
        }
    }
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // size > 0 accounts for clicking on empty list
            if(entryListModel.size() > 0) {
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                currentEntry = titleField.getText().trim();
                DiaryObject object = entryListModel.getElementAt(currentIndex);
                object.UpdateObject(sqlTimestamp, currentEntry, textArea.getText());
                UpdateEntry(object.dateCreated, sqlTimestamp, currentEntry, textArea.getText());
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());
                int[] g = {currentIndex};
                entryList.setSelectedIndices(g);
                titleField.setEditable(false);
                titleField.setBorder(null);
                titleField.setText(currentEntry);
            }
        }
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
                DiaryObject test = entryListModel.getElementAt(currentIndex);
                currentEntry = titleField.getText().trim();
                test.UpdateObject(test.lastEdit, currentEntry, test.textContent);
                UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);
                int[] g = {currentIndex};
                entryList.setSelectedIndices(g);
                //global current entry changed
                currentEntry = titleField.getText();
            }
        }
    }
    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());

            if (index >= 0) {

                String textInput = theList.getModel().getElementAt(index).toString();
                currentIndex = index;

                int dateCutOff = textInput.lastIndexOf("(") - 1;
                currentEntry = textInput.substring(0, dateCutOff);
                titleField.setText(currentEntry);

                textArea.setText(entryListModel.getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());

                //title editing disabled
                titleField.setEditable(false);
                titleField.setBorder(null);
                titleField.setPreferredSize(new Dimension(358, 24));

                //The text area is now active
                textArea.setEditable(true);
            }
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
//            //text from the search field is used to filter items within the list
////            String filter = searchField.getText();
//            String filter = searchField.getText().toLowerCase();
//            //this statement prevents the list from being filtered when focus is lost
//            if(!searchField.getText().equals("Search")){
//                filterModel((DefaultListModel<DiaryObject>) entryList.getModel(), filter);
//
//            }
            //text from the search field is used to filter items within the list
            String filter = searchField.getText();
            //this statement prevents the list from being filtered when focus is lost
            if(!searchField.getText().equals("Search")){
                filterModel((DefaultListModel<DiaryObject>) entryList.getModel(), filter.toLowerCase());
            }
        }
    }
    private void filterModel(DefaultListModel<DiaryObject> model, String filter) {
        //elements are being removed from or added to the list, but they map keys remains unaffected
        for (Timestamp dictionaryKey : diaryEntryMap.keySet()) {
            String text = diaryEntryMap.get(dictionaryKey).toString().toLowerCase();
            //elements not containing the filter text are removed from the list
            if (!text.contains(filter)) {
                if (model.contains(diaryEntryMap.get(dictionaryKey))) {
                    model.removeElement(diaryEntryMap.get(dictionaryKey));
                }
            }
            //elements containing the filter text are added to the list
            else {
                if (!model.contains(diaryEntryMap.get(dictionaryKey))) {
                    model.addElement(diaryEntryMap.get(dictionaryKey));
                }
            }
        }
    }
    public void HideButtons(){
        saveIconButton.setVisible(false);
        editIconButton.setVisible(false);
        cancelIconButton.setVisible(false);
    }
    public void ShowButtons(){
        saveIconButton.setVisible(true);
        editIconButton.setVisible(true);
        cancelIconButton.setVisible(true);
    }
    private static class SearchFocusListener implements FocusListener{
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
    private JButton iconButton(String icon){
        ImageIcon image = new ImageIcon(icon);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        JButton iconButton = new JButton(new ImageIcon(newImg));
        iconButton.setPreferredSize(new Dimension(30, 30));
        iconButton.setBorder(BorderFactory.createEmptyBorder());
        iconButton.setBackground(Colors.cream);
        iconButton.setFocusable(false);
        return iconButton;
    }
    private void LoadContent(){
        try{
            sidePanel.setPreferredSize(new Dimension(267, 511));

            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM notebook WHERE username = '"+username+"'";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                resultSet.getString("titleName");
                DiaryObject object = new DiaryObject(resultSet.getTimestamp(1), resultSet.getTimestamp(2), resultSet.getString(3), resultSet.getString(4));
                entryListModel.addElement(object);
                diaryEntryMap.put(object.dateCreated, object);
            }
            if (entryListModel.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                entryList.setSelectedIndices(g);
                //global entry updated

                String textInput = entryList.getModel().getElementAt(0).titleName;

                currentEntry = textInput;
                //text field and text area properly updated
                titleField.setText(currentEntry);
                textArea.setText(entryList.getModel().getElementAt(currentIndex).textContent);
                textArea.setEditable(true);
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());
                dateTextArea.setVisible(true);
                dateTextArea.setText("Last Edit: " + entryListModel.getElementAt(currentIndex).GetFormattedLastEdit());
                ShowButtons();
            }
        }
        catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void NewEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        String query = "INSERT INTO notebook " +
                "(\"dateCreated\", \"lastEdit\", \"titleName\", \"textContent\", username) "
                + "VALUES ('" + dateCreated
                + "', '" + lastEdit
                + "', '" + titleName
                + "', '" + textContent
                + "', '" + username
                + "')";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void DeleteEntry(Timestamp dateCreated){
        String sql = "DELETE FROM notebook WHERE \"dateCreated\" = '"+dateCreated+"'";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void UpdateEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        try{
            String sql = "UPDATE notebook SET " +
                    "\"lastEdit\" = '" + lastEdit + "', " +
                    "\"titleName\" = '" + titleName + "', " +
                    "\"textContent\" = '" + textContent + "', " +
                    "username = '" + username + "' " +
                    "WHERE \"dateCreated\" = '" + dateCreated + "'";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void SetUsername(){
        try {
            username = resultSet.getString("username");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public class DiaryObject{
        private Timestamp dateCreated;
        private Timestamp lastEdit;
        private String titleName;
        private String textContent;
        public DiaryObject(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
            this.dateCreated = dateCreated;
            this.lastEdit = lastEdit;
            this.titleName = titleName;
            this.textContent = textContent;
        }
        public void UpdateObject(Timestamp lastEdit, String titleName, String textContent){
            this.lastEdit = lastEdit;
            this.titleName = titleName;
            this.textContent = textContent;
        }
        public String GetFormattedLastEdit(){
            Date input = new Date(lastEdit.getTime());
            Locale loc = new Locale("en", "US");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
            String date = dateFormat.format(input);
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, loc);
            String time = timeFormat.format(input);
            String output = date + " " + time;
            return output;
        }
        public String toString(){
            DateFormat f1 = new SimpleDateFormat("MM/dd/yy");
            Date date = new Date(dateCreated.getTime());
            String output = f1.format(date);
            return titleName + " (" + output +")";
        }
    }
}