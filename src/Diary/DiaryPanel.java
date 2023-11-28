package Diary;
import Defaults.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Map;

public class DiaryPanel extends JPanel {
    private JPanel sidePanel;
    private final JTextArea textArea;
    private final JTextField titleField;
    private JTextField searchField;
    private final Map<String, String> diaryEntryMap;
    private DefaultListModel<String> entryListModel;
    private JList<String> entryList;
    private String currentEntry;
    public DiaryPanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        diaryEntryMap = new Hashtable<>();

        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

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
        saveButton.addActionListener(new SaveButtonListener());

        //delete button
        JButton deleteButton = new DiaryButton("Delete", Colors.barbiePink, Colors.lessBarbiePink);
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

        //entries label
        JLabel entriesLabel = new DiaryLabel("Diary Entries", 18);

        //search field
        searchField = new DiarySearchField();
        searchField.getDocument().addDocumentListener(new SearchDocumentListener());
        searchField.addFocusListener(new SearchFocusListener());

        //search field label
        JLabel searchLabel = new JLabel("");
        searchLabel.setForeground(Colors.textColor);
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        //list and list scroller
        entryListModel = new DefaultListModel<>();
        entryList = new JList<>(entryListModel);
        entryList.setFixedCellHeight(30);
        entryList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        entryList.addMouseListener(new mouseListener());
        JScrollPane listScroller = new JScrollPane(entryList);

        //create new entry button
        JButton createNewButton = new DiaryButton("Create New Entry", Colors.pastelGreen, Colors.mintGreen);
        createNewButton.addActionListener(new CreateNewButtonListener());

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
    private class CreateNewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newEntry;
            if(!entryListModel.contains("<<New Entry>>")){
                newEntry = "<<New Entry>>";
            }
            else{
                int i = 0;
                while(true) {
                    if(!entryListModel.contains("<<New Entry>> (" + i + ")")) {
                        newEntry = "<<New Entry>> (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            //element added to list
            entryListModel.addElement(newEntry);
            //element added to map
            diaryEntryMap.put(newEntry, "");

            //global current entry changed
            currentEntry = newEntry;

            //text field and text area properly updated
            titleField.setText(currentEntry);
            textArea.setText(diaryEntryMap.get(currentEntry));

            //text area active
            textArea.setEditable(true);
        }
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = entryList.getSelectedIndex();
            if (selectedIndex != -1) {
                //The entry is both removed from the map and the list
                diaryEntryMap.remove(entryList.getModel().getElementAt(selectedIndex));
                entryListModel.remove(selectedIndex);
            }
            //statement called if selected index is not the first
            if(selectedIndex > 0) {
                //the list item one index prior is selected
                int[] g = {selectedIndex - 1};
                entryList.setSelectedIndices(g);
                //global entry updated
                currentEntry = entryList.getModel().getElementAt(selectedIndex - 1);
                //text field and text area properly updated
                titleField.setText(currentEntry);
                textArea.setText(diaryEntryMap.get(currentEntry));
            }
            //statement called if the index is 0 and there is at least one element left
            else if (entryListModel.size() > 0) {
                //first index of list is selected
                int[] g = {0};
                entryList.setSelectedIndices(g);
                //global entry updated
                currentEntry = entryList.getModel().getElementAt(0);
                //text field and text area properly updated
                titleField.setText(currentEntry);
                textArea.setText(diaryEntryMap.get(currentEntry));
            }
            //statement called if there are no elements remaining after deletion
            else{
                //text set to default state
                titleField.setText("Press \"Create New Entry\" to begin writing");
                textArea.setText("");
                textArea.setEditable(false);
            }
        }
    }
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // size > 0 accounts for clicking on empty list
            if(entryListModel.size() > 0) {
                if (!currentEntry.equals(titleField.getText().trim())) {
                    //new entry name added to list
                    entryListModel.addElement(titleField.getText());
                    //old entry removed from map
                    diaryEntryMap.remove(currentEntry);
                    //old entry name removed from list by index
                    entryListModel.remove(entryListModel.indexOf(currentEntry));
                    //global current entry changed
                    currentEntry = titleField.getText();
                }
                //The entry is updated in the map if it has the same name
                //The entry is added to the map if it has a new name
                diaryEntryMap.put(currentEntry, textArea.getText());
            }
        }
    }
    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
                //global entry is changed to the selected entry
                currentEntry = theList.getModel().getElementAt(index).toString();
                //text updated to represent selected entry
                titleField.setText(currentEntry);
                textArea.setText(diaryEntryMap.get(currentEntry));
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
                filterModel((DefaultListModel<String>) entryList.getModel(), filter);
            }
        }
    }
    private void filterModel(DefaultListModel<String> model, String filter) {
        //elements are being removed from or added to the list, but they map keys remains unaffected
        for (String dictionaryKey : diaryEntryMap.keySet()) {
            //elements not containing the filter text are removed from the list
            if (!dictionaryKey.contains(filter)) {
                if (model.contains(dictionaryKey)) {
                    model.removeElement(dictionaryKey);
                }
            }
            //elements containing the filter text are added to the list
            else {
                if (!model.contains(dictionaryKey)) {
                    model.addElement(dictionaryKey);
                }
            }
        }
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
}