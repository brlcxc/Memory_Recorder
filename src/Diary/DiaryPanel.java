package Diary;
import Defaults.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class DiaryPanel extends JPanel {
    //this needs to default to the most recent entry
    //maybe delte button in top right
    //when new entry is created edit feild is automatically set to editable
    private JPanel sidePanel;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private JTextArea textArea;
    private JScrollPane textAreaScroll;
    private JTextField titleLabel;
    private Dictionary<String, String> dict;
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
        textAreaScroll = new JScrollPane(textArea);
        test2.setBackground(Colors.cream);
        ///title near the top - created and last edited near the bottom
        JLabel label1 = new DiaryLabel("Created: ");
        JLabel label2 = new DiaryLabel("Last Edited: ");
        titleLabel = new DiaryTextField("Title");
        titleLabel.setBorder(null);
        titleLabel.setBackground(Colors.cream);
        titleLabel.setHorizontalAlignment(JTextField.CENTER);
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
        add(titleLabel, gbc);

        gbc.insets = new Insets(8,8,8,8);
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(textAreaScroll, gbc);

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
        list = new JList<>(listModel);
        list.setFixedCellHeight(30);
        list.setFont(new Font("SansSerif", Font.PLAIN, 18));
        list.addMouseListener(new mouseListener());
        JScrollPane listScroller = new JScrollPane(list);
        JButton createNewButton = new DiaryButton("Create New Entry", Colors.pastelGreen, Colors.mintGreen);
        JLabel label = new DiaryLabel("Diary Entries");

        createNewButton.addActionListener(new CreateNewButtonListener());

        gbc.insets = new Insets(8,8,0,8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        sidePanel.add(label, gbc);

        gbc.insets = new Insets(8,8,8,0);
        gbc.gridy = -1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        sidePanel.add(listScroller, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = -1;
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
            titleLabel.setText(currentEntry);
            textArea.setText(dict.get(currentEntry));
        }
    }
    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        }
    }
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = list.getSelectedIndex();

            //its only deleting those that have been selected
            if(!currentEntry.equals(titleLabel.getText().trim())){
                listModel.addElement(titleLabel.getText());
                dict.remove(currentEntry);
//                if (selectedIndex != -1) {
//                    listModel.remove(selectedIndex);
//                }
                listModel.remove(listModel.indexOf(currentEntry));
                currentEntry = titleLabel.getText();
            }
            dict.put(currentEntry, textArea.getText());
        }
    }
    private class mouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            int index = theList.locationToIndex(mouseEvent.getPoint());
            if (index >= 0) {
                currentEntry = theList.getModel().getElementAt(index).toString();
                titleLabel.setText(currentEntry);
                textArea.setText(dict.get(currentEntry));
            }
        }
    }
}