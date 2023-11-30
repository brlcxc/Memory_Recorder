package Diary;
import Defaults.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class DiaryPanel extends JPanel {
    private JPanel sidePanel;
    private final JTextArea textArea;
    private final JTextArea dateTextArea;

    private final JTextField titleField;
    private JTextField searchField;
    private final Map<String, String> diaryEntryMap;
    private DefaultListModel<String> entryListModel;
    private JList<String> entryList;
    private String currentEntry;
    private JPanel titlePanel;
    private JButton saveIconButton;
    private JButton editIconButton;
    private JButton cancelIconButton;
    private int currentIndex;
    public DiaryPanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        diaryEntryMap = new Hashtable<>();

        setLayout(new GridBagLayout());
        setBackground(Colors.cream);
        setSidePanel();

        //title panel
        titlePanel = new JPanel();
        titlePanel.setBackground(Colors.cream);

        //title field
        titleField = new DiaryTextField("Press \"Create New Entry\" to begin writing");
        titleField.setBorder(null);
        titleField.setBackground(Colors.cream);
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setPreferredSize(new Dimension(358, 24));

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
        JButton saveButton = new DiaryButton("Save Entry", Colors.pastelGreen, Colors.mintGreen);
        saveButton.addActionListener(new SaveButtonListener());

        //delete button
        JButton deleteButton = new DiaryButton("Delete Entry", Colors.barbiePink, Colors.lessBarbiePink);
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
        entryList.addMouseListener(new mouseListener());;
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
            System.out.println(titleField.getWidth());
            System.out.println(titleField.getHeight());
            String newEntry;
            if(!entryListModel.contains("New Entry")){
                newEntry = "New Entry";
            }
            else{
                int i = 0;
                while(true) {
                    if(!entryListModel.contains("New Entry (" + i + ")")) {
                        newEntry = "New Entry (" + i + ")";
                        break;
                    }
                    i++;
                }
            }
            //element added to list
//            entryListModel.addElement(newEntry + " (2/24/23)");
            entryListModel.addElement(newEntry);
            //element added to map
            diaryEntryMap.put(newEntry, "");
            //global current entry changed
            currentEntry = newEntry;
            currentIndex = entryListModel.size() - 1;

            //text field and text area properly updated
            titleField.setText(currentEntry);
            textArea.setText(diaryEntryMap.get(currentEntry));
            titleField.setPreferredSize(new Dimension(340, 24));


            //modification buttons visible
            ShowButtons();

            dateTextArea.setVisible(true);
            dateTextArea.setText("Last Edit: 2/24/23  11:32");

            int[] g = {currentIndex};
            entryList.setSelectedIndices(g);

            //text area active
            textArea.setEditable(true);
            titleField.setEditable(false);
            titleField.setBorder(null);
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
                currentIndex = selectedIndex - 1;
                int[] g = {currentIndex};
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
                currentIndex = 0;
                int[] g = {currentIndex};
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
            if(entryListModel.size() > 0) {
                if (!currentEntry.equals(titleField.getText().trim())) {
                    //modified entry name changed at index
//                    entryListModel.setElementAt(titleField.getText() + " (2/24/23)", currentIndex);
                    entryListModel.setElementAt(titleField.getText(), currentIndex);
                    //old entry removed from map
                    diaryEntryMap.remove(currentEntry);
                    //global current entry changed
                    currentEntry = titleField.getText();
                }
                //The entry is updated in the map if it has the same name
                //The entry is added to the map if it has a new name
                diaryEntryMap.put(currentEntry, textArea.getText());
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
                entryListModel.setElementAt(titleField.getText(), currentIndex);
                //text extracted from old entry
                String entryText = diaryEntryMap.get(currentEntry);
                //old entry removed from map
                diaryEntryMap.remove(currentEntry);
                //global current entry changed
                currentEntry = titleField.getText();
                //New entry name added to map
                diaryEntryMap.put(currentEntry, entryText);
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
                currentIndex = index;
                //text updated to represent selected entry
                titleField.setText(currentEntry);
                textArea.setText(diaryEntryMap.get(currentEntry));

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
//                    model.addElement(dictionaryKey + " (2/24/23)");
                    model.addElement(dictionaryKey);
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
}
//maybe split up the panel further
//have a check box showing completing?
//maybe have a last edit in bottom right corner that appears as part of box but isn't

//I wonder if there is a way too cut off text at a certain point but still have the end part