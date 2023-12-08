package Diary;
import Defaults.*;
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


public class DiaryPanel extends JPanel {
    private JPanel sidePanel;
    private final JTextArea textArea;
    private final JTextArea dateTextArea;

    private final JTextField titleField;
    private JTextField searchField;
    //private final Map<String, String> diaryEntryMap;
    private final Map<Timestamp, DiaryObject> dataMap;
//    private DefaultListModel<String> entryListModel;
    private DefaultListModel<DiaryObject> entryListModel2;

    private DefaultListModel<Integer> test3;

//    private JList<String> entryList2;
    private JList<DiaryObject> entryList2;

    private String currentEntry;
    private JPanel titlePanel;
    private JButton saveIconButton;
    private JButton editIconButton;
    private JButton cancelIconButton;
    private int currentIndex;
    private Connection connection;
    public DiaryPanel(Connection con){
        this.connection = con;
        GridBagConstraints gbc = new GridBagConstraints();
        //diaryEntryMap = new Hashtable<>();
        dataMap = new Hashtable<>();

        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

        //title panel
        titlePanel = new JPanel();
        titlePanel.setBackground(Colors.cream);

        //title field
        titleField = new TitleTextField("Press \"Create New Entry\" to begin writing");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setPreferredSize(new Dimension(358, 24));

        saveIconButton = iconButton("src/Defaults/IconImages/save.png",13,13);
        saveIconButton.addActionListener(new SaveTitleButtonListener());

        editIconButton = iconButton("src/Defaults/IconImages/editing.png",15,15);
        editIconButton.addActionListener(new EditTitleButtonListener());

        cancelIconButton = iconButton("src/Defaults/IconImages/cancel.png",15,15);
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
        JLabel entriesLabel = new SidePanelTitleLabel("Diary Entries", 18);

        //search field
        searchField = new SearchTextField();
        searchField.getDocument().addDocumentListener(new SearchDocumentListener());
        searchField.addFocusListener(new SearchFocusListener());

        //search field label
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        //list and list scroller
//        entryListModel = new DefaultListModel<>();
//        entryList = new JList<>(entryListModel);
        entryListModel2 = new DefaultListModel<>();
//        entryList = new JList<>(entryListModel2);
//        entryList.setFixedCellHeight(30);
//        entryList.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        entryList.addMouseListener(new mouseListener());;
        entryList2 = new JList<>(entryListModel2);
        entryList2.setFixedCellHeight(30);
        entryList2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        entryList2.addMouseListener(new mouseListener());;
        JScrollPane listScroller = new JScrollPane(entryList2);

        //create new entry button
        JButton createNewButton = new StandardButton("Create New Entry", Colors.pastelGreen, Colors.mintGreen);
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
            System.out.println(titleField.getWidth());
            System.out.println(titleField.getHeight());
            String newEntry;
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
//            if(!entryListModel.contains("New Entry")){
/*            if(!diaryEntryMap.containsKey("New Entry")){
                newEntry = "New Entry";
            }*/
//            Boolean flag1 = true;
            ArrayList<String> list = new ArrayList<String>();
            for (DiaryObject v : dataMap.values()){
//                if(v.titleName.equals("New Entry")){
//                    flag1 = false;
//                }
                list.add(v.titleName);
            }

            if(!list.contains("New Entry")){
                newEntry = "New Entry";
            }
            else{
                int i = 0;
                while(true) {
                    if(!list.contains("New Entry (" + i + ")")) {
                        newEntry = "New Entry (" + i + ")";
                        break;
                    }
                    if(!list.contains("New Entry (" + i + ")")) {
                        newEntry = "New Entry (" + i + ")";
                        break;
                    }
                    i++;
            }
            }
            /*if(!dataMap.containsValue("New Entry")){
                newEntry = "New Entry";
            }
            else{
                int i = 0;
                while(true) {
//                    if(!entryListModel.contains("New Entry (" + i + ")")) {
                    if(!diaryEntryMap.containsKey("New Entry (" + i + ")")) {
                        newEntry = "New Entry (" + i + ")";
                        break;
                    }
                    if(!dataMap.containsValue("New Entry (" + i + ")")) {
                        newEntry = "New Entry (" + i + ")";
                        break;
                    }
                    i++;
                }
            }*/
            DiaryObject test = new DiaryObject(sqlTimestamp, sqlTimestamp, newEntry, "");
            NewEntry(sqlTimestamp, sqlTimestamp, newEntry, "");
            //element added to list
//            entryListModel.addElement(newEntry + " (2/24/23)");
            entryListModel2.addElement(test);

//            entryListModel.addElement(newEntry);
            //element added to map
//            diaryEntryMap.put(newEntry, "");
            dataMap.put(sqlTimestamp, test);
            //global current entry changed
            currentEntry = newEntry;
//            currentIndex = entryListModel.size() - 1;
            currentIndex = entryListModel2.size() - 1;

            //text field and text area properly updated
            titleField.setText(currentEntry);
//            textArea.setText(diaryEntryMap.get(currentEntry));
//            textArea.setText(test.GetText(sqlTimestamp));
            textArea.setText("");

            titleField.setPreferredSize(new Dimension(340, 24));


            //modification buttons visible
            ShowButtons();

            String pattern = "MM/dd/yyyy";
            Locale loc = new Locale("en", "US");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);
            String date = dateFormat.format(new Date());
//            System.out.print(date);
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, loc);
            String time = timeFormat.format(new Date());
//            System.out.println(time);
            dateTextArea.setVisible(true);
//            dateTextArea.setText("Last Edit: 2/24/23  11:32");
//            dateTextArea.setText("Last Edit: " + date + " " + time);
            dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());


            int[] g = {currentIndex};
            entryList2.setSelectedIndices(g);

            //text area active
            textArea.setEditable(true);
            titleField.setEditable(false);
            titleField.setBorder(null);
        }
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = entryList2.getSelectedIndex();
            if (selectedIndex != -1) {
                //The entry is both removed from the map and the list
//                DiaryObject object = entryList2.getModel().getElementAt(selectedIndex);
//                diaryEntryMap.remove(entryList2.getModel().getElementAt(selectedIndex));
                DiaryObject object = entryList2.getModel().getElementAt(selectedIndex);
                DeleteEntry(object.dateCreated);
                dataMap.remove(object.dateCreated);
                entryListModel2.remove(selectedIndex);
            }
            //statement called if selected index is not the first
            if(selectedIndex > 0) {
                //the list item one index prior is selected
                currentIndex = selectedIndex - 1;
                int[] g = {currentIndex};
                entryList2.setSelectedIndices(g);
                //global entry updated
//                String textInput = entryList2.getModel().getElementAt(selectedIndex - 1);
//                String textInput = entryList2.getModel().getElementAt(currentIndex).titleName;
//                System.out.println(textInput);
//                int dateCutOff = textInput.lastIndexOf("(") - 1;
                currentEntry = entryList2.getModel().getElementAt(currentIndex).titleName;
                //text field and text area properly updated
                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
                textArea.setText(entryList2.getModel().getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());

            }
            //statement called if the index is 0 and there is at least one element left
            else if (entryListModel2.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                entryList2.setSelectedIndices(g);
                //global entry updated

                String textInput = entryList2.getModel().getElementAt(0).titleName;
//                System.out.println(textInput + "hel");
//                int dateCutOff = textInput.lastIndexOf("(") - 1;
//                currentEntry = textInput.substring(0, dateCutOff);
                currentEntry = textInput;
                //text field and text area properly updated
                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
                textArea.setText(entryList2.getModel().getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());

            }
            //statement called if there are no elements remaining after deletion
            else{
                //text set to default state
                currentIndex = -1;
                titleField.setText("Press \"Create New Entry\" to begin writing");
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
            if(entryListModel2.size() > 0) {
//                String newTitle = titleField.getText().trim();
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                currentEntry = titleField.getText().trim();
                DiaryObject object = entryListModel2.getElementAt(currentIndex);
                object.UpdateObject(sqlTimestamp, currentEntry, textArea.getText());
                UpdateEntry(object.dateCreated, sqlTimestamp, currentEntry, textArea.getText());
                dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());
                int[] g = {currentIndex};
                entryList2.setSelectedIndices(g);

//                if (!currentEntry.equals(newTitle)) {
//                    //modified entry name changed at index
////                    String new
////                    entryListModel2.setElementAt(titleField.getText() + " (2/24/23)", currentIndex);
////                    entryListModel.setElementAt(titleField.getText(), currentIndex);
//                    //old entry removed from map
//                    //diaryEntryMap.remove(currentEntry);
//                    //global current entry changed
//                    currentEntry = newTitle;
//                }
                //The entry is updated in the map if it has the same name
                //The entry is added to the map if it has a new name
//                diaryEntryMap.put(currentEntry, textArea.getText());
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
                //modified entry name changed at index
//                entryListModel.setElementAt(titleField.getText() + " (2/24/23)", currentIndex);
//                entryListModel.setElementAt(titleField.getText(), currentIndex);
                //text extracted from old entry
//                String entryText = entryListModel2.getElementAt(currentIndex).textContent;
                //old entry removed from map
                DiaryObject test = entryListModel2.getElementAt(currentIndex);
                currentEntry = titleField.getText().trim();
                test.UpdateObject(test.lastEdit, currentEntry, test.textContent);
                UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);
                int[] g = {currentIndex};
                entryList2.setSelectedIndices(g);                //global current entry changed
                currentEntry = titleField.getText();
                //New entry name added to map
//                diaryEntryMap.put(currentEntry, entryText);
            }
        }
    }
    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());

            if (index >= 0) {
                //global entry is changed to the selected entry

                String textInput = theList.getModel().getElementAt(index).toString();
                currentIndex = index;

                int dateCutOff = textInput.lastIndexOf("(") - 1;
                currentEntry = textInput.substring(0, dateCutOff);
System.out.println(currentEntry + "B");
                //text updated to represent selected entry
                titleField.setText(currentEntry);

                textArea.setText(entryListModel2.getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());


                //title editing disabled
                titleField.setEditable(false);
                titleField.setBorder(null);
                titleField.setPreferredSize(new Dimension(340, 24));

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
            //text from the search field is used to filter items within the list
            String filter = searchField.getText();
            //this statement prevents the list from being filtered when focus is lost
            if(!searchField.getText().equals("Search")){
//                filterModel((DefaultListModel<String>) entryList2.getModel(), filter);
                filterModel((DefaultListModel<DiaryObject>) entryList2.getModel(), filter);

            }
        }
    }
    private void filterModel(DefaultListModel<DiaryObject> model, String filter) {
        //elements are being removed from or added to the list, but they map keys remains unaffected
        for (Timestamp dictionaryKey : dataMap.keySet()) {
            String text = dataMap.get(dictionaryKey).toString();
            //elements not containing the filter text are removed from the list
            if (!text.contains(filter)) {
                if (model.contains(dataMap.get(dictionaryKey))) {
                    model.removeElement(dataMap.get(dictionaryKey));
                }
            }
            //elements containing the filter text are added to the list
            else {
                if (!model.contains(dataMap.get(dictionaryKey))) {
//                    model.addElement(dictionaryKey + " (2/24/23)");
                    model.addElement(dataMap.get(dictionaryKey));
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
    private JButton iconButton(String icon, int width, int height){
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
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM diary";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            resultSet.getString("titleName");
            DiaryObject object = new DiaryObject(resultSet.getTimestamp(1), resultSet.getTimestamp(2), resultSet.getString(3), resultSet.getString(4));
            entryListModel2.addElement(object);
        }
            if (entryListModel2.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                entryList2.setSelectedIndices(g);
                //global entry updated

                String textInput = entryList2.getModel().getElementAt(0).titleName;
//                System.out.println(textInput + "hel");
//                int dateCutOff = textInput.lastIndexOf("(") - 1;
//                currentEntry = textInput.substring(0, dateCutOff);
                currentEntry = textInput;
                //text field and text area properly updated
                titleField.setText(currentEntry);
//                textArea.setText(diaryEntryMap.get(currentEntry));
                textArea.setText(entryList2.getModel().getElementAt(currentIndex).textContent);
                dateTextArea.setText("Last Edit: " + entryListModel2.getElementAt(currentIndex).GetFormattedLastEdit());

            }
    }catch(SQLException e1) {
        System.out.println(e1.getMessage());
    }
    }
    private void NewEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        String query = "INSERT INTO diary " +
                "(\"dateCreated\", \"lastEdit\", \"titleName\", \"textContent\") "
                + "VALUES ('" + dateCreated
                + "', '" + lastEdit
                + "', '" + titleName
                + "', '" + textContent
                + "')";
        try{
          Statement stmt = connection.createStatement();
          stmt.executeUpdate(query);
    }catch(SQLException e1) {
        System.out.println(e1.getMessage());
    }
    }
    private void DeleteEntry(Timestamp dateCreated){
        String sql = "DELETE FROM diary WHERE \"dateCreated\" = '"+dateCreated+"'";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void UpdateEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        try{
        String sql = "UPDATE diary SET " +
                "\"lastEdit\" = '" + lastEdit + "', " +
                "\"titleName\" = '" + titleName + "', " +
                "\"textContent\" = '" + textContent + "' " +
                "WHERE \"dateCreated\" = '" + dateCreated + "'";
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }

    }
    public class DiaryObject{
        Timestamp dateCreated;
        Timestamp lastEdit;
        String titleName;
        String textContent;
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
//            System.out.print(date);
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

//maybe split up the panel further
//have a check box showing completing?
//maybe have a last edit in bottom right corner that appears as part of box but isn't

//I wonder if there is a way too cut off text at a certain point but still have the end part