package ListToDo;


import Defaults.*;
import Diary.DiaryPanel;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;


public class ListToDoPanel extends JPanel {
    JPanel sidePanel;
    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;
    private DefaultListModel<DiaryObject> toDoListModel;
    private JList<DiaryObject> toDoList;
    private JTextField inputField;
    private JTextField searchField;
    private JTextField titleField;
    private JScrollPane listScroller1;
    private final Map<Timestamp, DiaryObject> toDoMap;

    private StandardButton addButton;
    private JButton saveIconButton;
    private JButton editIconButton;
    private JButton cancelIconButton;
    private String currentEntry;
    private int currentIndex;
    private JPanel titlePanel;
    private Connection connection;
    private  ResultSet resultSet;
    private String username;


    public ListToDoPanel(Connection con, ResultSet resultSet){
        this.connection = con;
        this.resultSet = resultSet;
        SetUsername();
        GridBagConstraints gbc = new GridBagConstraints();
        toDoMap = new Hashtable<>();



        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

        //title panel
        titlePanel = new JPanel();
        titlePanel.setBackground(Colors.cream);

        //title field
        titleField = new TitleTextField("Press \"New\" to create new list");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setPreferredSize(new Dimension(358, 30));

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

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setFixedCellHeight(30);
        itemList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listScroller1 = new JScrollPane(itemList);

        inputField = new JTextField();
        inputField.addFocusListener(new ItemFocusListener());

        addButton = new StandardButton("Add", Colors.pastelGreen, Colors.mintGreen);
        JButton completeButton = new StandardButton("Complete Item", Colors.pastelGreen, Colors.mintGreen);
        completeButton.addActionListener(new CompleteItemButtonListener());
        JButton deleteButton = new StandardButton("Remove Item", Colors.barbiePink, Colors.lessBarbiePink);
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


        gbc.insets = new Insets(0,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(titlePanel, gbc);

//        HideButtons();


        gbc.insets = new Insets(0,8,0,8);
        gbc.gridy = -1;
//        gbc.weightx = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputPanel, gbc);


        gbc.insets = new Insets(8,8,8,8);
        gbc.gridy = -2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(listScroller1, gbc);


        gbc.insets = new Insets(0,8,2,8);
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -3;
        gbc.fill = GridBagConstraints.NONE;
        add(bottomPanel, gbc);

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

        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        toDoList.setFixedCellHeight(30);
        toDoList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        toDoList.addMouseListener(new toDoMouseListener());;
        JScrollPane listScroller = new JScrollPane(toDoList);

        JPanel test = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        //create new entry button
        JButton createNewButton = new StandardButton("New", Colors.pastelGreen, Colors.mintGreen);
        createNewButton.addActionListener(new CreateNewButtonListener());
        JButton deleteButton = new StandardButton("Remove", Colors.barbiePink, Colors.lessBarbiePink);
        deleteButton.addActionListener(new DeleteButtonListener());
        test.add(createNewButton);
        test.add(Box.createHorizontalStrut(5));
        test.add(deleteButton);
        test.setBackground(Colors.cream);

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
                DiaryObject test = toDoListModel.getElementAt(currentIndex);
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                test.UpdateObject(sqlTimestamp, currentEntry, itemList);
                UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);
            }
        }
    }
    private class AddItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(toDoMap.size() > 0) {
                if (!inputField.getText().trim().isEmpty() && !inputField.getText().trim().equals("Add New List Item")) {
                    itemListModel.addElement("○ " + inputField.getText().trim());
                    inputField.setText("Add New List Item");
                    DiaryObject test = toDoListModel.getElementAt(currentIndex);
                    long now = System.currentTimeMillis();
                    Timestamp sqlTimestamp = new Timestamp(now);
                    test.UpdateObject(sqlTimestamp, currentEntry, itemList);
                    UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);
                }
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
                DiaryObject test = toDoListModel.getElementAt(currentIndex);
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                test.UpdateObject(sqlTimestamp, currentEntry, itemList);
                UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);

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
                DiaryObject test = toDoListModel.getElementAt(currentIndex);
                currentEntry = titleField.getText().trim();
                long now = System.currentTimeMillis();
                Timestamp sqlTimestamp = new Timestamp(now);
                test.UpdateObject(sqlTimestamp, currentEntry, test.list);
                UpdateEntry(test.dateCreated, test.lastEdit, currentEntry, test.textContent);
                int[] g = {currentIndex};
                toDoList.setSelectedIndices(g);
                //global current entry changed
                currentEntry = titleField.getText();
            }
        }
    }
    private class toDoMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
                String textInput = theList.getModel().getElementAt(index).toString();
                int dateCutOff = textInput.lastIndexOf("(") - 1;
                currentEntry = textInput.substring(0, dateCutOff);
                currentIndex = index;

                titleField.setText(currentEntry);

                itemList = toDoListModel.getElementAt(currentIndex).list;
                itemListModel = toDoListModel.getElementAt(currentIndex).listModel;
                listScroller1.getViewport().setView(itemList);

                //title editing disabled
                titleField.setEditable(false);
                titleField.setBorder(null);
                titleField.setPreferredSize(new Dimension(358, 30));
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
                filterModel((DefaultListModel<DiaryObject>) toDoList.getModel(), filter);
            }
        }
    }
    private void filterModel(DefaultListModel<DiaryObject> model, String filter) {
        //elements are being removed from or added to the list, but they map keys remains unaffected
        for (Timestamp dictionaryKey : toDoMap.keySet()) {
            String text = toDoMap.get(dictionaryKey).toString();
            //elements not containing the filter text are removed from the list
            if (!text.contains(filter)) {
                if (model.contains(toDoMap.get(dictionaryKey))) {
                    model.removeElement(toDoMap.get(dictionaryKey));
                }
            }
            //elements containing the filter text are added to the list
            else {
                if (!model.contains(toDoMap.get(dictionaryKey))) {
                    model.addElement(toDoMap.get(dictionaryKey));
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
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            String newEntry;
            ArrayList<String> titleList = new ArrayList<String>();
            for (DiaryObject v : toDoMap.values()){
                titleList.add(v.titleName);
            }
            if(!titleList.contains("New List")){
                newEntry = "New List";
            }
            else{
                int i = 0;
                while(true) {
                    if(!titleList.contains("New List (" + i + ")")) {
                        newEntry = "New List (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            //element added to list
//            entryListModel.addElement(newEntry + " (2/24/23)");
            DiaryObject object = new DiaryObject(sqlTimestamp, sqlTimestamp, newEntry, "");
            NewEntry(sqlTimestamp, sqlTimestamp, newEntry, "");
            toDoListModel.addElement(object);
            toDoMap.put(sqlTimestamp, object);
            currentEntry = newEntry;
            currentIndex = toDoListModel.size() - 1;

            itemListModel = new DefaultListModel<>();
            //element added to map
//            JList test = itemList();
            itemList = new JList<>(itemListModel);
            itemList.setFixedCellHeight(30);
            itemList.setFont(new Font("SansSerif", Font.PLAIN, 18));

            //global current entry changed


//            itemListModel.
//either replace the model from the scroll pane or wipe the model and add the contents to the new thing

            //text field and text area properly updated
            titleField.setText(currentEntry);
            //remove scroll pane element and then add a new one?
//            listScroller1.removeAll();
//            listScroller1.setViewportView(test);
//            itemListModel.removeAllElements();
            listScroller1.getViewport().setView(itemList);
//            repaint();
//            listScroller1 = new JScrollPane(test);
            titleField.setPreferredSize(new Dimension(358, 30));




            ShowButtons();


            int[] g = {currentIndex};
            toDoList.setSelectedIndices(g);


            //text area active
            addButton.setEnabled(true);
            titleField.setEditable(false);
            titleField.setBorder(null);
            sidePanel.setPreferredSize(new Dimension(267, 511));

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
                DiaryObject object = toDoList.getModel().getElementAt(selectedIndex);
                toDoMap.remove(object.dateCreated);
                toDoListModel.remove(selectedIndex);
                DeleteEntry(object.dateCreated);
            }
            //statement called if selected index is not the first
            if(selectedIndex > 0) {
                //the list item one index prior is selected
                currentIndex = selectedIndex - 1;
                int[] g = {currentIndex};
                toDoList.setSelectedIndices(g);
                //global entry updated
                currentEntry = toDoList.getModel().getElementAt(currentIndex).titleName;                //text field and text area properly updated
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
                currentEntry = toDoList.getModel().getElementAt(currentIndex).titleName;
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
                itemListModel.removeAllElements();
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
    private void LoadContent(){
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM to_do_list WHERE username = '"+username+"'";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                resultSet.getString("titleName");
                DiaryObject object = new DiaryObject(resultSet.getTimestamp(1), resultSet.getTimestamp(2), resultSet.getString(3), resultSet.getString(4));
                toDoListModel.addElement(object);
//                object.FormJList();
                toDoMap.put(object.dateCreated, object);
                object.FormJList();
/*                String[] strSplit = object.textContent.split("\n");
                List<String> list = Arrays.asList(strSplit);
                itemListModel = new DefaultListModel<>();
                itemList = new JList<>(itemListModel);
                itemList.setFixedCellHeight(30);
                itemList.setFont(new Font("SansSerif", Font.PLAIN, 18));
                for(int i = 0; i < list.size(); i++){
                    itemListModel.addElement(list.get(i));
                }
                object.UpdateObject(object.lastEdit, object.titleName, itemList);*/
            }
            if (toDoListModel.size() > 0) {
                //first index of list is selected
                currentIndex = 0;
                int[] g = {currentIndex};
                toDoList.setSelectedIndices(g);
                //global entry updated

                String textInput = toDoList.getModel().getElementAt(0).titleName;

                currentEntry = textInput;
                //text field and text area properly updated
                titleField.setText(currentEntry);
                itemList = toDoListModel.getElementAt(currentIndex).list;
                itemListModel = toDoListModel.getElementAt(currentIndex).listModel;
                listScroller1.getViewport().setView(itemList);
                addButton.setEnabled(true);
                ShowButtons();
            }
        }
        catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void NewEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        String query = "INSERT INTO to_do_list " +
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
        String sql = "DELETE FROM to_do_list WHERE \"dateCreated\" = '"+dateCreated+"'";
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e1) {
            System.out.println(e1.getMessage());
        }
    }
    private void UpdateEntry(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
        try{
            String sql = "UPDATE to_do_list SET " +
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
        private JList<String> list;
        private DefaultListModel<String> listModel;
        private String textContent;
        public DiaryObject(Timestamp dateCreated, Timestamp lastEdit, String titleName, String textContent){
            this.dateCreated = dateCreated;
            this.lastEdit = lastEdit;
            this.titleName = titleName;
            this.textContent = textContent;

//            list = new JList<String>();
            listModel = new DefaultListModel<>();
            list = new JList<>(listModel);
        }
        public void FormJList(){
            String[] strSplit = textContent.split("\n");
            List<String> list2 = Arrays.asList(strSplit);
//            itemListModel = new DefaultListModel<>();
//            itemList = new JList<>(itemListModel);
            list.setFixedCellHeight(30);
            list.setFont(new Font("SansSerif", Font.PLAIN, 18));
            if(!list2.get(0).equals("")) {
                if (strSplit.length > 0) {
                    for (int i = 0; i < list2.size(); i++) {
                        listModel.addElement(list2.get(i));
                    }
                }
            }
//            object.UpdateObject(object.lastEdit, object.titleName, itemList);
/*            itemListModel = new DefaultListModel<>();
            itemList = new JList<>(itemListModel);
            itemList.setFixedCellHeight(30);
            itemList.setFont(new Font("SansSerif", Font.PLAIN, 18));*/

//            String[] strSplit = textContent.split("\n");
////            listModel = new DefaultListModel<String>();
////            list = new JList<String>(listModel);
//            list.setFixedCellHeight(30);
//            list.setFont(new Font("SansSerif", Font.PLAIN, 18));
//            list.setListData(strSplit);
//            list = (JList<String>) list;
        }
        public void UpdateObject(Timestamp lastEdit, String titleName, JList<String> list){
            this.lastEdit = lastEdit;
            this.titleName = titleName;
            this.list = list;
            textContent = "";

            listModel = (DefaultListModel<String>) list.getModel();
//            listModel = list.getModel();


/*            try {
                listModel = (DefaultListModel<String>) list.getModel();
            }
            catch(Exception e){
                System.out.println("check");
                listModel = list.getModel();
            }*/
            for(int i =0; i < listModel.getSize(); i++){
                textContent += (listModel.getElementAt(i) + "\n");
            }
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
//each time the thing is called the entire set of string is written to the text file