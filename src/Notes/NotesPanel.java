package Notes;

import Defaults.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotesPanel extends JPanel {
    private JPanel sidePanel;
    private JTextArea notesTextArea;

    public NotesPanel() {
        setBackground(Color.RED);
        setLayout(new BorderLayout());
        setSidePanel();
        setMainPanel();
    }

    private void setSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.ORANGE);

        JButton createButton = new JButton("Create");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewEntry();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEntry();
            }
        });

        sidePanel.add(createButton);
        sidePanel.add(saveButton);
        sidePanel.add(deleteButton);

        add(sidePanel, BorderLayout.WEST);
    }

    private void setMainPanel() {
        notesTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createNewEntry() {
        String newEntry = JOptionPane.showInputDialog("Enter a new note:");
        if (newEntry != null) {
            notesTextArea.setText(newEntry);
        }
    }

    private void saveEntry() {
        // Implement code to save the current entry
        // For simplicity, let's just display a message
        JOptionPane.showMessageDialog(this, "Entry saved!");
    }

    private void deleteEntry() {
        notesTextArea.setText(""); // Clear the text area for simplicity
    }

    public JPanel getSidePanel() {
        return sidePanel;
    }
}
//called side panel in here but notes side panel outside